
/**
 * BITMASKER CLASS
 * Handles calculation of bitmask value for Tiles.
 *
 *    1        1       Total = (Sum of occupied values)
 *  8 T 2      T 2     ex total = (1 + 2) = 3
 *    4                Bitmask = binary value of total = 11
 *
 * Bitmask value range: 0, 1111 [0, 15] (None occupied, all occupied)
 * Bitmask value determines sprite of Tile.
 */

package logic;

import java.util.List;


public class Bitmasker {

    public int findBitmask(Tile tile, Tile[][] grid) {
        boolean top = false;
        boolean bottom = false;
        boolean left = false;
        boolean right = false;

        List<Point> neighbors = tile.getNeighbors();

        // Find neighbor positions
        for (Point neighbor : neighbors) {
            int x = neighbor.x;
            int y = neighbor.y;

            if (grid[x][y].isWall()) {
                int diffX = tile.x - x;
                int diffY = tile.y - y;

                if (diffX == -1) {
                    if (diffY == 0) {
                        right = true;
                    }
                } else if (diffX == 0) {
                    if (diffY == -1) {
                        bottom = true;
                    } else if (diffY == 1) {
                        top = true;
                    }
                } else if (diffX == 1) {
                    if (diffY == 0) {
                        left = true;
                    }
                }
            }
        }

        int value = 0;
        // Add Tile value if flagged as occupied
        if (top) value += 1;
        if (bottom) value += 4;
        if (left) value += 8;
        if (right) value += 2;

        if (value == 0) {
            return value;
        } else {
            int bitmask = calculateBinary(value);
            return bitmask;
        }
    }

    private int calculateBinary(int value) {
        int remainder;
        String strResult = "";

        while (value != 0) {
            remainder = value % 2;
            strResult += Integer.toString(remainder);
            value /= 2;
        }

        String reversed = "";
        for (int i = strResult.length() - 1; i >= 0; i--) {
            reversed += strResult.charAt(i);
        }

        int result = Integer.parseInt(reversed);
        return result;
    }
}