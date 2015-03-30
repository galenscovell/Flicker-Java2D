
/**
 * WORLD CLASS
 * World is composed of a 2D array grid and a list of matching Tile instances.
 */

package logic;

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

    public int[][] getGrid() {
        return builder.getGrid();
    }

    public List<Tile> getTiles() {
        return builder.getTiles();
    }
}