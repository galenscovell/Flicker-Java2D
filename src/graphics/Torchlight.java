
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

import java.util.List;


public class Torchlight {
    private int tileSize;
    private Color lit;
    private Color dim;
    private Color dark;
    private Color darker;
    private Color pitch;

    public Torchlight(int tileSize) {
        this.tileSize = tileSize;
        this.lit = new Color(0.0f, 0.0f, 0.0f, 0.1f);
        this.dim = new Color(0.0f, 0.0f, 0.0f, 0.3f);
        this.dark = new Color(0.0f, 0.0f, 0.0f, 0.5f);
        this.darker = new Color(0.0f, 0.0f, 0.0f, 0.7f);
        this.pitch = new Color(0.0f, 0.0f, 0.0f, 0.9f);
    }

    public void setDark(Graphics2D gfx, Tile tile) {
        gfx.setColor(darker);
        gfx.fillRect(tile.x * tileSize, tile.y * tileSize, tileSize, tileSize);
    }

    public void castLight(Graphics2D gfx, Player player, List<Tile> tiles) {
        float x, y;

        for (int i = 0; i < 360; i += 10) {
            x = (float) Math.cos((float) i * 0.01745f);
            y = (float) Math.sin((float) i * 0.01745f);
            castRay(gfx, player, x, y, tiles);
        }
    }

    private void castRay(Graphics2D gfx, Player player, float x, float y, List<Tile> tiles) {
        float ox, oy;
        ox = (float) player.getCurrentX() + (tileSize / 2) + 0.5f;
        oy = (float) player.getCurrentY() + (tileSize / 2) + 0.5f;
        for (int i = 0; i < 128; i++) {
            Tile tile = findTileSpace((int) ox, (int) oy, tiles);
            if (tile == null) {
                return;
            }
            gfx.setColor(Color.WHITE);
            gfx.fillRect(tile.x * tileSize, tile.y * tileSize, tileSize, tileSize);
            ox += x;
            oy += y;
        }
    }

    private Tile findTileSpace(int x, int y, List<Tile> tiles) {
        for (Tile tile : tiles) {
            if ((tile.x * tileSize <= x && tile.x * tileSize + tileSize >= x) && (tile.y * tileSize <= y && tile.y * tileSize + tileSize >= y)) {
                return tile;
            }
        }
        return null;
    }
}