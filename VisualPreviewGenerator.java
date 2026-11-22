import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Utilitário de renderização gráfica em alta qualidade das telas
 * do Sistema Acadêmico (Modern Edition).
 */
public class VisualPreviewGenerator {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "true");
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        try {
            System.out.println(">>> Renderizando capturas do Sistema Acadêmico Moderno 3D...");

            int largura = 870;
            int altura = 640;

            String[] nomes = {"print_aba1_cadastro", "print_aba2_lista", "print_aba3_estatisticas"};
            String[] titulosAbas = {"Cadastro de Aluno", "Lista de Alunos", "Estatísticas"};

            for (int i = 0; i < 3; i++) {
                JPanel window = montarJanelaSimulada(i, largura, altura);

                BufferedImage image = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = image.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

                window.setSize(largura, altura);
                layoutRecursivo(window);
                window.paint(g2);
                g2.dispose();

                // Salva PNG
                File fPng = new File(nomes[i] + ".png");
                ImageIO.write(image, "png", fPng);

                // Salva JPG
                BufferedImage rgbImage = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
                Graphics2D gJpg = rgbImage.createGraphics();
                gJpg.drawImage(image, 0, 0, null);
                gJpg.dispose();
                File fJpg = new File(nomes[i] + ".jpg");
                ImageIO.write(rgbImage, "jpg", fJpg);

                System.out.printf("OK [%s]: %s e %s\n", titulosAbas[i], fPng.getName(), fJpg.getName());
            }

            System.out.println(">>> Todas as capturas modernas foram salvas com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void layoutRecursivo(Container c) {
        c.doLayout();
        for (Component child : c.getComponents()) {
            if (child instanceof Container) {
                layoutRecursivo((Container) child);
            }
        }
    }

    private static JPanel montarJanelaSimulada(int abaAtiva, int largura, int altura) {
        JPanel window = new JPanel(new BorderLayout());
        window.setBackground(new Color(241, 245, 249));

        // 1. Barra de Título elegante do Sistema Operacional (estilo macOS/Linux Dark)
        JPanel barraTitulo = new JPanel(new BorderLayout());
        barraTitulo.setBackground(new Color(15, 23, 42)); // Slate 900
        barraTitulo.setPreferredSize(new Dimension(largura, 34));
        barraTitulo.setBorder(new EmptyBorder(0, 14, 0, 10));

        JLabel lblTituloJanela = new JLabel(" Sistema Acadêmico — Faculdade de Computação (UFU)");
        lblTituloJanela.setForeground(Color.WHITE);
        lblTituloJanela.setFont(new Font("SansSerif", Font.BOLD, 12));
        barraTitulo.add(lblTituloJanela, BorderLayout.WEST);

        JPanel botoesControle = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 9));
        botoesControle.setOpaque(false);
        botoesControle.add(criarCirculoControle(new Color(148, 163, 184))); // Min
        botoesControle.add(criarCirculoControle(new Color(148, 163, 184))); // Max
        botoesControle.add(criarCirculoControle(new Color(239, 68, 68)));   // Fechar
        barraTitulo.add(botoesControle, BorderLayout.EAST);

        // 2. View
        SistemaAcademicoView view = new SistemaAcademicoView(null);

        // Barra de Menus (JMenuBar)
        JMenuBar menuBar = view.criarBarraMenus();

        // Topo: Barra de Título + Menus + Header Moderno
        JPanel topoContainer = new JPanel();
        topoContainer.setLayout(new BoxLayout(topoContainer, BoxLayout.Y_AXIS));
        topoContainer.add(barraTitulo);
        topoContainer.add(menuBar);
        topoContainer.add(view.criarHeaderApp());

        window.add(topoContainer, BorderLayout.NORTH);

        // 3. JTabbedPane com as 3 abas e Ícones 3D
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 13));

        Icon iconeCadastro = IconHelper.getIcon("tab_cadastro_3d.png", 22, 22);
        Icon iconeLista = IconHelper.getIcon("tab_lista_3d.png", 22, 22);
        Icon iconeEstatisticas = IconHelper.getIcon("tab_estatisticas_3d.png", 22, 22);

        tabbedPane.addTab("Cadastro de Aluno", iconeCadastro, view.criarPainelCadastro());
        tabbedPane.addTab("Lista de Alunos", iconeLista, view.criarPainelLista());
        tabbedPane.addTab("Estatísticas", iconeEstatisticas, view.criarPainelEstatisticas());

        tabbedPane.setSelectedIndex(abaAtiva);
        window.add(tabbedPane, BorderLayout.CENTER);

        // 4. Barra de status inferior
        window.add(view.criarBarraStatus(), BorderLayout.SOUTH);

        // Borda externa
        window.setBorder(BorderFactory.createLineBorder(new Color(148, 163, 184), 1));

        return window;
    }

    private static JLabel criarCirculoControle(Color cor) {
        JLabel l = new JLabel(" ") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(cor);
                g2.fillOval(1, 1, 11, 11);
                g2.dispose();
            }
        };
        l.setPreferredSize(new Dimension(14, 14));
        return l;
    }
}
