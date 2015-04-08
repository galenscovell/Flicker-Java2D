
/**
 * SPRITESHEET CLASS
 * Manages spritesheet loading and caching.
 */

package graphics;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;


public class SpriteSheet {
    private String path;
    public int SHEETX, SHEETY;
    private int spriteSize;
    public BufferedImage sheet;
    private BufferedImage[] subSprites;

    public static SpriteSheet charsheet = new SpriteSheet("/res/textures/charsheet.png", 16);
    public static SpriteSheet tilesheet = new SpriteSheet("/res/textures/tilesheet.png", 16);


    public SpriteSheet(String path, int spriteSize) {
        this.path = path;
        this.spriteSize = spriteSize;
        load();
        this.subSprites = getAllSubSprites();
    }

    public BufferedImage getSprite(int pos) {
        return subSprites[pos];
    }

    protected BufferedImage[] getAllSubSprites() {
        // Find all sprites wtihin spritesheet and load them to an array
        int partsX = SHEETX / spriteSize;
        int partsY = SHEETY / spriteSize;
        BufferedImage[] subSprites = new BufferedImage[partsX * partsY];

        for (int y = 0; y < partsY; y++) {
            for (int x = 0; x < partsX; x++) {
                subSprites[x + y * partsX] = getSubSprite(x * spriteSize, y * spriteSize);
            }
        }
        return subSprites;
    }

    protected BufferedImage getSubSprite(int x, int y) {
        // Extract sprite from total sprites by providing top-left coordinates
        BufferedImage subSprite = sheet.getSubimage(x, y, spriteSize, spriteSize);
        return subSprite;
    }

    private void load() {
        // Load spritesheet
        try {
            this.sheet = ImageIO.read(SpriteSheet.class.getResource(path));
            SHEETX = sheet.getWidth();
            SHEETY = sheet.getHeight();
        } catch (IOException e) {
            System.err.println("Unable to load: " + path);
        }
    }
}