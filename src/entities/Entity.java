
/**
* ENTITY INTERFACE
* All entities move(), draw(), attack(), and getX()/getY()
* They also utilize toggleInView(), isInView() and have movement/attack accessors.
*/

package entities;

import java.awt.Graphics2D;


public interface Entity {
    public void move(int dx, int dy, boolean possible);
    public void draw(Graphics2D gfx, int tileSize, double interpolation);
    public void attack(Graphics2D gfx, int tileSize, double interpolation, Player player);
    
    public int getX();
    public int getY();
    public void toggleInView();
    public boolean isInView();
    public boolean isAttacking();
    public void toggleAttacking();
    public boolean isMoveTime();
    public void resetMoveTime();
    public void incrementMoveTime();
}