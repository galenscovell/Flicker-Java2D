
/**
 * SALAMANDER CLASS
 * Handles Salamander creature behavior and rendering.
 */

package entities;

import graphics.Sprite;
import graphics.SpriteSheet;

import java.awt.Graphics2D;


public class Salamander implements Entity {
    public int x, y, prevX, prevY;
    private int spriteNumber, waitFrames;

    private SpriteSheet sheet;
    private Sprite sprite;
    private Sprite[] currentSet;
    private Sprite[] leftSprites, rightSprites;


    public Salamander(int x, int y) {
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
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

    public void move(int dx, int dy, boolean possible) {

    }

    public void draw(Graphics2D gfx, int tileSize) {
        animate(currentSet);
        gfx.drawImage(sprite.getSprite(), x, y, tileSize, tileSize, null);
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