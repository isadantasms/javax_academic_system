
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class ModernTabbedPaneUI extends BasicTabbedPaneUI {
    @Override
    protected void installDefaults() {
        super.installDefaults();
        tabInsets = new Insets(10, 20, 10, 20);
        selectedTabPadInsets = new Insets(0, 0, 0, 0);
    }
    @Override
    protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(11, 15, 23)); // Fundo escuro total da barra de abas
        g2.fillRect(0, 0, tabPane.getWidth(), tabPane.getHeight());
        g2.dispose();
        super.paintTabArea(g, tabPlacement, selectedIndex);
    }
    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (isSelected) {
            g2.setColor(new Color(21, 29, 42)); // Card escuro selecionado
            g2.fillRoundRect(x + 2, y + 2, w - 4, h - 2, 10, 10);
            // Indicador azul luminoso na base
            g2.setColor(new Color(56, 189, 248)); // Ciano brilhante
            g2.fillRoundRect(x + 6, y + h - 3, w - 12, 3, 2, 2);
        } else {
            g2.setColor(new Color(15, 23, 34)); // Inativo escuro discreto
            g2.fillRoundRect(x + 2, y + 4, w - 4, h - 5, 8, 8);
        }
        g2.dispose();
    }
    @Override
    protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(isSelected ? font.deriveFont(Font.BOLD, 13f) : font.deriveFont(Font.PLAIN, 13f));
        g2.setColor(isSelected ? Color.WHITE : new Color(148, 163, 184)); // Branco nítido ou Slate 400
        int y = textRect.y + metrics.getAscent();
        g2.drawString(title, textRect.x, y);
        g2.dispose();
    }
    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(isSelected ? new Color(59, 130, 246, 160) : new Color(42, 55, 74));
        g2.drawRoundRect(x + 2, y + (isSelected ? 2 : 4), w - 4, h - (isSelected ? 2 : 5), 8, 8);
        g2.dispose();
    }
    @Override
    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(42, 55, 74));
        g2.drawLine(0, 0, tabPane.getWidth(), 0);
        g2.dispose();
    }
    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        // Sobrescrito vazio para não desenhar a borda pontilhada padrão de foco
    }
    public static void main(String[] args) {
        System.out.println("ModernTabbedPaneUI compile check: SUCCESS!");
    }
}
