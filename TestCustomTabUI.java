
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class TestCustomTabUI {
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "true");
        JTabbedPane tab = new JTabbedPane();
        tab.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(isSelected ? new Color(37, 99, 235) : new Color(21, 29, 42));
                g2.fillRect(x, y, w, h);
            }
        });
        tab.addTab("Teste", new JPanel());
        System.out.println("CustomTabbedPaneUI works!");
    }
}
