
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
    private List<Tile> tiles;

    public World(int width, int height, int tileSize) {
        this.tileSize = tileSize;
        this.columns = width / tileSize;
        this.rows = height / tileSize;
        this.builder = new CaveBuilder(columns, rows);

        builder.build();
        this.tiles = builder.getTiles();
    }

    public void checkAdjacent() {
        Tile[][] grid = builder.getGrid();

        for (Tile tile : tiles) {
            int floorNeighbors = 0;
            List<Point> neighborPoints = tile.getNeighbors();
            for (Point point : neighborPoints) {
                if (grid[point.getX()][point.getY()].isFloor()) {
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

    public List<Tile> getTiles() {
        return tiles;
    }
}