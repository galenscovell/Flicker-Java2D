
/**
 * WORLD CLASS
 * World is composed of a 2D array grid and a list of matching Tile instances.
 */

package logic;

import java.util.ArrayList;
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
            int value = 0;
            List<Point> neighborPoints = tile.getNeighbors();
            for (Point point : neighborPoints) {
                if (grid[point.x][point.y].isFloor()) {
                    value++;
                }
            }
            tile.setFloorNeighbors(value);
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
        List<Tile> pruned = new ArrayList<Tile>();

        // If Tile is a wall connected to a floor Tile, it becomes a perimeter Tile
        for (Tile tile : tiles) {
            if (tile.isWall()) {
                if (tile.getFloorNeighbors() > 0) {
                    tile.state = 3;
                } else {
                    pruned.add(tile);
                }
            }
        }

        // Remove non-perimeter walls from Tiles list
        for (Tile tile : pruned) {
            tiles.remove(tile);
        }

        // Find bitmask for perimeter Tiles
        for (Tile tile : tiles) {
            if (tile.isPerimeter()) {
                tile.setBitmask(bitmasker.findBitmask(tile, grid));
            }
        }

        // Remove builder reference
        builder = null;

        // Find sprites for remaining Tiles
        for (Tile tile : tiles) {
            tile.findSprite();
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}