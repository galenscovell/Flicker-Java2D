
/**
 * BUILDER INTERFACE
 * All builders utilize build(), smooth() and getTiles()
 */

package logic;

import java.util.Map;


public interface Builder {
    public void build();
    public void smooth(Tile tile);
    public Map<Integer, Tile> getTiles();
}