
/**
 * RENDERER CLASS
 * Renders Tiles and Entities within viewport around Player.
 */

package logic;

import entities.Entity;
import entities.Player;
import entities.Salamander;
import graphics.Fog;

import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.List;


public class Renderer {
    private int tileSize, viewportWidth, viewportHeight;
    private List<Tile> tiles;
    private Fog fog;
    private Player player;
    private List<Entity> entities;


    public Renderer(List<Tile> tiles, int tileSize, int x, int y) {
        this.tiles = tiles;
        this.tileSize = tileSize;
        this.viewportWidth = x;
        this.viewportHeight = y;
        this.fog = new Fog();
        this.entities = new ArrayList<Entity>();
    }

    public void render(Graphics2D gfx, double interpolation) {
        // These values are in pixels, not tile units
        int camUpperLeftX = player.getCurrentX() - (viewportWidth / 2);
        int camUpperLeftY = player.getCurrentY() - (viewportHeight / 2);
        int maxX = camUpperLeftX + viewportWidth;
        int maxY = camUpperLeftY + viewportHeight;
        
        // Translate graphics origin to camera's upper left
        // Extend the corner by half a tile in both directions to prevent rendering issues
        gfx.translate(-(camUpperLeftX + tileSize / 2), -(camUpperLeftY + tileSize / 2));

        int tileX, tileY;
        for (Tile tile : tiles) {
            // Tile [x, y] are in Tiles, convert to pixels
            tileX = tile.x * tileSize;
            tileY = tile.y * tileSize;
            // Only draw Tiles within current viewport
            if ((tileX + tileSize) >= camUpperLeftX && (tileX - tileSize) <= maxX && (tileY + tileSize) >= camUpperLeftY && (tileY - tileSize) <= maxY) {
                tile.draw(gfx, tileSize);
            }
        }

        for (Entity entity : entities) {
            // Entity [x, y] are in pixels
            // Only draw Entities within current viewport
            if (entity.getX() >= camUpperLeftX && entity.getX() <= maxX && entity.getY() >= camUpperLeftY && entity.getY() <= maxY) {
                entity.draw(gfx, tileSize, interpolation);
                if (!entity.isInView()) {
                    entity.toggleInView();
                }
            } else if (entity.isInView()) {
                entity.toggleInView();
            }
        }
        player.draw(gfx, tileSize, interpolation);
        fog.render(gfx);

        // Reset graphics origin
        gfx.translate((camUpperLeftX + tileSize / 2), (camUpperLeftY + tileSize / 2));
    }

    public void placePlayer() {
        boolean playerPlaced = false;
        int critterPlaced = 0;
        // Ensure player start position is on floor
        for (Tile tile : tiles) {
            if (tile.isFloor() && !playerPlaced) {
                this.player = new Player(tile.x * tileSize, tile.y * tileSize);
                tile.toggleOccupied();
                playerPlaced = true;
            } else if (tile.isFloor() && playerPlaced) {
                entities.add(new Salamander(tile.x * tileSize, tile.y * tileSize));
                tile.toggleOccupied();
                critterPlaced++;
                if (critterPlaced == 2) {
                    return;
                }
            }
        }
    }

    public List<Entity> getEntityList() {
        return entities;
    }

    public Player getPlayer() {
        return player;
    }

    private Tile findTile(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.x == x && tile.y == y) {
                return tile;
            }
        }
        return null;
    }
}