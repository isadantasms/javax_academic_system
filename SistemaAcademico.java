import javax.swing.*;
import java.awt.*;

/**
 * Universidade Federal de Uberlândia - UFU
 * Faculdade de Computação - FACOM
 * Bacharelado em Sistemas de Informação
 * Programação Orientada a Objetos (POO) - Trabalho 06
 *
 * Janela principal do Sistema Acadêmico. Estende JFrame e organiza
 * a estrutura base da interface gráfica: menu superior, cabeçalho institucional,
 * painel com abas navegáveis e barra de status.
 *
 * @author Isabela Dantas de Moraes
 */
public class SistemaAcademico extends JFrame {

    private static final long serialVersionUID = 1L;
    private final transient SistemaAcademicoView view;

    public SistemaAcademico() {
        super("Sistema Acadêmico - Faculdade de Computação (UFU)");

        this.view = new SistemaAcademicoView(this);

        // Barra de menus superior (Arquivo e Ajuda)
        setJMenuBar(view.criarBarraMenus());

        // Organização do contêiner principal com BorderLayout
        getContentPane().setLayout(new BorderLayout());

        // Cabeçalho institucional na região norte
        getContentPane().add(view.criarHeaderApp(), BorderLayout.NORTH);

        // Painel central com as abas do sistema (ocupa a maior área da tela)
        getContentPane().add(view.criarTabbedPane(), BorderLayout.CENTER);

        // Barra de status no rodapé
        getContentPane().add(view.criarBarraStatus(), BorderLayout.SOUTH);

        // Ajustes de dimensão da janela e comportamento ao fechar
        setSize(850, 640);
        setMinimumSize(new Dimension(760, 520));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela no monitor
    }

    public static void main(String[] args) {
        // Tenta aplicar o Look and Feel nativo do sistema operacional antes de criar os componentes
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Em caso de falha na detecção do tema do SO, mantém o visual padrão do Swing
        }

        // Suavização de fontes e bordas para melhorar a legibilidade
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        // Inicializa a aplicação na Event Dispatch Thread (EDT) para garantir a segurança de threads no Swing
        SwingUtilities.invokeLater(() -> {
            SistemaAcademico app = new SistemaAcademico();
            app.setVisible(true);
        });
    }
}
