
/**
 * SPRITESHEET CLASS
 * Manages spritesheet loading and caching.
 */

package graphics;


import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;


public class SpriteSheet {
    private String path;
    private final int SHEETSIZE;
    private int[] pixels;

    public SpriteSheet(String path, int size) {
        this.path = path;
        this.SHEETSIZE = size;
        this.pixels = new int[SHEETSIZE * SHEETSIZE];
        load();
        System.out.println("Sheetsize: " + SHEETSIZE);
        System.out.println("Sheet pixels: " + pixels.length);
    }

    private void load() {
        try {
            BufferedImage image = ImageIO.read(getClass().getResource(path));
            int width = image.getWidth();
            int height = image.getHeight();
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getSize() {
        return SHEETSIZE;
    }

    public int getPixels(int x) {
        return pixels[x];
    }
}