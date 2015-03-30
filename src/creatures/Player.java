
/**
 * PLAYER CLASS
 * Displays player sprite.
 */

package creatures;

import java.awt.Color;
import java.awt.Graphics2D;


public class Player {
    private int size;
    private int x;
    private int y;

    public Player(int x, int y) {
        this.size = 6;
        this.x = x;
        this.y = y;
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
        gfx.setColor(new Color(0x2ecc71));
        gfx.fillRect(x, y, size, size);
    }

    public String toString() {
        return "Player at [" + x + ", " + y + "]";
    }
}