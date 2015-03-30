
/**
 * CAMERA CLASS
 * Renders viewport area around player.
 */

package logic;

import creatures.Player;

import java.awt.Color;
import java.awt.Graphics2D;

import java.util.List;


public class Camera {
    private int tileSize;
    private List<Tile> tiles;
    private int[][] grid;
    private Player player;

    private int viewportWidth;
    private int viewportHeight;


    public Camera(List<Tile> tiles, int[][] grid, int tileSize, int x, int y) {
        this.tiles = tiles;
        this.grid = grid;
        this.tileSize = tileSize;
        this.viewportWidth = x;
        this.viewportHeight = y;
        this.player = new Player(100, 200);
    }

    public void render(Graphics2D gfx) {
        int centerX = player.getX() - (viewportWidth / 2);
        int centerY = player.getY() - (viewportHeight / 2);
        int maxX = centerX + viewportWidth;
        int maxY = centerY + viewportHeight;
        int minX = centerX - (viewportWidth / 2);
        int minY = centerY - (viewportHeight / 2);

        int tileX, tileY;
        Color floor = new Color(0x34495e);
        Color wall = new Color(0x2c3e50);

        // Translate graphics origin as player position
        gfx.translate(-centerX, -centerY);

        for (Tile tile : tiles) {
            tileX = tile.getX() * tileSize;
            tileY = tile.getY() * tileSize;

            // Ignore tiles outside of viewport
            if (tileX < minX || tileX > maxX || tileY < minY || tileY > maxY) {
                continue;
            }

            if (tile.isFloor(grid)) {
                gfx.setColor(floor);
            } else if (tile.isWall(grid)) {
                gfx.setColor(wall);
            }
            gfx.fillRect(tileX, tileY, tileSize, tileSize);
        }

        player.draw(gfx);

        // Reset graphics origin
        gfx.translate(centerX, centerY);
    }

    public void playerMove() {
        player.move(2, 2);
        System.out.println(player);
    }
}