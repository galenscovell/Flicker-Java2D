
/**
 * PLAYER CLASS
 * Displays player sprite and handles player coordinates.
 */

package entities;

import graphics.Sprite;
import graphics.SpriteSheet;

import java.awt.Graphics2D;


public class Player {
    private int x, y, prevX, prevY, currentX, currentY;
    private int spriteNumber, waitFrames;
    private int agi;

    private SpriteSheet sheet;
    private Sprite sprite;
    private Sprite[] currentSet;
    private Sprite[] upSprites, downSprites, leftSprites, rightSprites;


    public Player(int x, int y) {
        this.x = x;
        this.y = y; 
        this.prevX = x;
        this.prevY = y;
        this.currentX = x;
        this.currentY = y;
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

        this.agi = 4;
    }

    public int getAgi() {
        return agi;
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

    public void move(int dx, int dy, boolean possible) {
        if (dy < 0) {
            currentSet = upSprites;
        } else if (dy > 0) {
            currentSet = downSprites;
        } else if (dx < 0) {
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
        // When interpolation is 1, movement animation is complete
        if (interpolation == 1.0) {
            prevX = x;
            prevY = y;
            gfx.drawImage(sprite.getSprite(), x, y, tileSize, tileSize, null);
            return;
        }
        currentX = (int) (prevX + ((x - prevX) * interpolation));
        currentY = (int) (prevY + ((y - prevY) * interpolation));
        
        gfx.drawImage(sprite.getSprite(), currentX, currentY, tileSize, tileSize, null);
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