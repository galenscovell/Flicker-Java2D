
/**
 * TILE CLASS
 * Keeps track of tile position, state and rendering of Tile sprite.
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

    public void draw(Graphics2D gfx, int tileSize) {
        int screenX = x * tileSize;
        int screenY = y * tileSize;
        gfx.drawImage(sprite.getSprite(), screenX, screenY, tileSize, tileSize, null);
    }

    public void findSprite() {
        SpriteSheet sheet = SpriteSheet.tilesheet;

        if (isFloor()) {
            sprite = new Sprite(sheet, 7);
        } else if (isPerimeter()){
            switch (bitmask) {
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
                    sprite = new Sprite(sheet, 5);
                    break;
                default:
                    state = 1;
                    sprite = new Sprite(sheet, 7);
            }
        }
    }

    private List<Point> findNeighbors(int columns, int rows) {
        // Compute neighboring tiles once during object construction
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
        if (i < 0 || j < 0){
            return true;
        } else if (i >= columns || j >= rows){
            return true;
        } else {
            return false;
        }
    }
}