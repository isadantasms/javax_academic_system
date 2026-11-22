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
 * CONFERÊNCIA DOS REQUISITOS DO EDITAL:
 * 1. Janela principal (JFrame) com título apropriado, centralizada na tela, 
 *    barra de menus (JMenuBar) com menus "Arquivo" e "Ajuda", utilizando JMenu e JMenuItem.
 * 2. JTabbedPane contendo três abas distintas: "Cadastro de Aluno", "Lista de Alunos" 
 *    e "Estatísticas". Na aba de Cadastro: JLabel, JTextField, JComboBox, JRadioButton 
 *    em ButtonGroup, JCheckBox e JButton (Salvar e Limpar).
 * 3. Aba "Lista de Alunos": JTable dentro de JScrollPane com colunas apropriadas e botões.
 * 4. Aba "Estatísticas": JSplitPane dividindo a tela com JTextArea de um lado e JButton do outro.
 * 5. Organização visual, alinhamento, uso explícito de pelo menos 3 tipos distintos de Layout 
 *    (BorderLayout, GridLayout e FlowLayout) e Look and Feel do sistema operacional aplicado 
 *    ANTES da instanciação da janela.
 */
public class SistemaAcademico extends JFrame {

    private final SistemaAcademicoView view;

    public SistemaAcademico() {
        super("Sistema Acadêmico - Faculdade de Computação (UFU)");

        this.view = new SistemaAcademicoView(this);

        // 1. Barra de Menus (JMenuBar) com JMenu e JMenuItem
        setJMenuBar(view.criarBarraMenus());

        // 5. REQUISITO 5 - LAYOUT 1: BorderLayout aplicado na janela principal
        getContentPane().setLayout(new BorderLayout());

        // Header Superior com Logo 3D
        getContentPane().add(view.criarHeaderApp(), BorderLayout.NORTH);

        // 2. Centro: JTabbedPane contendo as 3 abas principais
        getContentPane().add(view.criarTabbedPane(), BorderLayout.CENTER);

        // Barra de status inferior
        getContentPane().add(view.criarBarraStatus(), BorderLayout.SOUTH);

        // 1. Configurações da Janela: dimensões e centralização
        setSize(850, 640);
        setMinimumSize(new Dimension(760, 520));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela
    }

    public static void main(String[] args) {
        // 5. REQUISITO 5: Look and Feel do Sistema Operacional aplicado ANTES de instanciar o JFrame
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fallback silencioso se o SO não disponibilizar L&F nativo
        }

        // Habilita renderização com anti-aliasing no Swing
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        // Instanciação e exibição da interface gráfica na Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            SistemaAcademico app = new SistemaAcademico();
            app.setVisible(true);
        });
    }
}
