
/**
 * HUDPANEL CLASS
 * Displays player statistics and provides game options.
 */

package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.border.MatteBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class HUDPanel extends JPanel {
    private int x, y;


    public HUDPanel(int panelWidth, int panelHeight) {
        this.x = panelWidth;
        this.y = panelHeight;
        setPreferredSize(new Dimension(x, y));
        setOpaque(true);
        setBackground(new Color(0x757161));

        
        Border firstBorder = BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(0x30346D));
        Border secondBorder = BorderFactory.createMatteBorder(4, 4, 4, 4, new Color(0x4D494D));
        Border compound1 = BorderFactory.createCompoundBorder(firstBorder, secondBorder);

        Border outline = BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0x111111));
        Border compoundOutline = BorderFactory.createCompoundBorder(compound1, outline);

        Border shadow = BorderFactory.createMatteBorder(4, 1, 1, 1, new Color(0x333333));
        Border completeBorder = BorderFactory.createCompoundBorder(compoundOutline, shadow);
        setBorder(completeBorder);

        createComponents(this);
    }

    private void createComponents(Container container) {

    }
}