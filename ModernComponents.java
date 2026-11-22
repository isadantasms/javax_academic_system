import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Biblioteca de componentes visuais modernos para Java Swing.
 * Proporciona uma interface visual de alto impacto (estilo SaaS / Dashboard Desktop 2026).
 */
public class ModernComponents {

    // Paleta de Cores Moderna (Tailwind / Slate Design)
    public static final Color COLOR_BG = new Color(241, 245, 249);           // Slate 100
    public static final Color COLOR_CARD_BG = new Color(255, 255, 255);      // Pure White
    public static final Color COLOR_TEXT_PRIMARY = new Color(15, 23, 42);     // Slate 900
    public static final Color COLOR_TEXT_MUTED = new Color(100, 116, 139);   // Slate 500
    public static final Color COLOR_BORDER = new Color(226, 232, 240);       // Slate 200
    public static final Color COLOR_BORDER_FOCUS = new Color(37, 99, 235);   // Royal Blue 600

    public static final Color COLOR_PRIMARY = new Color(37, 99, 235);        // Royal Blue
    public static final Color COLOR_PRIMARY_HOVER = new Color(29, 78, 216);
    public static final Color COLOR_SUCCESS = new Color(16, 185, 129);       // Emerald
    public static final Color COLOR_SUCCESS_HOVER = new Color(5, 150, 105);
    public static final Color COLOR_DANGER = new Color(239, 68, 68);          // Rose Red
    public static final Color COLOR_DANGER_HOVER = new Color(220, 38, 38);
    public static final Color COLOR_WARNING = new Color(245, 158, 11);       // Amber
    public static final Color COLOR_WARNING_HOVER = new Color(217, 119, 6);
    public static final Color COLOR_SECONDARY = new Color(71, 85, 105);      // Slate 600
    public static final Color COLOR_SECONDARY_HOVER = new Color(51, 65, 85);

    /**
     * Card moderno com cantos arredondados e sombra projetada suave (Drop Shadow)
     */
    public static class ModernCard extends JPanel {
        private final int cornerRadius;

        public ModernCard(int radius) {
            super();
            this.cornerRadius = radius;
            setOpaque(false);
        }

        public ModernCard() {
            this(16);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Sombra suave (Drop Shadow em camadas translúcidas)
            for (int i = 0; i < 4; i++) {
                g2.setColor(new Color(15, 23, 42, 6 - i));
                g2.fill(new RoundRectangle2D.Float(2 - i, 2 + i, w - 4 + (i * 2), h - 4 + i, cornerRadius + 2, cornerRadius + 2));
            }

            // Fundo do Card
            g2.setColor(COLOR_CARD_BG);
            g2.fill(new RoundRectangle2D.Float(3, 3, w - 7, h - 7, cornerRadius, cornerRadius));

            // Borda suave
            g2.setColor(COLOR_BORDER);
            g2.setStroke(new BasicStroke(1.2f));
            g2.draw(new RoundRectangle2D.Float(3, 3, w - 7, h - 7, cornerRadius, cornerRadius));

            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * Botão moderno com cantos arredondados, gradiente sutil, profundidade 3D e hover
     */
    public static class ModernButton extends JButton {
        private final Color baseColor;
        private final Color hoverColor;
        private boolean isHovered = false;
        private boolean isPressed = false;
        private final int radius;

        public ModernButton(String text, Icon icon, Color baseColor, Color hoverColor, int radius) {
            super(text, icon);
            this.baseColor = baseColor;
            this.hoverColor = hoverColor;
            this.radius = radius;

            setFont(new Font("SansSerif", Font.BOLD, 13));
            setForeground(Color.WHITE);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setIconTextGap(8);
            setBorder(new EmptyBorder(8, 16, 8, 16));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    isPressed = false;
                    repaint();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    isPressed = true;
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    isPressed = false;
                    repaint();
                }
            });
        }

        public ModernButton(String text, Icon icon, Color baseColor, Color hoverColor) {
            this(text, icon, baseColor, hoverColor, 10);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            Color curColor = isPressed ? hoverColor.darker() : (isHovered ? hoverColor : baseColor);

            // Sombra inferior do botão (relevo 3D tátil)
            if (!isPressed) {
                g2.setColor(new Color(15, 23, 42, 45));
                g2.fill(new RoundRectangle2D.Float(1, 3, w - 2, h - 2, radius, radius));
            }

            // Gradiente volumétrico
            int offset = isPressed ? 2 : 0;
            GradientPaint gp = new GradientPaint(
                    0, offset, curColor.brighter(),
                    0, h - 2 + offset, curColor
            );
            g2.setPaint(gp);
            g2.fill(new RoundRectangle2D.Float(1, 1 + offset, w - 2, h - 3, radius, radius));

            // Brilho no topo (3D Specular Highlight)
            if (!isPressed) {
                g2.setColor(new Color(255, 255, 255, 60));
                g2.drawLine(radius, 2, w - radius, 2);
            }

            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * Campo de texto com cantos arredondados, padding interno e foco interativo com glow
     */
    public static class ModernTextField extends JTextField {
        private boolean hasFocus = false;
        private final int radius;

        public ModernTextField(int radius) {
            super();
            this.radius = radius;
            setOpaque(false);
            setFont(new Font("SansSerif", Font.PLAIN, 13));
            setBorder(new EmptyBorder(8, 12, 8, 12));

            addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    hasFocus = true;
                    repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    hasFocus = false;
                    repaint();
                }
            });
        }

        public ModernTextField() {
            this(10);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Fundo
            g2.setColor(Color.WHITE);
            g2.fill(new RoundRectangle2D.Float(1, 1, w - 2, h - 2, radius, radius));

            // Borda com destaque ao focar
            if (hasFocus) {
                // Glow suave
                g2.setColor(new Color(59, 130, 246, 60));
                g2.setStroke(new BasicStroke(2.5f));
                g2.draw(new RoundRectangle2D.Float(1, 1, w - 3, h - 3, radius, radius));
                g2.setColor(COLOR_BORDER_FOCUS);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(1, 1, w - 3, h - 3, radius, radius));
            } else {
                g2.setColor(COLOR_BORDER);
                g2.setStroke(new BasicStroke(1.2f));
                g2.draw(new RoundRectangle2D.Float(1, 1, w - 3, h - 3, radius, radius));
            }

            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * Renderizador de células da JTable para exibir badges modernos (Pill Style)
     */
    public static class BadgeTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            String valStr = (value != null) ? value.toString() : "";

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 4));
            panel.setOpaque(true);
            panel.setBackground(isSelected ? new Color(224, 231, 255) : (row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252)));

            boolean isBolsista = "Sim".equalsIgnoreCase(valStr) || valStr.contains("Sim");

            JLabel badge = new JLabel(isBolsista ? "✓ Bolsista" : "Regular") {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    Color bgPill = isBolsista ? new Color(220, 252, 231) : new Color(241, 245, 249);
                    Color borderPill = isBolsista ? new Color(134, 239, 172) : new Color(203, 213, 225);

                    g2.setColor(bgPill);
                    g2.fill(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, 14, 14));
                    g2.setColor(borderPill);
                    g2.setStroke(new BasicStroke(1.0f));
                    g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, 14, 14));

                    g2.dispose();
                    super.paintComponent(g);
                }
            };

            badge.setFont(new Font("SansSerif", Font.BOLD, 11));
            badge.setForeground(isBolsista ? new Color(22, 101, 52) : new Color(100, 116, 139));
            badge.setBorder(new EmptyBorder(3, 10, 3, 10));

            panel.add(badge);
            return panel;
        }
    }
}
