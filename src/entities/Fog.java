
/**
 * FOG CLASS
 * Displays transparent fog sprite.
 */

package entities;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.IOException;


public class Fog {
    private BufferedImage image;
    private int x, y, size;
    private float alpha = 0.1f;
    private int frameSkip;

    public Fog() {
        try {
            this.image = ImageIO.read(Fog.class.getResource("/res/textures/fogAlpha.png"));
        } catch (IOException e) {
            System.err.println("Unable to load fog image.");
        }
        this.x = -64;
        this.y = -64;
        this.size = 4096;
        this.frameSkip = 2;
    }

    public void render(Graphics2D gfx) {
        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        gfx.setComposite(composite);
        gfx.drawImage(image, x, y, size, size, null);
        if (frameSkip == 0) {
            animate();
            frameSkip = 2;
        } else {
            frameSkip--;
        }
    }

    private void animate() {
        x--;
        y--;
        if ((x < -size / 4)) {
            x = 0;
            y = 0;
        }
    }
}