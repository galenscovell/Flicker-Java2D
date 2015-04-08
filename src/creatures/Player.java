
/**
 * PLAYER CLASS
 * Displays player sprite and handles player coordinates.
 */

package creatures;

import graphics.Sprite;
import graphics.SpriteSheet;

import java.awt.Graphics2D;


public class Player {
    public int x, y, size;
    private int spriteNumber, waitFrames;

    private SpriteSheet sheet;
    private Sprite sprite;
    private Sprite[] currentSet;
    private Sprite[] upSprites, downSprites, leftSprites, rightSprites;


    public Player(int x, int y, int size) {
        this.size = size;
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

        this.currentSet = downSprites;
        this.sprite = currentSet[0];
        this.spriteNumber = 0;
        this.waitFrames = 20;
    }

    public void move(int dx, int dy) {
        if (dy < 0) {
            currentSet = upSprites;
        } else if (dy > 0) {
            currentSet = downSprites;
        } else if (dx < 0) {
            currentSet = leftSprites;
        } else if (dx > 0) {
            currentSet = rightSprites;
        }
        animate(currentSet);
        x += dx;
        y += dy;
    }

    public void draw(Graphics2D gfx, int tileSize) {
        animate(currentSet);
        gfx.drawImage(sprite.getSprite(), x, y, size, size, null);
    }

    public String toString() {
        return "Player at [" + x + ", " + y + "]";
    }

    private void animate(Sprite[] currentSet) {
        if (waitFrames == 0) {
            spriteNumber++;
            waitFrames = 20;
            if (spriteNumber > 3) {
                spriteNumber = 0;
            }
        } else {
            waitFrames--;
        }
        sprite = currentSet[spriteNumber];
    }
}