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
 * Componentes visuais personalizados para a interface do sistema acadêmico.
 * Estendem os componentes padrão do Swing (JButton, JTextField, JPanel, etc.)
 * e sobrescrevem a rotina de pintura (paintComponent) com Java 2D para compor o tema escuro.
 */
public class ModernComponents {

    // Paleta de cores base da aplicação
    public static final Color COLOR_BG = new Color(11, 15, 23);             // Fundo principal da aplicação
    public static final Color COLOR_CARD_BG = new Color(21, 29, 42);        // Fundo dos painéis internos e tabelas
    public static final Color COLOR_INPUT_BG = new Color(13, 18, 28);       // Fundo dos campos de entrada de texto

    public static final Color COLOR_TEXT_TITLE = new Color(255, 255, 255);   // Títulos e cabeçalhos em branco
    public static final Color COLOR_TEXT_PRIMARY = new Color(241, 245, 249); // Texto principal de leitura
    public static final Color COLOR_TEXT_LABEL = new Color(148, 163, 184);   // Rótulos de campos (cinza claro)
    public static final Color COLOR_TEXT_MUTED = new Color(100, 116, 139);   // Textos secundários e dicas

    public static final Color COLOR_BORDER = new Color(42, 55, 74);          // Borda padrão dos componentes
    public static final Color COLOR_BORDER_FOCUS = new Color(59, 130, 246);   // Destaque de borda ao receber foco

    public static final Color COLOR_PRIMARY = new Color(37, 99, 235);        // Azul principal (botão de salvar/ação)
    public static final Color COLOR_PRIMARY_HOVER = new Color(29, 78, 216);
    public static final Color COLOR_SUCCESS = new Color(16, 185, 129);       // Verde para operações de confirmação
    public static final Color COLOR_SUCCESS_HOVER = new Color(5, 150, 105);
    public static final Color COLOR_DANGER = new Color(239, 68, 68);          // Vermelho para exclusões
    public static final Color COLOR_DANGER_HOVER = new Color(220, 38, 38);
    public static final Color COLOR_WARNING = new Color(245, 158, 11);       // Amarelo para limpeza de formulário
    public static final Color COLOR_WARNING_HOVER = new Color(217, 119, 6);
    public static final Color COLOR_SECONDARY = new Color(51, 65, 85);       // Cinza ardósia para atualização
    public static final Color COLOR_SECONDARY_HOVER = new Color(71, 85, 105);

    /**
     * Customização de pintura para o painel de abas (JTabbedPane).
     * Sobrescreve métodos da BasicTabbedPaneUI para desenhar abas arredondadas e indicador luminoso.
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
            // Pinta a faixa superior de abas com o fundo do sistema
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
                // Aba selecionada: cor de destaque com cantos arredondados
                g2.setColor(COLOR_CARD_BG);
                g2.fillRoundRect(x + 2, y + 2, w - 4, h, 12, 12);

                // Linha colorida na base indicando a aba ativa
                g2.setColor(new Color(56, 189, 248));
                g2.fillRoundRect(x + 8, y + h - 3, w - 16, 3, 2, 2);
            } else {
                // Aba inativa: tonalidade mais escura para contraste
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
                g2.setColor(Color.WHITE); // Texto em negrito e branco quando ativa
            } else {
                g2.setFont(font.deriveFont(Font.PLAIN, 13f));
                g2.setColor(COLOR_TEXT_LABEL); // Cinza legível quando inativa
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
            // Sobrescrito vazio para não desenhar a borda pontilhada nativa de foco
        }
    }

    /**
     * Painel arredondado que funciona como contêiner visual (card),
     * agrupando os componentes com borda e preenchimento adequados.
     */
    public static class ModernCard extends JPanel {
        private final int radius;

        public ModernCard(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        public ModernCard() {
            this(14);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Preenchimento do card
            g2.setColor(COLOR_CARD_BG);
            g2.fill(new RoundRectangle2D.Float(1, 1, w - 2, h - 2, radius, radius));

            // Borda do card
            g2.setColor(COLOR_BORDER);
            g2.setStroke(new BasicStroke(1.2f));
            g2.draw(new RoundRectangle2D.Float(1, 1, w - 3, h - 3, radius, radius));

            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * Botão customizado que estende JButton, com transições visuais ao passar o mouse (hover)
     * e ao pressionar, mantendo cantos arredondados.
     */
    public static class ModernButton extends JButton {
        private final Color normalColor;
        private final Color hoverColor;
        private boolean isHover = false;
        private boolean isPressed = false;
        private final int radius;

        public ModernButton(String text, Icon icon, Color normalColor, Color hoverColor, int radius) {
            super(text, icon);
            this.normalColor = normalColor;
            this.hoverColor = hoverColor;
            this.radius = radius;

            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setForeground(Color.WHITE);
            setFont(new Font("SansSerif", Font.BOLD, 13));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setIconTextGap(8);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHover = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHover = false;
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

        public ModernButton(String text, Icon icon, Color normalColor, Color hoverColor) {
            this(text, icon, normalColor, hoverColor, 10);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color base = normalColor;
            if (isPressed) {
                base = hoverColor.darker();
            } else if (isHover) {
                base = hoverColor;
            }

            int w = getWidth();
            int h = getHeight();

            // Desenho do botão com cantos arredondados
            g2.setColor(base);
            g2.fill(new RoundRectangle2D.Float(1, 1, w - 2, h - 2, radius, radius));

            // Linha sutil de reflexo na borda superior
            g2.setColor(new Color(255, 255, 255, 30));
            g2.draw(new RoundRectangle2D.Float(1.5f, 1.5f, w - 3, h - 3, radius, radius));

            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * Campo de texto que herda de JTextField, com suporte a texto informativo (placeholder)
     * e realce da cor da borda quando recebe o foco.
     */
    public static class ModernTextField extends JTextField {
        private String placeholder = "";
        private boolean hasFocus = false;
        private final int radius;

        public ModernTextField(int radius) {
            this.radius = radius;
            setOpaque(false);
            setBackground(COLOR_INPUT_BG);
            setForeground(COLOR_TEXT_PRIMARY);
            setCaretColor(Color.WHITE);
            setFont(new Font("SansSerif", Font.PLAIN, 13));
            setBorder(new EmptyBorder(8, 14, 8, 14));

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

            // Fundo do campo de texto
            g2.setColor(COLOR_INPUT_BG);
            g2.fill(new RoundRectangle2D.Float(1, 1, w - 2, h - 2, radius, radius));

            // Borda com destaque azul quando o campo está ativo
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

            // Exibe o texto de orientação (placeholder) se o campo estiver em branco e sem foco
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
     * Gráfico de barras desenhado com Java 2D (Graphics2D).
     * Renderiza dinamicamente as colunas com gradiente de cor e valores numéricos sobre cada barra.
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

            // Identifica o maior valor para definir a proporção de altura das barras
            int maxVal = 1;
            for (int v : dados.values()) {
                if (v > maxVal) maxVal = v;
            }

            int n = dados.size();
            int barW = Math.max(18, chartW / (n * 2));
            int step = chartW / n;

            Color[] cores = {
                    new Color(56, 189, 248),  // Azul claro
                    new Color(168, 85, 247),  // Roxo
                    new Color(52, 211, 153),  // Verde esmeralda
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

                // Desenha o número correspondente acima de cada barra
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 11));
                String vStr = String.valueOf(val);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(vStr, x + (barW - fm.stringWidth(vStr)) / 2, y - 4);

                // Legenda de identificação abaixo da barra
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
     * Barra de progresso customizada com cantos circulares e gradiente de cor.
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

            // Fundo da barra
            g2.setColor(COLOR_INPUT_BG);
            g2.fillRoundRect(0, 0, w, h, h, h);

            // Preenchimento gradiente proporcional ao valor atual
            double ratio = (getValue() - getMinimum()) / (double) (getMaximum() - getMinimum());
            int fillW = (int) (w * Math.min(1.0, Math.max(0.0, ratio)));
            if (fillW > 0) {
                GradientPaint gp = new GradientPaint(0, 0, new Color(37, 99, 235), fillW, 0, new Color(56, 189, 248));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, fillW, h, h, h);
            }

            // Texto com a indicação da quantidade
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
     * Renderizador personalizado para a coluna "Bolsista" na JTable.
     * Em vez de texto puro, desenha uma etiqueta (badge) com cor verde para bolsistas
     * e cinza para alunos regulares.
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
