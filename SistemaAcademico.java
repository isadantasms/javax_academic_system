import javax.swing.*;
import java.awt.*;

/**
 * MINISTÉRIO DA EDUCAÇÃO
 * UNIVERSIDADE FEDERAL DE UBERLÂNDIA - UFU
 * FACULDADE DE COMPUTAÇÃO - FACOM
 * SISTEMAS DE INFORMAÇÃO
 * 
 * Trabalho 06: Interfaces Gráficas em Swing
 * Tema: Sistema Acadêmico (Modern UI Edition)
 * 
 * Requisitos atendidos rigorosamente:
 * 1. Janela principal (JFrame) com título apropriado, centralizada, barra de menus
 *    (JMenuBar) com menus "Arquivo" e "Ajuda", utilizando JMenu e JMenuItem.
 * 2. JTabbedPane com 3 abas:
 *    - "Cadastro de Aluno": JLabel, JTextField para nome, JComboBox para curso,
 *      JRadioButton agrupados em ButtonGroup para turno, JCheckBox para bolsista,
 *      JButton para salvar e limpar, organizados em BorderLayout, GridLayout e FlowLayout.
 * 3. "Lista de Alunos": JTable dentro de JScrollPane com colunas apropriadas e botões organizados.
 * 4. "Estatísticas": JSplitPane dividindo a tela com JTextArea de um lado e JButton do outro.
 * 5. Organização visual, alinhamento, uso de pelo menos 3 layouts e Look and Feel do sistema operacional.
 */
public class SistemaAcademico extends JFrame {

    private final SistemaAcademicoView view;

    public SistemaAcademico() {
        super("Sistema Acadêmico - Faculdade de Computação (UFU)");

        // 5. Look and Feel do Sistema Operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        this.view = new SistemaAcademicoView(this);

        // 1. Barra de Menus (JMenuBar)
        setJMenuBar(view.criarBarraMenus());

        // 2. Painel Principal com BorderLayout
        getContentPane().setLayout(new BorderLayout());

        // Header Superior Moderno com Logo 3D
        getContentPane().add(view.criarHeaderApp(), BorderLayout.NORTH);

        // Centro: JTabbedPane contendo as 3 abas
        getContentPane().add(view.criarTabbedPane(), BorderLayout.CENTER);

        // Barra de status inferior
        getContentPane().add(view.criarBarraStatus(), BorderLayout.SOUTH);

        // 1. Configurações da Janela
        setSize(850, 640);
        setMinimumSize(new Dimension(760, 520));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela
    }

    public static void main(String[] args) {
        // Habilita anti-aliasing no sistema Swing
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        SwingUtilities.invokeLater(() -> {
            SistemaAcademico app = new SistemaAcademico();
            app.setVisible(true);
        });
    }
}
