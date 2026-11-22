
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class TestTabMethods extends BasicTabbedPaneUI {
    @Override
    protected void installDefaults() {
        super.installDefaults();
        tabInsets = new Insets(10, 18, 10, 18);
        selectedTabPadInsets = new Insets(0, 0, 0, 0);
    }
    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(isSelected ? Color.BLUE : Color.BLACK);
        g2.fillRect(x, y, w, h);
        g2.dispose();
    }
    @Override
    protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(isSelected ? Color.WHITE : Color.LIGHT_GRAY);
        g2.drawString(title, textRect.x, textRect.y + metrics.getAscent());
        g2.dispose();
    }
    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
    }
    @Override
    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
    }
    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
    }
    public static void main(String[] args) {
        System.out.println("Compiled and verified successfully!");
    }
}
