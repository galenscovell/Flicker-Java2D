
/**
 * HUDPANEL CLASS
 * Displays player statistics and provides game options.
 */

package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class HUDPanel extends JPanel {

    public HUDPanel(int panelWidth, int panelHeight) {
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(new Color(0x31312F));
        setOpaque(true);
    }

    private void createComponents(Container container) {

    }
}