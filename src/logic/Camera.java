
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
    private Player player;

    private int viewportWidth;
    private int viewportHeight;
    private int centerX;
    private int centerY;


    public Camera(List<Tile> tiles, int tileSize, int x, int y) {
        this.tiles = tiles;
        this.tileSize = tileSize;
        this.viewportWidth = x;
        this.viewportHeight = y;
        this.player = new Player(1000, 1800);
    }

    public void render(Graphics2D gfx) {
        findCameraCenter();
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

            if (tile.isFloor()) {
                gfx.setColor(floor);
            } else if (tile.isWall()) {
                gfx.setColor(wall);
            }
            gfx.fillRect(tileX, tileY, tileSize, tileSize);
        }

        player.draw(gfx);

        // Reset graphics origin
        gfx.translate(centerX, centerY);
    }

    public void playerMove(int dx, int dy) {
        int playerX = (player.getX() / tileSize);
        int playerY = (player.getY() / tileSize);

        Tile nextLocation = findTile(playerX + dx, playerY + dy);
        if (nextLocation != null && nextLocation.isFloor()) {
            player.move(dx * tileSize, dy * tileSize);
        }
    }

    private Tile findTile(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.getX() == x && tile.getY() == y) {
                return tile;
            }
        }
        return null;
    }

    private void findCameraCenter() {
        centerX = player.getX() - (viewportWidth / 2);
        centerY = player.getY() - (viewportHeight / 2);
    }
}