
/**
 * PLAYER CLASS
 * Displays player sprite.
 */

package creatures;

import graphics.Sprite;
import graphics.SpriteSheet;

import java.awt.Color;
import java.awt.Graphics;


public class Player {
    private int size;
    private int x;
    private int y;
    private Sprite sprite;
    private SpriteSheet sheet;

    public Player(int x, int y) {
        this.size = 16;
        this.x = x;
        this.y = y;
        this.sheet = new SpriteSheet("/res/textures/charsheet.png", 256);
        this.sprite = new Sprite(16, 0, 0, sheet);
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics gfx) {
        gfx.drawImage(sprite.getImageFromPixels(), x, y, null);
    }

    public String toString() {
        return "Player at [" + x + ", " + y + "]";
    }
}