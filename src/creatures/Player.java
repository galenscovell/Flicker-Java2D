
/**
 * PLAYER CLASS
 * Displays player sprite and handles player coordinates.
 */

package creatures;

import graphics.Sprite;
import graphics.SpriteSheet;

import java.awt.Graphics2D;


public class Player {
    private int size;
    private int x, y;
    private int spriteNumber;
    private int waitFrames;

    private SpriteSheet sheet;
    private Sprite[] spriteSet;
    private Sprite sprite;

    private Sprite[] upSprites;
    private Sprite[] downSprites;
    private Sprite[] leftSprites;
    private Sprite[] rightSprites;


    public Player(int x, int y) {
        this.size = 32;
        this.x = x;
        this.y = y;
        this.sheet = SpriteSheet.charsheet;

        this.upSprites = new Sprite[4];
        this.downSprites = new Sprite[4];
        this.leftSprites = new Sprite[4];
        this.rightSprites = new Sprite[4];

        // Populate sprite animation sets
        for (int i = 0; i < 4; i++) {
            upSprites[i] = new Sprite(sheet, i + 48);
            downSprites[i] = new Sprite(sheet, i);
            leftSprites[i] = new Sprite(sheet, i + 16);
            rightSprites[i] = new Sprite(sheet, i + 32);
        }

        this.spriteSet = downSprites;
        this.sprite = spriteSet[0];
        this.spriteNumber = 0;
        this.waitFrames = 20;
    }

    public void move(int dx, int dy) {
        if (dy < 0) {
            spriteSet = upSprites;
        } else if (dy > 0) {
            spriteSet = downSprites;
        } else if (dx < 0) {
            spriteSet = leftSprites;
        } else if (dx > 0) {
            spriteSet = rightSprites;
        }
        animate(spriteSet);
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
        animate(spriteSet);
        gfx.drawImage(sprite.getSprite(), x, y, size, size, null);
    }

    public String toString() {
        return "Player at [" + x + ", " + y + "]";
    }

    private void animate(Sprite[] spriteSet) {
        if (waitFrames == 0) {
            spriteNumber++;
            waitFrames = 20;
            if (spriteNumber > 3) {
                spriteNumber = 0;
            }
        } else {
            waitFrames--;
        }
        sprite = spriteSet[spriteNumber];
    }
}