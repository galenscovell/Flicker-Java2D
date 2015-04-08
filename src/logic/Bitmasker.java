
/**
 * BITMASKER CLASS
 * Handles calculation of bitmask value for Tiles.
 *
 *   1  32  2   * 32  2       Bitmask value = (Sum of occupied values)
 *  16   T 64   *  T 64       ex total = (32 + 2 + 64) = 98
 *   8 128  4   *  *  * 
 *
 * Bitmask value range: 0, 255 (None occupied, all occupied)
 * Bitmask value determines sprite of Tile.
 */

package logic;

import java.util.List;


public class Bitmasker {

    public int findBitmask(Tile tile) {
        boolean topLeft = false; 
        boolean top = false;
        boolean topRight = false;
        boolean left = false;
        boolean right = false;
        boolean bottomLeft = false;
        boolean bottom = false;
        boolean bottomRight = false;

        List<Point> neighbors = tile.getNeighbors();

        // Find neighbor positions
        for (Point neighbor : neighbors) {
            int diffX = tile.getX() - neighbor.getX();
            int diffY = tile.getY() - neighbor.getY();

            if (diffX == -1) {
                if (diffY == -1) {
                    bottomRight = true;
                } else if (diffY == 0) {
                    right = true;
                } else if (diffY == 1) {
                    topRight = true;
                }
            } else if (diffX == 0) {
                if (diffY == -1) {
                    bottom = true;
                } else if (diffY == 1) {
                    top = true;
                }
            } else if (diffX == 1) {
                if (diffY == -1) {
                    bottomLeft = true;
                } else if (diffY == 0) {
                    left = true;
                } else if (diffY == 1) {
                    topLeft = true;
                }
            }
        }

        int bitmask = 0;
        // Add Tile value if flagged as occupied
        if (topLeft) bitmask += 1;
        if (top) bitmask += 32;
        if (topRight) bitmask += 2;
        if (left) bitmask += 16;
        if (right) bitmask += 64;
        if (bottomLeft) bitmask += 8;
        if (bottom) bitmask += 128;
        if (bottomRight) bitmask += 4;

        return bitmask;
    }
}