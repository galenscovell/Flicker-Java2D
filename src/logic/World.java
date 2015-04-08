
/**
 * WORLD CLASS
 * World is composed of a 2D array grid and a list of matching Tile instances.
 */

package logic;

import java.util.List;


public class World {
    private int tileSize;
    private int columns, rows;
    private Builder builder;
    private List<Tile> tiles;


    public World(int width, int height, int tileSize) {
        this.tileSize = tileSize;
        this.columns = width / tileSize;
        this.rows = height / tileSize;
        this.builder = new DungeonBuilder(columns, rows);

        builder.build();
        this.tiles = builder.getTiles();
    }

    public void checkAdjacent() {
        Tile[][] grid = builder.getGrid();

        for (Tile tile : tiles) {
            int floorNeighbors = 0;
            List<Point> neighborPoints = tile.getNeighbors();
            for (Point point : neighborPoints) {
                if (grid[point.x][point.y].isFloor()) {
                    floorNeighbors++;
                }
            }
            tile.setFloorNeighbors(floorNeighbors);
        }
    }

    public void update() {
        checkAdjacent();

        for (Tile tile : tiles) {
            builder.smooth(tile);
        }
    }

    public void skin() {
        Tile[][] grid = builder.getGrid();
        Bitmasker bitmasker = new Bitmasker();
        int value;

        for (Tile tile : tiles) {
            if (tile.isWall()) {
                value = bitmasker.findBitmask(tile, grid);
                tile.setBitmask(value);
            }
        }

        for (Tile tile : tiles) {
            tile.findSprite();
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}