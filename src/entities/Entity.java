
/**
* ENTITY INTERFACE
* All entities move(), draw(), getX()/getY() and getCurrentX()/getCurrentY()
* They also utilize toggleInView(), isInView() and move accessors.
*/

package entities;

import java.awt.Graphics2D;


public interface Entity {
    public void move(int dx, int dy, boolean possible);
    public void draw(Graphics2D gfx, int tileSize, double interpolation);
    public int getX();
    public int getY();
    public int getCurrentX();
    public int getCurrentY();
    public void toggleInView();
    public boolean isInView();

    public int getMoves();
    public void resetMoves();
    public void decrementMoves();
}