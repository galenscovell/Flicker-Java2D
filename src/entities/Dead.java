
/**
 * DEAD CLASS
 * Enemy death sprite loading and rendering.
 */

package entities;

import graphics.Sprite;
import graphics.SpriteSheet;

import java.awt.Graphics2D;

import java.util.Random;


public class Dead {
    private int x, y, tileSize;
    private Sprite sprite;


    public Dead(int x, int y, int tileSize) {
        this.x = x;
        this.y = y;
        this.tileSize = tileSize;
        sprite = setSprite();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics2D gfx) {
        gfx.drawImage(sprite.getSprite(), x * tileSize, y * tileSize, tileSize, tileSize, null);
    }

    private Sprite setSprite() {
        SpriteSheet sheet = SpriteSheet.charsheet;
        Random random = new Random();

        int choice = random.nextInt(4);
        if (choice == 0) {
            return new Sprite(sheet, 144);
        } else if (choice == 1) {
            return new Sprite(sheet, 145);
        } else if (choice == 2) {
            return new Sprite(sheet, 146);
        } else {
            return new Sprite(sheet, 147);
        }
    }
}