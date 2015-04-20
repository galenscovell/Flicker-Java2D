
/**
 * RENDERER CLASS
 * Renders Tiles and Entities within viewport around Player.
 */

package logic;

import entities.Entity;
import entities.Player;
import entities.Salamander;
import graphics.Fog;
import graphics.Torchlight;

import java.awt.Color;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Renderer {
    private int tileSize, viewportWidth, viewportHeight;
    private List<Tile> tiles;
    private Map<Tile, Boolean> lightMap;
    private List<Entity> entities;

    private Fog fog;
    private Player player;
    private Torchlight torchlight;


    public Renderer(List<Tile> tiles, int tileSize, int x, int y) {
        this.tileSize = tileSize;
        this.viewportWidth = x;
        this.viewportHeight = y;
        this.tiles = tiles;
        this.lightMap = new HashMap<Tile, Boolean>();
        this.entities = new ArrayList<Entity>();

        this.fog = new Fog();
        this.torchlight = new Torchlight(tileSize);
    }

    public void render(Graphics2D gfx, double interpolation) {
        lightMap.clear();
        // These values are in pixels, not tile units
        int camUpperLeftX = player.getCurrentX() - (viewportWidth / 2);
        int camUpperLeftY = player.getCurrentY() - (viewportHeight / 2);
        int maxX = camUpperLeftX + viewportWidth;
        int maxY = camUpperLeftY + viewportHeight;
        
        // Translate graphics origin to camera's upper left
        gfx.translate(-camUpperLeftX, -camUpperLeftY);

        int tileX, tileY;
        for (Tile tile : tiles) {
            // Tile [x, y] are in Tiles, convert to pixels
            tileX = tile.x * tileSize;
            tileY = tile.y * tileSize;
            
            if (inViewport(tileX, tileY, camUpperLeftY, maxX, camUpperLeftY, maxY)) {
                tile.draw(gfx, tileSize);
                if (tile.isFloor()) {
                    lightMap.put(tile, false);
                } else {
                    gfx.setColor(new Color(0, 0, 0, 220));
                    gfx.fillRect(tileX, tileY, tileSize, tileSize);
                }
            }
        }

        for (Entity entity : entities) {
            // Entity [x, y] are in pixels
            if (inViewport(entity.getX(), entity.getY(), camUpperLeftY, maxX, camUpperLeftY, maxY)) {
                if (entity.isAttacking()) {
                    entity.attack(gfx, tileSize, interpolation, player);
                } else {
                    entity.draw(gfx, tileSize, interpolation);
                }
                if (!entity.isInView()) {
                    entity.toggleInView();
                }
            } else if (entity.isInView()) {
                entity.toggleInView();
            }
        }

        player.draw(gfx, tileSize, interpolation);
        torchlight.castLight(gfx, player, lightMap);
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
                playerPlaced = true;
            } else if (tile.isFloor() && playerPlaced) {
                entities.add(new Salamander(tile.x * tileSize, tile.y * tileSize));
                tile.toggleOccupied();
                return;
            }
        }
    }

    public List<Entity> getEntityList() {
        return entities;
    }

    public Player getPlayer() {
        return player;
    }

    private boolean inViewport(int x, int y, int camUpperLeftX, int maxX, int camUpperLeftY, int maxY) {
        if ((x + tileSize) >= camUpperLeftX && (x - tileSize) <= maxX && (y + tileSize) >= camUpperLeftY && (y - tileSize) <= maxY) {
            return true;
        } else {
            return false;
        }
    }   
}