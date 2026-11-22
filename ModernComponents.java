import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.Map;

/**
 * Biblioteca de componentes visuais modernos em Dark Mode (Midnight Pro Edition)
 * para Java Swing (javax.swing).
 * Garante controle absoluto de renderização, alto contraste e visual contemporâneo.
 */
public class ModernComponents {

    // Paleta de Cores Midnight Pro
    public static final Color COLOR_BG = new Color(11, 15, 23);             // Deep Obsidian Slate #0B0F17
    public static final Color COLOR_CARD_BG = new Color(21, 29, 42);        // Dark Card Slate #151D2A
    public static final Color COLOR_INPUT_BG = new Color(13, 18, 28);       // Input Background #0D121C

    public static final Color COLOR_TEXT_TITLE = new Color(255, 255, 255);   // White #FFFFFF
    public static final Color COLOR_TEXT_PRIMARY = new Color(241, 245, 249); // White Slate #F1F5F9
    public static final Color COLOR_TEXT_LABEL = new Color(148, 163, 184);   // Slate 400 #94A3B8
    public static final Color COLOR_TEXT_MUTED = new Color(100, 116, 139);   // Slate 500 #64748B

    public static final Color COLOR_BORDER = new Color(42, 55, 74);          // Card Border #2A374A
    public static final Color COLOR_BORDER_FOCUS = new Color(59, 130, 246);   // Blue Glow #3B82F6

    public static final Color COLOR_PRIMARY = new Color(37, 99, 235);        // Royal Blue #2563EB
    public static final Color COLOR_PRIMARY_HOVER = new Color(29, 78, 216);
    public static final Color COLOR_SUCCESS = new Color(16, 185, 129);       // Emerald #10B981
    public static final Color COLOR_SUCCESS_HOVER = new Color(5, 150, 105);
    public static final Color COLOR_DANGER = new Color(239, 68, 68);          // Rose Red #EF4444
    public static final Color COLOR_DANGER_HOVER = new Color(220, 38, 38);
    public static final Color COLOR_WARNING = new Color(245, 158, 11);       // Amber #F59E0B
    public static final Color COLOR_WARNING_HOVER = new Color(217, 119, 6);
    public static final Color COLOR_SECONDARY = new Color(51, 65, 85);       // Slate 700
    public static final Color COLOR_SECONDARY_HOVER = new Color(71, 85, 105);

    /**
     * UI Customizada para o JTabbedPane:
     * Elimina completamente o cinza claro do Look and Feel e pinta um fundo escuro elegante
     * com abas selecionadas em alto relevo e indicador luminoso ciano.
     */
    public static class ModernTabbedPaneUI extends BasicTabbedPaneUI {

        @Override
        protected void installDefaults() {
            super.installDefaults();
            tabInsets = new Insets(10, 20, 10, 20);
            selectedTabPadInsets = new Insets(0, 0, 0, 0);
        }

        @Override
        protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
            Graphics2D g2 = (Graphics2D) g.create();
            // Fundo escuro total para a barra superior de abas
            g2.setColor(COLOR_BG);
            g2.fillRect(0, 0, tabPane.getWidth(), tabPane.getHeight());
            g2.dispose();
            super.paintTabArea(g, tabPlacement, selectedIndex);
        }

        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                                          int x, int y, int w, int h, boolean isSelected) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (isSelected) {
                // Aba Ativa: Fundo Card Escuro com topo arredondado
                g2.setColor(COLOR_CARD_BG);
                g2.fillRoundRect(x + 2, y + 2, w - 4, h, 12, 12);

                // Indicador luminoso na base da aba (Ciano Neon)
                g2.setColor(new Color(56, 189, 248));
                g2.fillRoundRect(x + 8, y + h - 3, w - 16, 3, 2, 2);
            } else {
                // Aba Inativa: Fundo escuro sutil
                g2.setColor(new Color(15, 23, 34));
                g2.fillRoundRect(x + 2, y + 4, w - 4, h - 3, 10, 10);
            }

            g2.dispose();
        }

        @Override
        protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
                                      int x, int y, int w, int h, boolean isSelected) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (isSelected) {
                g2.setColor(new Color(59, 130, 246, 180));
                g2.drawRoundRect(x + 2, y + 2, w - 4, h, 12, 12);
            } else {
                g2.setColor(COLOR_BORDER);
                g2.drawRoundRect(x + 2, y + 4, w - 4, h - 3, 10, 10);
            }
            g2.dispose();
        }

        @Override
        protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics,
                                 int tabIndex, String title, Rectangle textRect, boolean isSelected) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            if (isSelected) {
                g2.setFont(font.deriveFont(Font.BOLD, 13f));
                g2.setColor(Color.WHITE); // Branco nítido na aba ativa
            } else {
                g2.setFont(font.deriveFont(Font.PLAIN, 13f));
                g2.setColor(COLOR_TEXT_LABEL); // Slate 400 de alta legibilidade na aba inativa
            }

            int y = textRect.y + metrics.getAscent();
            g2.drawString(title, textRect.x, y);
            g2.dispose();
        }

        @Override
        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(COLOR_BORDER);
            g2.drawLine(0, 0, tabPane.getWidth(), 0);
            g2.dispose();
        }

        @Override
        protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects,
                                           int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
            // Elimina o pontilhado dos anos 90
        }
    }

    /**
     * Card moderno escuro com cantos arredondados, borda luminosa sutil e profundidade
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

            for (int i = 0; i < 3; i++) {
                g2.setColor(new Color(0, 0, 0, 45 - (i * 12)));
                g2.fill(new RoundRectangle2D.Float(2 - i, 2 + i, w - 4 + (i * 2), h - 4 + i, cornerRadius + 2, cornerRadius + 2));
            }

            g2.setColor(COLOR_CARD_BG);
            g2.fill(new RoundRectangle2D.Float(3, 3, w - 7, h - 7, cornerRadius, cornerRadius));

            g2.setColor(COLOR_BORDER);
            g2.setStroke(new BasicStroke(1.2f));
            g2.draw(new RoundRectangle2D.Float(3, 3, w - 7, h - 7, cornerRadius, cornerRadius));

            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * Botão moderno com gradiente volumétrico, relevo 3D, hover e texto 100% nítido
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

            if (!isPressed) {
                g2.setColor(new Color(0, 0, 0, 80));
                g2.fill(new RoundRectangle2D.Float(1, 3, w - 2, h - 2, radius, radius));
            }

            int offset = isPressed ? 2 : 0;
            GradientPaint gp = new GradientPaint(
                    0, offset, curColor.brighter(),
                    0, h - 2 + offset, curColor
            );
            g2.setPaint(gp);
            g2.fill(new RoundRectangle2D.Float(1, 1 + offset, w - 2, h - 3, radius, radius));

            if (!isPressed) {
                g2.setColor(new Color(255, 255, 255, 70));
                g2.drawLine(radius, 2, w - radius, 2);
            }

            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * Campo de texto escuro com cantos arredondados, texto branco legível e glow ao focar
     */
    public static class ModernTextField extends JTextField {
        private boolean hasFocus = false;
        private final int radius;
        private String placeholder = "";

        public ModernTextField(int radius) {
            super();
            this.radius = radius;
            setOpaque(false);
            setFont(new Font("SansSerif", Font.PLAIN, 13));
            setForeground(COLOR_TEXT_PRIMARY);
            setCaretColor(new Color(56, 189, 248));
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

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            g2.setColor(COLOR_INPUT_BG);
            g2.fill(new RoundRectangle2D.Float(1, 1, w - 2, h - 2, radius, radius));

            if (hasFocus) {
                g2.setColor(new Color(59, 130, 246, 80));
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

            // Placeholder quando vazio
            if (getText().isEmpty() && !hasFocus && !placeholder.isEmpty()) {
                Graphics2D gText = (Graphics2D) g.create();
                gText.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                gText.setColor(COLOR_TEXT_MUTED);
                gText.setFont(getFont());
                FontMetrics fm = gText.getFontMetrics();
                gText.drawString(placeholder, getInsets().left, (h + fm.getAscent() - fm.getDescent()) / 2);
                gText.dispose();
            }
        }
    }

    /**
     * Gráfico Procedural em Java 2D (Graphics2D) para exibição visual de dados analíticos
     */
    public static class ModernBarChart extends JPanel {
        private Map<String, Integer> dados;

        public ModernBarChart(Map<String, Integer> dados) {
            this.dados = dados;
            setOpaque(false);
            setPreferredSize(new Dimension(280, 160));
        }

        public void setDados(Map<String, Integer> dados) {
            this.dados = dados;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (dados == null || dados.isEmpty()) return;

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int paddingLeft = 30;
            int paddingBottom = 30;
            int chartW = w - paddingLeft - 20;
            int chartH = h - paddingBottom - 20;

            int maxVal = 1;
            for (int v : dados.values()) {
                if (v > maxVal) maxVal = v;
            }

            int n = dados.size();
            int barW = Math.max(18, chartW / (n * 2));
            int step = chartW / n;

            Color[] cores = {
                    new Color(56, 189, 248),  // Ciano
                    new Color(168, 85, 247),  // Roxo
                    new Color(52, 211, 153),  // Verde
                    new Color(251, 191, 36)   // Dourado
            };

            int idx = 0;
            for (Map.Entry<String, Integer> entry : dados.entrySet()) {
                int val = entry.getValue();
                int barH = (int) ((val / (double) maxVal) * chartH);
                int x = paddingLeft + (idx * step) + (step - barW) / 2;
                int y = 20 + (chartH - barH);

                Color corBase = cores[idx % cores.length];
                GradientPaint gp = new GradientPaint(x, y, corBase, x, y + barH, corBase.darker());
                g2.setPaint(gp);
                g2.fillRoundRect(x, y, barW, barH, 8, 8);

                // Valor sobre a barra
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 11));
                String vStr = String.valueOf(val);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(vStr, x + (barW - fm.stringWidth(vStr)) / 2, y - 4);

                // Legenda abaixo
                g2.setColor(COLOR_TEXT_LABEL);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
                String label = entry.getKey();
                if (label.length() > 6) label = label.substring(0, 5) + ".";
                FontMetrics fmLbl = g2.getFontMetrics();
                g2.drawString(label, x + (barW - fmLbl.stringWidth(label)) / 2, h - 8);

                idx++;
            }

            g2.dispose();
        }
    }

    /**
     * Barra de progresso com design moderno
     */
    public static class ModernProgressBar extends JProgressBar {
        public ModernProgressBar(int min, int max) {
            super(min, max);
            setOpaque(false);
            setBorderPainted(false);
            setStringPainted(true);
            setFont(new Font("SansSerif", Font.BOLD, 10));
            setForeground(Color.WHITE);
            setPreferredSize(new Dimension(140, 18));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Fundo
            g2.setColor(COLOR_INPUT_BG);
            g2.fillRoundRect(0, 0, w, h, h, h);

            // Preenchimento
            double ratio = (getValue() - getMinimum()) / (double) (getMaximum() - getMinimum());
            int fillW = (int) (w * Math.min(1.0, Math.max(0.0, ratio)));
            if (fillW > 0) {
                GradientPaint gp = new GradientPaint(0, 0, new Color(37, 99, 235), fillW, 0, new Color(56, 189, 248));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, fillW, h, h, h);
            }

            // Texto
            if (isStringPainted()) {
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                String str = getString();
                int tx = (w - fm.stringWidth(str)) / 2;
                int ty = (h + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(str, tx, ty);
            }

            g2.dispose();
        }
    }

    /**
     * Renderizador de células da JTable em Dark Mode com Badges coloridos
     */
    public static class BadgeTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            String valStr = (value != null) ? value.toString() : "";

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 4));
            panel.setOpaque(true);
            panel.setBackground(isSelected ? new Color(30, 58, 138) : (row % 2 == 0 ? COLOR_CARD_BG : new Color(17, 24, 36)));

            boolean isBolsista = "Sim".equalsIgnoreCase(valStr) || valStr.contains("Sim");

            JLabel badge = new JLabel(isBolsista ? "✓ Bolsista" : "Regular") {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    Color bgPill = isBolsista ? new Color(6, 78, 59) : new Color(31, 41, 55);
                    Color borderPill = isBolsista ? new Color(16, 185, 129) : new Color(75, 85, 99);

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
            badge.setForeground(isBolsista ? new Color(52, 211, 153) : new Color(156, 163, 175));
            badge.setBorder(new EmptyBorder(3, 10, 3, 10));

            panel.add(badge);
            return panel;
        }
    }
}
