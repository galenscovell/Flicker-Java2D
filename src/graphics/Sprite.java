
/**
 * SPRITE CLASS
 * Extract sprite from spritesheet.
 */

package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Sprite {
    private BufferedImage sprite;
    private int size;
    private SpriteSheet spritesheet;


    public Sprite(SpriteSheet sheet, int sheetPos) {
        this.spritesheet = sheet;
        this.sprite = spritesheet.getSprite(sheetPos);
        this.size = sprite.getHeight();
    }

    public Sprite(int size, Color color) {
        Graphics2D gfx;
        this.size = size;
        this.sprite = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        gfx = sprite.createGraphics();
        gfx.setColor(color);
        gfx.fillRect(0, 0, sprite.getWidth(), sprite.getHeight());
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public int getSize() {
        return sprite.getHeight();
    }
}
