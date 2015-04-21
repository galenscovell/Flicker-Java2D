
/**
 * SALAMANDER CLASS
 * Handles Salamander creature sprites and stats.
 */

package entities;

import graphics.Sprite;
import graphics.SpriteSheet;


public class Salamander extends Creature {

    public Salamander(int x, int y) {
        super(x, y);
        setSprites();
        setStats();
    }

    private void setStats() {
        speed = 2;
        strength = 2;
    }

    private void setSprites() {
        SpriteSheet sheet = SpriteSheet.charsheet;
        leftSprites = new Sprite[2];
        rightSprites = new Sprite[2];

        // Populate sprite animation sets
        for (int i = 0; i < 2; i++) {
            leftSprites[i] = new Sprite(sheet, i + 80);
            rightSprites[i] = new Sprite(sheet, i + 82);
        }

        currentSet = leftSprites;
        sprite = currentSet[0];
    }
}