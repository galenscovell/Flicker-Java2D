
/**
 * DOOR CLASS
 * Loads door sprite and handles interaction events.
 */

package inanimates;

import graphics.Sprite;
import graphics.SpriteSheet;

import java.awt.Graphics2D;


public class Door implements Inanimate {
    private int x, y, tileSize;
    private Sprite sprite;


    public Door(int x, int y, int tileSize, int direction) {
        this.x = x;
        this.y = y;
        this.tileSize = tileSize;

        SpriteSheet sheet = SpriteSheet.tilesheet;
        this.sprite = new Sprite(sheet, 96 + direction);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void interact() {

    }

    public void draw(Graphics2D gfx) {
        gfx.drawImage(sprite.getSprite(), x, y, tileSize, tileSize, null);
    }
}