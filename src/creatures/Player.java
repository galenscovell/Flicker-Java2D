
/**
 * PLAYER CLASS
 * Displays player sprite.
 */

package creatures;

import graphics.Sprite;
import graphics.SpriteSheet;

import java.awt.Color;
import java.awt.Graphics2D;


public class Player {
    private int size;
    private int x;
    private int y;
    private Sprite sprite;

    public Player(int x, int y) {
        this.size = 32;
        this.x = x;
        this.y = y;
        this.sprite = new Sprite(SpriteSheet.charsheet, 0);
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

    public void draw(Graphics2D gfx) {
        gfx.drawImage(sprite.getSprite(), x, y, 32, 32, null);
    }

    public String toString() {
        return "Player at [" + x + ", " + y + "]";
    }
}