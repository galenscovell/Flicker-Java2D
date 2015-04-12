
/**
* ENTITY INTERFACE
* All entities move(), draw() and animate()
*/

package entities;

import java.awt.Graphics2D;


public interface Entity {
    public void move(int dx, int dy, boolean possible);
    public void draw(Graphics2D gfx, int tileSize);
}