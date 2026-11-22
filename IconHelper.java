import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Utilitário para carregar e redimensionar ícones da interface.
 * Caso a imagem não seja encontrada na pasta de recursos, desenha
 * programaticamente um ícone vetorial básico para não deixar a interface vazia.
 */
public class IconHelper {

    public static ImageIcon getIcon(String filename, int w, int h) {
        String path = "assets/icons/" + filename;
        File f = new File(path);
        if (f.exists()) {
            ImageIcon icon = new ImageIcon(path);
            Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        // Se o arquivo PNG não for localizado, gera um ícone simplificado em tempo de execução
        return new ImageIcon(criarIconeFallback(filename, w, h));
    }

    private static Image criarIconeFallback(String name, int w, int h) {
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color c1 = new Color(37, 99, 235);
        Color c2 = new Color(29, 78, 216);
        if (name.contains("salvar") || name.contains("exportar")) {
            c1 = new Color(16, 185, 129);
            c2 = new Color(5, 150, 105);
        } else if (name.contains("remover") || name.contains("sair")) {
            c1 = new Color(239, 68, 68);
            c2 = new Color(185, 28, 28);
        } else if (name.contains("limpar")) {
            c1 = new Color(245, 158, 11);
            c2 = new Color(180, 83, 9);
        }

        g2.setPaint(new GradientPaint(0, 0, c1, w, h, c2));
        g2.fillRoundRect(2, 2, w - 4, h - 4, 6, 6);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(2, 2, w - 4, h - 4, 6, 6);
        g2.dispose();
        return img;
    }
}
