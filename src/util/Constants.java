
/**
 * CONSTANTS CLASS
 * Storage of constants used throughout application for easy access.
 */

package util;


public class Constants {
    private Constants() { }

    // Width/height (in pixels) of application window
    public static final int WINDOW_X = 960;
    public static final int WINDOW_Y = 676;

    // Width/height of world in pixels
    public static final int WORLD_WIDTH = 4800;
    public static final int WORLD_HEIGHT = 4800;

    // Tile square dimensions (in pixels)
    public static final int TILESIZE = 48;

    // Tile columns/rows in world
    public static final int TILE_COLUMNS = WORLD_WIDTH / TILESIZE;
    public static final int TILE_ROWS = WORLD_HEIGHT / TILESIZE;

    // Height (in pixels) of HUD (HUD uses window width)
    public static final int HUD_HEIGHT = TILESIZE * 2;

    // Renders per second = Framerate
    // Logic updates per second = Framerate / Timestep
    public static final int FRAMERATE = 60;
    public static final int TIMESTEP = 10;
}
