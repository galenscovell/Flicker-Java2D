
/**
 * SALAMANDER CLASS
 * Handles Salamander creature behavior and rendering.
 */

package entities;

import graphics.Sprite;
import graphics.SpriteSheet;

import java.awt.Graphics2D;


public class Salamander implements Entity {
    private int x, y, prevX, prevY, currentX, currentY;
    private int spriteNumber, waitFrames;
    private boolean inView;

    private SpriteSheet sheet;
    private Sprite sprite;
    private Sprite[] currentSet;
    private Sprite[] leftSprites, rightSprites;


    public Salamander(int x, int y) {
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.currentX = x;
        this.currentY = y;
        this.sheet = SpriteSheet.charsheet;

        this.leftSprites = new Sprite[2];
        this.rightSprites = new Sprite[2];

        // Populate sprite animation sets
        for (int i = 0; i < 2; i++) {
            leftSprites[i] = new Sprite(sheet, i + 80);
            rightSprites[i] = new Sprite(sheet, i + 82);
        }

        this.currentSet = leftSprites;
        this.sprite = currentSet[0];
        this.spriteNumber = 0;
        this.waitFrames = 20;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void toggleInView() {
        if (inView) {
            inView = false;
        } else {
            inView = true;
        }
    }

    public boolean isInView() {
        return inView;
    }

    public void move(int dx, int dy, boolean possible) {
        prevX = x;
        prevY = y;

        if (dx < 0) {
            currentSet = leftSprites;
        } else if (dx > 0) {
            currentSet = rightSprites;
        }

        if (possible) {
            animate(currentSet);
            x += dx;
            y += dy;
        }
    }

    public void draw(Graphics2D gfx, int tileSize, double interpolation) {
        animate(currentSet);
        currentX = (int) (prevX + ((x - prevX) * interpolation));
        currentY = (int) (prevY + ((y - prevY) * interpolation));
        
        gfx.drawImage(sprite.getSprite(), currentX, currentY, tileSize, tileSize, null);
    }

    private void animate(Sprite[] currentSet) {
        if (waitFrames == 0) {
            spriteNumber++;
            waitFrames = 20;
            if (spriteNumber > 1) {
                spriteNumber = 0;
            }
        } else {
            waitFrames--;
        }
        sprite = currentSet[spriteNumber];
    }
}