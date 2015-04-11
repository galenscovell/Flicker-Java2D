
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
                // Otherwise add Tile to remove list
                } else {
                    pruned.add(tile);
                }
            }
        }

        // Set perimeter Tiles not on perimeter to be floor Tiles
        int wallNeighbors;
        for (Tile tile : tiles) {
            if (tile.isPerimeter()) {
                wallNeighbors = 0;
                for (Point neighbor : tile.getNeighbors()) {
                    if (grid[neighbor.x][neighbor.y].isWall()) {
                        wallNeighbors++;
                    }
                }
                if (wallNeighbors == 0) {
                    tile.state = 1;
                }
            }
        }

        // If Tile is on world boundary or has adjacent non-perimeter wall, make it perimeter
        for (Tile tile : tiles) {
            if (tile.isFloor()) {
                wallNeighbors = 0;
                for (Point neighbor : tile.getNeighbors()) {
                    if (grid[neighbor.x][neighbor.y].isWall()) {
                        wallNeighbors++;
                    }
                }
                if (wallNeighbors > 0 || tile.getNeighbors().size() < 8) {
                    tile.state = 3;
                }
            }
        }

        // Recheck Tiles for floor neighbors, if floor exists without any adjacent
        // floor Tiles remove it. If Tile has only one adjacent floor make it perimeter.
        checkAdjacent();
        for (Tile tile : tiles) {
            if (tile.getFloorNeighbors() == 1) {
                tile.state = 3;
            } else if (tile.getFloorNeighbors() == 0) {
                pruned.add(tile);
            }
        }

        // Remove pruned Tiles from Tiles list
        for (Tile tile : pruned) {
            tiles.remove(tile);
        }

        for (Tile tile : tiles) {
            if (tile.isPerimeter() || tile.isFloor()) {
                tile.setBitmask(bitmasker.findBitmask(tile, grid));
            }
        }

        for (Tile tile : tiles) {
            tile.findSprite();
        }

        builder = null;
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}