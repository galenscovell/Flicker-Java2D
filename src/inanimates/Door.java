
/**
 * DOOR CLASS
 * Loads door sprite and handles interaction events.
 * @direction is either 0 (vertically facing) or 1 (horizontally facing).
 */

package inanimates;

import graphics.Sprite;
import graphics.SpriteSheet;

import logic.Tile;

import java.awt.Graphics2D;


public class Door implements Inanimate {
    private int x, y, tileSize;
    private Sprite[] sprites;
    private Sprite currentSprite;
    private boolean blocking;


    public Door(int x, int y, int tileSize, int direction) {
        this.x = x;
        this.y = y;
        this.tileSize = tileSize;

        SpriteSheet sheet = SpriteSheet.tilesheet;
        this.sprites = new Sprite[2];
        this.sprites[0] = new Sprite(sheet, 96 + direction);
        this.sprites[1] = new Sprite(sheet, 112 + direction);
        this.currentSprite = sprites[0];

        this.blocking = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void interact(Tile tile) {
        if (blocking) {
            this.currentSprite = sprites[1];
            tile.toggleBlocking();
            tile.toggleOccupied();
            blocking = false;
        } else {
            this.currentSprite = sprites[0];
            tile.toggleBlocking();
            tile.toggleOccupied();
            blocking = true;
        }
    }

    public boolean isBlocking() {
        return blocking;
    }

    public void draw(Graphics2D gfx) {
        gfx.drawImage(currentSprite.getSprite(), x * tileSize, y * tileSize, tileSize, tileSize, null);
    }
}