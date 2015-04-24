
/**
 * RENDERER CLASS
 * Handles game graphics.
 * Calls rendering for Player and Tiles, Entities, and Inanimates within current Player viewport.
 * Creates resistance/light maps for FOV calls via Torchlight as well as Fog.
 */

package logic;

import entities.Dead;
import entities.Entity;
import entities.Player;
import entities.Salamander;

import graphics.Fog;
import graphics.Torchlight;

import inanimates.Door;
import inanimates.Inanimate;

import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Renderer {
    private int tileSize, viewportWidth, viewportHeight;
    private Map<Integer, Tile> tiles;

    private List<Entity> entities;
    private List<Dead> deadList;
    private List<Inanimate> inanimates;

    private Player player;
    private Torchlight torchlight;
    private Fog fog;


    public Renderer(Map<Integer, Tile> tiles, int tileSize, int x, int y) {
        this.tileSize = tileSize;
        this.viewportWidth = x;
        this.viewportHeight = y;
        this.tiles = tiles;

        this.entities = new ArrayList<Entity>();
        this.deadList = new ArrayList<Dead>();
        this.inanimates = new ArrayList<Inanimate>();

        this.fog = new Fog();
    }

    public void render(Graphics2D gfx, double interpolation) {
        // These values are in pixels, not Tiles
        int camUpperLeftX = player.getCurrentX() - (viewportWidth / 2);
        int camUpperLeftY = player.getCurrentY() - (viewportHeight / 2);
        int maxX = camUpperLeftX + viewportWidth;
        int maxY = camUpperLeftY + viewportHeight;

        // Translate graphics origin to camera's upper left
        gfx.translate(-camUpperLeftX, -camUpperLeftY);

        for (Tile tile : tiles.values()) {
            // Tile [x, y] are in Tiles, convert to pixels
            if (inViewport(tile.x * tileSize, tile.y * tileSize, camUpperLeftX, maxX, camUpperLeftY, maxY)) {
                tile.draw(gfx, tileSize);
            }
        }

        for (Dead dead : deadList) {
            // Dead [x, y] are in Tiles, convert to pixels
            if (inViewport(dead.getX() * tileSize, dead.getY() * tileSize, camUpperLeftX, maxX, camUpperLeftY, maxY)) {
                dead.draw(gfx);
            }
        }

        for (Inanimate inanimate : inanimates) {
            // Inanimate [x, y] are in Tiles, convert to pixels
            if (inViewport(inanimate.getX() * tileSize, inanimate.getY() * tileSize, camUpperLeftX, maxX, camUpperLeftY, maxY)) {
                inanimate.draw(gfx);
                torchlight.updateResistanceMap(inanimate.getX(), inanimate.getY(), inanimate.isBlocking());
            }
        }

        int diffX, diffY;
        for (Entity entity : entities) {
            // Entity [x, y] are in pixels
            if (inViewport(entity.getX(), entity.getY(), camUpperLeftX, maxX, camUpperLeftY, maxY)) {
                if (entity.isAttacking()) {
                    entity.attack(gfx, interpolation, player);
                } else {
                    entity.draw(gfx, interpolation);
                }

                diffX = Math.abs(player.getX() - entity.getX());
                diffY = Math.abs(player.getY() - entity.getY());
                if (!entity.isInView() && (diffX < 150 && diffY < 150)) {
                    entity.toggleInView();
                } else if (entity.isInView() && (diffX >= 150 || diffY >= 150)) {
                    entity.toggleInView();
                }
            } 
        }

        if (player.isAttacking()) {
            player.attack(gfx, interpolation);
        }

        player.draw(gfx, interpolation);
        torchlight.findFOV(gfx, player, camUpperLeftX / tileSize, maxX / tileSize, camUpperLeftY / tileSize, maxY / tileSize);
        fog.render(gfx);

        // Reset graphics origin
        gfx.translate(camUpperLeftX, camUpperLeftY);
    }

    public List<Entity> getEntityList() {
        return entities;
    }

    public List<Dead> getDeadList() {
        return deadList;
    }

    public List<Inanimate> getInanimateList() {
        return inanimates;
    }

    public Player getPlayer() {
        return player;
    }

    public void placePlayer() {
        boolean playerPlaced = false;
        // Ensure player start position is on floor
        for (Tile tile : tiles.values()) {
            if (tile.isFloor() && !playerPlaced) {
                this.player = new Player(tile.x * tileSize, tile.y * tileSize, tileSize);
                tile.toggleOccupied();
                playerPlaced = true;
            } else if (tile.isFloor() && playerPlaced) {
                entities.add(new Salamander(tile.x * tileSize, tile.y * tileSize, tileSize));
                tile.toggleOccupied();
                return;
            }
        }
    }

    public void placeInanimates() {
        for (Tile tile : tiles.values()) {
            if (tile.isFloor() && tile.getFloorNeighbors() > 2) {
                if (tile.getBitmask() == 1010) {
                    inanimates.add(new Door(tile.x, tile.y, tileSize, 0));
                    tile.toggleBlocking();
                    tile.toggleOccupied();
                } else if (tile.getBitmask() == 101) {
                    inanimates.add(new Door(tile.x, tile.y, tileSize, 1));
                    tile.toggleBlocking();
                    tile.toggleOccupied();
                }
            }
        }
    }

    public void createResistanceMap(int width, int height) {
        int columns = width / tileSize;
        int rows = height / tileSize;
        float[][] resistanceMap = new float[rows][columns];

        for (Tile tile : tiles.values()) {
            float resistance;
            if (tile.isPerimeter() || tile.isBlocking()) {
                resistance = 2.0f;
            } else {
                resistance = 0.0f;
            }
            resistanceMap[tile.y][tile.x] = resistance;
        }
        this.torchlight = new Torchlight(tileSize, resistanceMap);
    }

    private boolean inViewport(int x, int y, int camUpperLeftX, int maxX, int camUpperLeftY, int maxY) {
        if ((x + tileSize) >= camUpperLeftX && (x - tileSize) <= maxX && (y + tileSize) >= camUpperLeftY && (y - tileSize) <= maxY) {
            return true;
        } else {
            return false;
        }
    }   
}