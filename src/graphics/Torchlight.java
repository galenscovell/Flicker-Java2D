
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
        this.lit = new Color(0.2f, 0.1f, 0.0f, 0.0f);
        this.dim = new Color(0.2f, 0.1f, 0.0f, 0.15f);
        this.dark = new Color(0.0f, 0.0f, 0.0f, 0.3f);
        this.darker = new Color(0.0f, 0.0f, 0.0f, 0.6f);
        this.pitch = new Color(0.0f, 0.0f, 0.0f, 0.9f);
    }

    public void castLight(Graphics2D gfx, Player player, List<Tile> tiles) {
        Point start = new Point(player.getX() / tileSize, player.getY() / tileSize);
        int absDiffX, absDiffY;

        for (Tile tile : tiles) {
            absDiffX = Math.abs(start.x - tile.x);
            absDiffY = Math.abs(start.y - tile.y);

            // Ignore all tiles except those within radius
            if (absDiffX > 5 || absDiffY > 5) {
                continue;
            }

            Point end = new Point(tile.x, tile.y);
            castRay(gfx, start, end);
        }
    }

    private void castRay(Graphics2D gfx, Point start, Point end) {
        gfx.setColor(Color.WHITE);
        gfx.drawLine(start.x * tileSize, start.y * tileSize, end.x * tileSize, end.y * tileSize);
    }
}