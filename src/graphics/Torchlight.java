
/**
 * TORCHLIGHT CLASS
 * Handles torchlight effect surrounding player.
 */

package graphics;

import entities.Player;
import logic.Tile;
import logic.Point;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;


public class Torchlight {
    private int tileSize;
    private Color lit;
    private Color dim;
    private Color dark;

    public Torchlight(int tileSize) {
        this.tileSize = tileSize;
        this.lit = new Color(255, 214, 170, 50);
        this.dim = new Color(255, 197, 143, 50);
        this.dark = new Color(255, 147, 41, 50);
    }

    public void castLight(Graphics2D gfx, Player player, Tile[] viewportTiles) {
        float x, y;
        Point[] rayPoints = new Point[30];
        int index = 0;
        for (int i = 0; i < 360; i += 12) {
            x = (float) Math.cos((float) i * 0.01745f);
            y = (float) Math.sin((float) i * 0.01745f);
            rayPoints[index] = castRay(gfx, player, x, y, viewportTiles);
            index++;
        }
        connectRays(gfx, rayPoints);
    }

    private Point castRay(Graphics2D gfx, Player player, float x, float y, Tile[] viewportTiles) {
        float ox = (float) player.getCurrentX() + (tileSize / 2) + 0.5f;
        float oy = (float) player.getCurrentY() + (tileSize / 2) + 0.5f;
        for (int i = 0; i < 128; i++) {
            Tile tile = findTileSpace((int) ox, (int) oy, viewportTiles);
            if (tile == null || tile.isPerimeter()) {
                return new Point((int)ox, (int)oy);
            }
            ox += x;
            oy += y;
        }
        return new Point((int)ox, (int)oy);
    }

    private void connectRays(Graphics2D gfx, Point[] rayPoints) {
        int[] xPoints = new int[rayPoints.length];
        int[] yPoints = new int[rayPoints.length];
        for (int i = 0; i < rayPoints.length; i++) {
            xPoints[i] = rayPoints[i].x;
            yPoints[i] = rayPoints[i].y;
        }
        gfx.setColor(lit);
        Polygon p = new Polygon(xPoints, yPoints, rayPoints.length);
        gfx.fillPolygon(p);
    }

    private Tile findTileSpace(int x, int y, Tile[] viewportTiles) {
        for (Tile tile : viewportTiles) {
            if ((tile.x * tileSize <= x && tile.x * tileSize + tileSize >= x) && (tile.y * tileSize <= y && tile.y * tileSize + tileSize >= y)) {
                return tile;
            }
        }
        return null;
    }
}