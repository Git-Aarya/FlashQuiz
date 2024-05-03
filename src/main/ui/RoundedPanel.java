package ui;

import javax.swing.*;
import java.awt.*;

class RoundedPanel extends JPanel {
    private final int radius = 30;
    private final Color defaultBackground = new Color(50, 60, 70);
    private final Color hoverBackground = defaultBackground.brighter();

    public RoundedPanel() {
        super();
        setOpaque(false);
        setBackground(defaultBackground);
        setupHoverEffect();
    }

    private void setupHoverEffect() {
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(hoverBackground);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(defaultBackground);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(radius, radius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draws the rounded panel.
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 190);
    }
}
