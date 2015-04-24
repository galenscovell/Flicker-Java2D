
/**
 * CAVEBUILDER CLASS
 * Constructs a new world grid and tileset with cave features. 
 * (large, open areas)
 */

package logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class CaveBuilder implements Builder {
    private int rows, columns;
    private Tile[][] grid;


    public CaveBuilder(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        this.grid = new Tile[rows][columns];
    }

    public void build() {
        Random random = new Random();
        int chance, state;

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                grid[y][x] = new Tile(x, y, columns, rows);
                chance = random.nextInt(100);
                if (chance < 40) {
                    grid[y][x].state = 1;
                } else {
                    grid[y][x].state = 0;
                }
            }
        }
    }

    public void smooth(Tile tile) {
        if (tile.getFloorNeighbors() > 3) {
            tile.state = 1;
        } else if (tile.getFloorNeighbors() < 3) {
            tile.state = 0;
        }
    }

    public Map<Integer, Tile> getTiles() {
        Map<Integer, Tile> tiles = new HashMap<Integer, Tile>();
        int key;
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                key = x * columns + y;
                tiles.put(key, grid[y][x]);
            }
        }
        return tiles;
    }
}