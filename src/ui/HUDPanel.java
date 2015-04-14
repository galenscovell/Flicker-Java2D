
/**
 * HUDPANEL CLASS
 * Displays player statistics and provides game options.
 */

package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class HUDPanel extends JPanel {
    private int x, y;
    private JPanel[] healthTicks;
    private Color full = new Color(0x26A65B);


    public HUDPanel(int panelWidth, int panelHeight) {
        this.x = panelWidth;
        this.y = panelHeight;
        setPreferredSize(new Dimension(x, y));
        setOpaque(true);
        setBackground(Color.BLACK);
        createComponents(this);
    }

    private void createComponents(Container container) {
        Font retroFont = new Font("SDS_8x8", Font.PLAIN, 14);
        Dimension labelSize = new Dimension((x / 3) - 20, y / 3);

        JPanel healthPanel = new JPanel();
        healthPanel.setPreferredSize(new Dimension((x / 3) - 20, y));
        healthPanel.setOpaque(false);

        createHealthBar(healthPanel, labelSize, retroFont);
        container.add(healthPanel);
        
        JPanel itemsPanel = new JPanel();
        itemsPanel.setPreferredSize(new Dimension(x / 3, y));
        itemsPanel.setOpaque(false);
        JLabel itemsLabel = new JLabel("INVENTORY", JLabel.CENTER);
        itemsLabel.setPreferredSize(labelSize);
        itemsLabel.setFont(retroFont);
        itemsLabel.setForeground(Color.WHITE);
        itemsPanel.add(itemsLabel);
        container.add(itemsPanel);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setPreferredSize(new Dimension((x / 3) - 20, y));
        optionsPanel.setOpaque(false);
        JLabel optionsLabel = new JLabel("OPTIONS", JLabel.CENTER);
        optionsLabel.setPreferredSize(labelSize);
        optionsLabel.setFont(retroFont);
        optionsLabel.setForeground(Color.WHITE);
        optionsPanel.add(optionsLabel);
        container.add(optionsPanel);
    }

    public void changeHealth(int i) {
        int currentHealth = currentHealthTick();
        if (i < 0 && currentHealth > 0) {
            healthTicks[currentHealth].setBackground(Color.WHITE);
        } else if (i > 0 && currentHealth < 4) {
            healthTicks[currentHealth].setBackground(full);
        }
        
    }

    private int currentHealthTick() {
        boolean[] healthChecker = new boolean[MainFrame.playerStats.getCon()];
        int currentHealthTick = 0;
        for (boolean state : healthChecker) {
            if (state) {
                currentHealthTick++;
            } else {
                return currentHealthTick;
            }
        }
        return 0;
    }

    private void createHealthBar(Container container, Dimension labelSize, Font retroFont) {
        Dimension healthTickSize = new Dimension(30, 30);
        Border tickBorderRaised = BorderFactory.createRaisedBevelBorder();
        Border tickBorderMatte = BorderFactory.createMatteBorder(2, 5, 2, 2, new Color(0x87D37C));
        Border tickBorderCompound = BorderFactory.createCompoundBorder(tickBorderRaised, tickBorderMatte);

        int con = MainFrame.playerStats.getCon();
        healthTicks = new JPanel[con];
        for (int i = 0; i < con; i++) {
            JPanel tick = new JPanel();
            tick.setPreferredSize(healthTickSize);
            tick.setBackground(full);
            tick.setBorder(tickBorderCompound);
            healthTicks[i] = tick;
            container.add(tick);
        }
        
        JLabel healthLabel = new JLabel("HEALTH", JLabel.CENTER);
        healthLabel.setPreferredSize(labelSize);
        healthLabel.setFont(retroFont);
        healthLabel.setForeground(Color.WHITE);
        container.add(healthLabel);
    }
}