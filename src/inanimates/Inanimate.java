
/**
 * INANIMATE INTERFACE
 * All inanimates have getX(), getY(), interact() and draw().
 */

package inanimates;

import java.awt.Graphics2D;


public interface Inanimate {
    public int getX();
    public int getY();
    public void interact();
    public void draw(Graphics2D gfx);
}