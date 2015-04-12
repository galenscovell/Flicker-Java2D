
/**
 * RENDERER CLASS
 * Renders Tiles and Entities within viewport around Player.
 */

package logic;

import entities.Entity;
import entities.Player;
import entities.Salamander;
import graphics.Fog;

import java.awt.Color;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.List;


public class Renderer {
    private int tileSize;
    private List<Tile> tiles;
    private int viewportWidth, viewportHeight;

    private Fog fog;
    private Player player;
    private List<Entity> entities;
    private List<Entity> entitiesInView;
    private List<Entity> entitiesOutOfView;


    public Renderer(List<Tile> tiles, int tileSize, int x, int y) {
        this.tiles = tiles;
        this.tileSize = tileSize;
        this.viewportWidth = x;
        this.viewportHeight = y;
        this.fog = new Fog();
        this.entities = new ArrayList<Entity>();
        this.entitiesInView = new ArrayList<Entity>();
        this.entitiesOutOfView = new ArrayList<Entity>();
    }

    public void render(Graphics2D gfx) {
        // These values are in pixels, not tile units
        int camUpperLeftX = player.getX() - (viewportWidth / 2) + (tileSize / 2);
        int camUpperLeftY = player.getY() - (viewportHeight / 2) + (tileSize / 2);
        int maxX = camUpperLeftX + viewportWidth;
        int maxY = camUpperLeftY + viewportHeight;

        // Translate graphics origin to camera's upper left
        gfx.translate(-camUpperLeftX, -camUpperLeftY);

        int tileX, tileY;
        for (Tile tile : tiles) {
            // Tile [x, y] are in Tiles, convert to pixels
            tileX = tile.x * tileSize;
            tileY = tile.y * tileSize;
            // Ignore tiles outside of current viewport
            if (tileX >= camUpperLeftX && tileX <= (maxX - tileSize) && tileY >= camUpperLeftY && tileY <= (maxY - tileSize)) {
                tile.draw(gfx, tileSize);
            }
        }

        entitiesInView = new ArrayList<Entity>();
        entitiesOutOfView = new ArrayList<Entity>();
        for (Entity entity : entities) {
            // Entity [x, y] are in pixels
            // Ignore entities outside of current viewport
            if (entity.getX() >= camUpperLeftX && entity.getX() <= (maxX - tileSize) && entity.getY() >= camUpperLeftY && entity.getY() <= (maxY - tileSize)) {
                entity.draw(gfx, tileSize);
                if (entity != player) {
                    entitiesInView.add(entity);
                }
            } else {
                entitiesOutOfView.add(entity);
            }
        }

        fog.render(gfx);

        // Reset graphics origin
        gfx.translate(camUpperLeftX, camUpperLeftY);
    }

    public void placePlayer() {
        boolean playerPlaced = false;
        // Ensure player start position is on floor
        for (Tile tile : tiles) {
            if (tile.isFloor() && !playerPlaced) {
                this.player = new Player(tile.x * tileSize, tile.y * tileSize);
                tile.toggleOccupied();
                entities.add(player);
                playerPlaced = true;
            } else if (tile.isFloor() && playerPlaced) {
                entities.add(new Salamander(tile.x * tileSize, tile.y * tileSize));
                tile.toggleOccupied();
                return;
            }
        }
    }

    public void playerMove(int dx, int dy) {
        int playerX = (player.getX() / tileSize);
        int playerY = (player.getY() / tileSize);

        Tile nextTile = findTile(playerX + dx, playerY + dy);
        if (nextTile.isFloor() && !nextTile.isOccupied()) {
            // If possible, move to new Tile and set old Tile as unoccupied
            Tile currentTile = findTile(playerX, playerY);
            currentTile.toggleOccupied();
            player.move(dx * tileSize, dy * tileSize, true);
            nextTile.toggleOccupied();
        } else {
            // Otherwise just turn in that direction
            player.move(dx * tileSize, dy * tileSize, false);
        }
    }

    public List<Entity> getEntityOutOfViewList() {
        return entitiesOutOfView;
    }

    public List<Entity> getEntityInViewList() {
        return entitiesInView;
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