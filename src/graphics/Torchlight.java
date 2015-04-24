
/**
 * TORCHLIGHT CLASS
 * Handles torchlight effect surrounding player.
 */

package graphics;

import entities.Player;
import logic.Tile;

import java.awt.Color;
import java.awt.Graphics2D;

import java.util.HashMap;
import java.util.Map;


public class Torchlight {
    private int tileSize, radius, startX, startY;
    private int[][] mult;
    private float[][] resistanceMap;
    private float[][] lightMap;


    public Torchlight(int tileSize, float[][] resistanceMap) {
        this.tileSize = tileSize;
        this.radius = 6;
        this.mult = new int[][]{{1, 0, 0, -1, -1, 0, 0, 1}, 
                                {0, 1, -1, 0, 0, -1, 1, 0}, 
                                {0, 1, 1, 0, 0, -1, -1, 0}, 
                                {1, 0, 0, 1, -1, 0, 0, -1}};
        this.resistanceMap = resistanceMap;
        this.lightMap = new float[resistanceMap.length][resistanceMap[0].length];
    }

    public void findFOV(Graphics2D gfx, Player player) {
        startX = player.getCurrentX() / tileSize;
        startY = player.getCurrentY() / tileSize;
        lightMap[startY][startX] = 1.0f;

        for (int i = 0; i < 8; i++) {
            castLight(1, 1.0f, 0.0f, mult[0][i], mult[1][i], mult[2][i], mult[3][i]);
        }

        drawLight(gfx);
    }

    private void drawLight(Graphics2D gfx) {
        for (int x = 0; x < lightMap[0].length; x++) {
            for (int y = 0; y < lightMap.length; y++) {
                gfx.setColor(new Color(0.0f, 0.0f, 0.0f, 1.0f - lightMap[y][x]));
                gfx.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                // Reset each value for next frame
                lightMap[y][x] = 0.0f;
            }
        }
    }

    private void castLight(int row, float startSlope, float endSlope, int xx, int xy, int yx, int yy) {
        float newStart = 0.0f;
        if (startSlope < endSlope) {
            return;
        }

        boolean blocked = false;
        for (int distance = row; distance <= radius && !blocked; distance++) {
            int dy = -distance;
            for (int dx = -distance; dx <= 0; dx++) {
                int currentX = startX + dx * xx + dy * xy;
                int currentY = startY + dx * yx + dy * yy;
                float leftSlope = (dx - 0.5f) / (dy + 0.5f);
                float rightSlope = (dx + 0.5f) / (dy - 0.5f);

                if (!(currentX >= 0 && currentY >= 0 && currentX < lightMap[0].length && currentY < lightMap.length) || startSlope < rightSlope) {
                    continue;
                } else if (endSlope > leftSlope) {
                    break;
                }

                // Check if in lightable area and light if needed
                float radiusCircle = (float) Math.sqrt(dx * dx + dy * dy);
                if (radiusCircle <= radius) {
                    float brightness = (1 - (radiusCircle / radius));
                    lightMap[currentY][currentX] = brightness;
                }

                // Previous cell was blocking
                if (blocked) {
                    if (resistanceMap[currentY][currentX] >= 1) {
                        newStart = rightSlope;
                        continue;
                    } else {
                        blocked = false;
                        startSlope = newStart;
                    }
                } else {
                    // Hit wall within sight line
                    if (resistanceMap[currentY][currentX] >= 1 && distance < radius) {
                        blocked = true;
                        castLight(distance + 1, startSlope, leftSlope, xx, xy, yx, yy);
                        newStart = rightSlope;
                    }
                }
            }
        }
    }
}