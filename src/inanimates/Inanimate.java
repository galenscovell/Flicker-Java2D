
/**
 * INANIMATE INTERFACE
 * All inanimates have getX(), getY(), interact() and draw().
 */

package inanimates;

import logic.Tile;

import java.awt.Graphics2D;


public interface Inanimate {
    public int getX();
    public int getY();
    public void interact(Tile tile);
    public boolean isBlocking();
    public void draw(Graphics2D gfx);
}