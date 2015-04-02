
/**
 * TILE CLASS
 * Keeps track of tile position and state.
 * State can be one of Wall(0), Floor(1)
 */

package logic;


public class Tile {
    private int x;
    private int y;
    private int floorNeighbors;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isWall(int[][] grid) {
        if (grid[x][y] == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFloor(int[][] grid) {
        if (grid[x][y] == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void updateNeighbors(int value) {
        floorNeighbors = value;
    }

    public int getNeighbors() {
        return floorNeighbors;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}