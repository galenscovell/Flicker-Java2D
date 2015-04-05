
/**
 * SPRITE CLASS
 * Extract sprite from spritesheet and convert pixels into image.
 */

package graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;


public class Sprite {
    private final int SPRITESIZE;
    private int sheetX;
    private int sheetY;
    private int[] pixels;
    private SpriteSheet sheet;

    public Sprite(int size, int x, int y, SpriteSheet sheet) {
        this.SPRITESIZE = size;
        this.sheetX = x * SPRITESIZE; // x position on spritesheet
        this.sheetY = y * SPRITESIZE; // y position on spritesheet
        this.pixels = new int[SPRITESIZE * SPRITESIZE * 4];
        this.sheet = sheet;
        load();
    }

    private void load() {
        // Extract sprite from spritesheet
        // Set pixels for this sprite = pixels within specific range in spritesheet
        for (int y = 0; y < SPRITESIZE; y++) {
            for (int x = 0; x < SPRITESIZE; x++) {
                this.pixels[x + (y * SPRITESIZE)] = sheet.getPixels((x + sheetX) + (y + sheetY) * sheet.getSize());
            }
        }

        int count = 1;
        for (int val : pixels) {
            System.out.println("Pixel: " + count + ", Value: " + val);
            count++;
        }
    }

    public Image getImageFromPixels() {
        BufferedImage image = new BufferedImage(SPRITESIZE, SPRITESIZE, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = (WritableRaster) image.getData();
        raster.setPixels(0, 0, SPRITESIZE, SPRITESIZE, this.pixels);
        image.setData(raster);
        return image;
    }
}