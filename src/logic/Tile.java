
/**
 * TILE CLASS
 * Keeps track of tile position, state and rendering.
 * State can be Wall(0), Floor(1), Corridor(2) or Perimeter(3)
 */

package logic;

import graphics.Sprite;
import graphics.SpriteSheet;

import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.List;


public class Tile {
    public int x, y, state;
    private int floorNeighbors;
    private List<Point> neighborTilePoints;
    private int bitmask;
    private Sprite sprite;
    private boolean occupied, blocking;


    public Tile(int x, int y, int columns, int rows) {
        this.x = x;
        this.y = y;
        this.state = 0;
        this.bitmask = 0;
        this.neighborTilePoints = findNeighbors(columns, rows);
    }

    public boolean isWall() {
        return state == 0;
    }

    public boolean isFloor() {
        return state == 1;
    }

    public boolean isCorridor() {
        return state == 2;
    }

    public boolean isPerimeter() {
        return state == 3;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void toggleOccupied() {
        if (occupied) {
            occupied = false;
        } else {
            occupied = true;
        }
    }

    public boolean isBlocking() {
        return blocking;
    }

    public void toggleBlocking() {
        if (blocking) {
            blocking = false;
        } else {
            blocking = true;
        }
    }

    public void setFloorNeighbors(int value) {
        floorNeighbors = value;
    }

    public int getFloorNeighbors() {
        return floorNeighbors;
    }

    public List<Point> getNeighbors() {
        return neighborTilePoints;
    }

    public void setBitmask(int value) {
        bitmask = value;
    }

    public int getBitmask() {
        return bitmask;
    }

    public void draw(Graphics2D gfx, int tileSize) {
        gfx.drawImage(sprite.getSprite(), x * tileSize, y * tileSize, tileSize, tileSize, null);
    }

    public void findSprite() {
        SpriteSheet sheet = SpriteSheet.tilesheet;
        if (isPerimeter()) {
            switch (bitmask) {
                case 0:
                    sprite = new Sprite(sheet, 53);
                    break;
                case 1:
                case 100:
                case 101:
                    sprite = new Sprite(sheet, 16);
                    break;
                case 10:
                case 1000:
                case 1010:
                    sprite = new Sprite(sheet, 1);
                    break;
                case 11:
                    sprite = new Sprite(sheet, 32);
                    break;
                case 110:
                    sprite = new Sprite(sheet, 0);
                    break;
                case 111:
                    sprite = new Sprite(sheet, 19);
                    break;
                case 1001:
                    sprite = new Sprite(sheet, 34);
                    break;
                case 1011:
                    sprite = new Sprite(sheet, 36);
                    break;
                case 1100:
                    sprite = new Sprite(sheet, 2);
                    break;
                case 1101:
                    sprite = new Sprite(sheet, 21);
                    break;
                case 1110:
                    sprite = new Sprite(sheet, 4);
                    break;
                case 1111:
                default:
                    state = 1;
                    sprite = new Sprite(sheet, 5);
            }
        } else if (isFloor()) {
            switch (bitmask) {
                case 0:
                    sprite = new Sprite(sheet, 65);
                    break;
                case 1:
                    sprite = new Sprite(sheet, 49);
                    break;
                case 10:
                    sprite = new Sprite(sheet, 66);
                    break;
                case 11:
                    sprite = new Sprite(sheet, 50);
                    break;
                case 100:
                    sprite = new Sprite(sheet, 81);
                    break;
                case 101:
                    sprite = new Sprite(sheet, 69);
                    break;
                case 110:
                    sprite = new Sprite(sheet, 82);
                    break;
                case 111:
                    sprite = new Sprite(sheet, 70);
                    break;
                case 1000:
                    sprite = new Sprite(sheet, 64);
                    break;
                case 1001:
                    sprite = new Sprite(sheet, 48);
                    break;
                case 1010:
                    sprite = new Sprite(sheet, 67);
                    break;
                case 1011:
                    sprite = new Sprite(sheet, 51);
                    break;
                case 1100:
                    sprite = new Sprite(sheet, 80);
                    break;
                case 1101:
                    sprite = new Sprite(sheet, 68);
                    break;
                case 1110:
                    sprite = new Sprite(sheet, 83);
                    break;
                case 1111:
                    sprite = new Sprite(sheet, 53);
                    break;
                default:
                    sprite = new Sprite(sheet, 65);
            }
        }
    }

    private List<Point> findNeighbors(int columns, int rows) {
        List<Point> points = new ArrayList<Point>();
        int sumX, sumY;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                sumX = x + i;
                sumY = y + j;

                if ((sumX == x && sumY == y || (isOutOfBounds(sumX, sumY, columns, rows)))) {
                    continue;
                }
                points.add(new Point(sumX, sumY));
            }
        }
        return points;
    }

    private boolean isOutOfBounds(int i, int j, int columns, int rows) {
        if (i < 0 || j < 0) {
            return true;
        } else if (i >= columns || j >= rows) {
            return true;
        } else {
            return false;
        }
    }
}