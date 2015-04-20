
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
    private int tileSize;
    private Color lit;
    private Color dark;

    public Torchlight(int tileSize) {
        this.tileSize = tileSize;
        this.lit = new Color(255, 214, 170, 80);
        this.dark = new Color(0, 0, 0, 240);
    }

    public void castLight(Graphics2D gfx, Player player, Map<Tile, Boolean> lightMap) {
        float x, y;
        for (int i = 0; i < 360; i += 6) {
            x = (float) Math.cos((float) i * 0.01745f);
            y = (float) Math.sin((float) i * 0.01745f);
            castRay(player, x, y, lightMap);
        }
        connectRays(gfx, lightMap);
    }

    private void castRay(Player player, float x, float y, Map<Tile, Boolean> lightMap) {
        float ox = (float) player.getCurrentX() + (tileSize / 2) + 0.5f;
        float oy = (float) player.getCurrentY() + (tileSize / 2) + 0.5f;
        for (int i = 0; i < 96; i++) {
            Tile tile = findTileSpace((int) ox, (int) oy, lightMap);
            if (tile == null) {
                return;
            } else {
                lightMap.put(tile, true);
            }
            ox += x;
            oy += y;
        }
    }

    private void connectRays(Graphics2D gfx, Map<Tile, Boolean> lightMap) {
        for (Map.Entry<Tile, Boolean> entry : lightMap.entrySet()) {
            Tile tile = entry.getKey();
            if (entry.getValue()) {
                gfx.setColor(lit);
            } else {
                gfx.setColor(dark);
            }
            gfx.fillRect(tile.x * tileSize, tile.y * tileSize, tileSize, tileSize);
        }
    }

    private Tile findTileSpace(int x, int y, Map<Tile, Boolean> lightMap) {
        int tileX, tileY;
        for (Tile tile : lightMap.keySet()) {
            tileX = tile.x * tileSize;
            tileY = tile.y * tileSize;
            if (tileX <= x && (tileX + tileSize) >= x && tileY <= y && (tileY + tileSize) >= y) {
                return tile;
            }
        }
        return null;
    }
}