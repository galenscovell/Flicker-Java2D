
/**
 * PLAYERSTATS CLASS
 * Holds all Player statistics such as attributes, deaths, kills, etc.
 */

package ui;


public class PlayerStats {
    private int agi, con, str, def;

    public PlayerStats() {
        this.agi = 3;
        this.con = 3;
        this.str = 3;
        this.def = 3;
    }

    public int getAgi() {
        return agi;
    }

    public void setAgi(int value) {
        agi = value;
    }

    public int getCon() {
        return con;
    }

    public void setCon(int value) {
        con = value;
    }
}