
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
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class HUDPanel extends JPanel {
    private int x, y;
    private JPanel[] healthTicks;
    private boolean[] healthChecker;


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
        if (i < 0) {
            healthTicks[currentHealth].setBackground(new Color(0x34495e));
            healthChecker[currentHealth] = false;
        } else if (i > 0) {
            healthTicks[currentHealth].setBackground(new Color(0x26A65B));
            healthChecker[currentHealth] = true;
        }
        
    }

    private int currentHealthTick() {
        int currentHealthTick = 0;
        for (boolean state : healthChecker) {
            if (state) {
                currentHealthTick++;
            } else {
                return currentHealthTick - 1;
            }
        }
        return healthChecker.length - 1;
    }

    private void createHealthBar(Container container, Dimension labelSize, Font retroFont) {
        Dimension healthTickSize = new Dimension(20, 15);
        
        healthTicks = new JPanel[8];
        healthChecker = new boolean[8];
        for (int i = 0; i < 8; i++) {
            JPanel tick = new JPanel();
            tick.setPreferredSize(healthTickSize);
            tick.setBackground(new Color(0x26A65B));
            tick.setBorder(BorderFactory.createRaisedBevelBorder());
            healthTicks[i] = tick;
            healthChecker[i] = true;
            container.add(tick);
        }
        
        JLabel healthLabel = new JLabel("HEALTH", JLabel.CENTER);
        healthLabel.setPreferredSize(labelSize);
        healthLabel.setFont(retroFont);
        healthLabel.setForeground(Color.WHITE);
        container.add(healthLabel);
    }
}