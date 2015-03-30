
/**
 * WORLD CLASS
 * World is composed of a 2D array grid and a list of matching Tile instances.
 */

package logic;

import java.awt.Color;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.List;


public class World {
    private int tileSize;
    private int columns;
    private int rows;

    private Builder builder;
    public int[][] grid;
    private List<Tile> tiles;
    

    public World(int width, int height, int tileSize) {
        this.tileSize = tileSize;
        this.columns = width / tileSize;
        this.rows = height / tileSize;

        this.builder = new CaveBuilder(columns, rows);
        builder.build();

        this.grid = builder.getGrid();
        this.tiles = builder.getTiles();
    }

    public int checkAdjacent(Tile tile) {
        int sumX, sumY;
        int floorNeighbors = 0;
                
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                sumX = tile.getX() + x;
                sumY = tile.getY() + y;
                if (sumX == tile.getX() && sumY == tile.getY()) {
                    continue;
                }
                if (isOutOfBounds(sumX, sumY)) {
                    continue;
                }
                if (grid[sumX][sumY] == 1) {
                    floorNeighbors++;
                }
            }
        }
        return floorNeighbors;
    }

    public boolean isOutOfBounds(int x, int y) {
        if (x < 0 || y < 0){
            return true;
        } else if (x >= columns || y >= rows){
            return true;
        } else {
            return false;
        }
    }

    public void update() {
        for (Tile tile : tiles) {
            tile.updateNeighbors(checkAdjacent(tile));
        }
        for (Tile tile : tiles) {
            builder.smooth(tile);
        }
    }

    public void render(Graphics2D gfx) {
        int screenX, screenY;
        Color floor    = new Color(0x34495e);
        Color wall     = new Color(0x2c3e50);
        Color active   = new Color(0xecf0f1);

        for (Tile tile : tiles) {
            if (tile.isFloor(grid)) {
                gfx.setColor(floor);
            } else if (tile.isWall(grid)) {
                gfx.setColor(wall);
            } else {
                gfx.setColor(active);
            }
            screenX = (tile.getX() * tileSize) + 1;
            screenY = (tile.getY() * tileSize) + 1;
            gfx.fillRect(screenX, screenY, tileSize, tileSize);
        }
    }
}