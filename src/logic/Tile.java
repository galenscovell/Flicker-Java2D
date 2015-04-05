
/**
 * TILE CLASS
 * Keeps track of tile position and state and tile drawing.
 * State can be one of Wall(0), Floor(1)
 */

package logic;

import graphics.Sprite;
import graphics.SpriteSheet;

import java.awt.Color;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.List;


public class Tile {
    private int x;
    private int y;
    private int state;
    private int floorNeighbors;
    private List<Point> neighboringTiles;
    private Sprite sprite;

    public Tile(int x, int y, int state, int columns, int rows) {
        this.x = x;
        this.y = y;
        this.state = state;
        this.neighboringTiles = findNeighbors(columns, rows);
    }

    public boolean isWall() {
        if (state == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFloor() {
        if (state == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void setFloorNeighbors(int value) {
        floorNeighbors = value;
    }

    public int getFloorNeighbors() {
        return floorNeighbors;
    }

    public void setState(int value) {
        state = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Point> getNeighbors() {
        return neighboringTiles;
    }

    public void draw(Graphics2D gfx, int tileSize) {
        int screenX = x * tileSize;
        int screenY = y * tileSize;

        if (this.isFloor()) {
            this.sprite = new Sprite(SpriteSheet.tilesheet, 3);
            gfx.setColor(new Color(0x2c3e50));
        } else if (this.isWall()) {
            this.sprite = new Sprite(SpriteSheet.tilesheet, 16);
            gfx.setColor(new Color(0x2c3e50));
        }
        gfx.fillRect(screenX, screenY, tileSize, tileSize);
        gfx.drawImage(sprite.getSprite(), screenX, screenY, tileSize, tileSize, null);
    }

    private List<Point> findNeighbors(int columns, int rows) {
        // Compute neighboring tiles only once at object construction
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