
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
    private int x, y;
    private int spriteNumber;
    private int animationFrames;
    private SpriteSheet spritesheet;
    private Sprite sprite;

    public Player(int x, int y) {
        this.size = 32;
        this.x = x;
        this.y = y;
        this.spritesheet = SpriteSheet.charsheet;
        this.spriteNumber = 0;
        this.animationFrames = 0;
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

    public void draw(Graphics2D gfx, int tileSize) {
        // Slow animation over 20 frames
        if (animationFrames == 0) {
            animate();
            animationFrames = 20;
        }
        animationFrames--;
        gfx.drawImage(sprite.getSprite(), x, y, tileSize, tileSize, null);
    }

    public String toString() {
        return "Player at [" + x + ", " + y + "]";
    }

    private void animate() {
        if (spriteNumber == 0) {
            sprite = new Sprite(spritesheet, 1);
            spriteNumber = 1;
        } else if (spriteNumber == 1) {
            sprite = new Sprite(spritesheet, 0);
            spriteNumber = 0;
        }
    }
}