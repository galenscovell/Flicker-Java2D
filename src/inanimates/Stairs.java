
/**
 * STAIRS CLASS
 * Loads stair sprite and handles interaction events.
 */

package inanimates;

import graphics.Sprite;
import graphics.SpriteSheet;

import logic.Tile;

import java.awt.Graphics2D;


public class Stairs implements Inanimate {
    private int x, y, tileSize;
    private boolean blocking;
    private Sprite sprite;


    public Stairs(int x, int y, int tileSize) {
        this.x = x;
        this.y = y;
        this.tileSize = tileSize;

        SpriteSheet sheet = SpriteSheet.tilesheet;
        this.sprite = new Sprite(sheet, 98);

        this.blocking = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return "Stairs";
    }

    public void interact(Tile tile) {
        
    }

    public boolean isBlocking() {
        return blocking;
    }

    public void draw(Graphics2D gfx) {
        gfx.drawImage(sprite.getSprite(), x * tileSize, y * tileSize, tileSize, tileSize, null);
    }
}