
/**
 * CAMERA CLASS
 * Renders viewport area around player.
 */

package logic;

import creatures.Player;

import java.awt.Color;
import java.awt.Graphics;

import java.util.List;


public class Camera {
    private int tileSize;
    private List<Tile> tiles;
    private Player player;

    private int viewportWidth;
    private int viewportHeight;
    private int camUpperLeftX;
    private int camUpperLeftY;


    public Camera(List<Tile> tiles, int tileSize, int x, int y) {
        this.tiles = tiles;
        this.tileSize = tileSize;
        this.viewportWidth = x;
        this.viewportHeight = y;
    }

    public void placePlayer() {
        // Ensure player start position is on floor and near bottom-center
        int numberOfTiles = tiles.size();
        int lastFourthTiles = (int) Math.round(numberOfTiles * 0.8);
        for (int i = lastFourthTiles; i < numberOfTiles; i++) {
            Tile tile = tiles.get(i);
            if (tile.isFloor()) {
                this.player = new Player(tile.getX() * tileSize, tile.getY() * tileSize);
                return;
            }
        }
    }

    public void render(Graphics gfx) {
        findCameraUpperLeft();
        int maxX = camUpperLeftX + viewportWidth;
        int maxY = camUpperLeftY + viewportHeight;

        int tileX, tileY;
        Color floor = new Color(0x34495e);
        Color wall = new Color(0x2c3e50);

        // Translate graphics origin to camera's upper left
        gfx.translate(-camUpperLeftX, -camUpperLeftY);

        for (Tile tile : tiles) {
            tileX = tile.getX() * tileSize;
            tileY = tile.getY() * tileSize;

            // Ignore tiles outside of viewport
            if (tileX < camUpperLeftX || tileX > maxX || tileY < camUpperLeftY || tileY > maxY) {
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
        gfx.translate(camUpperLeftX, camUpperLeftY);
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

    private void findCameraUpperLeft() {
        // These values are in pixels, not tile units
        camUpperLeftX = player.getX() - (viewportWidth / 2);
        camUpperLeftY = player.getY() - (viewportHeight / 2);
    }
}