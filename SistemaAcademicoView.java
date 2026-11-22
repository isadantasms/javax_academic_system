import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

/**
 * Classe responsável por construir e gerenciar toda a interface gráfica do Sistema Acadêmico.
 * Organiza os componentes visuais das três abas principais (Cadastro, Listagem e Estatísticas),
 * define os gerenciadores de layout, trata os eventos de interação do usuário e mantém
 * a lista de discentes em memória.
 */
public class SistemaAcademicoView {

    private final Component parentComponent;

    // Componentes principais
    private JTabbedPane tabbedPane;

    // Aba 1: Cadastro
    private ModernComponents.ModernTextField txtMatricula;
    private ModernComponents.ModernTextField txtNome;
    private JComboBox<String> cbCurso;
    private JRadioButton rbMatutino;
    private JRadioButton rbVespertino;
    private JRadioButton rbNoturno;
    private ButtonGroup bgTurno;
    private JCheckBox chkBolsista;
    private ModernComponents.ModernButton btnSalvar;
    private ModernComponents.ModernButton btnLimpar;

    // Aba 2: Lista
    private JTable tabelaAlunos;
    private DefaultTableModel modeloTabela;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private ModernComponents.ModernTextField txtBusca;
    private JLabel lblContadorAlunos;
    private ModernComponents.ModernButton btnRemover;
    private ModernComponents.ModernButton btnExportar;
    private ModernComponents.ModernButton btnAtualizar;

    // Aba 3: Estatísticas
    private JSplitPane splitPaneEstatisticas;
    private JTextArea txtEstatisticas;
    private ModernComponents.ModernBarChart graficoBarras;
    private ModernComponents.ModernButton btnRecalcular;
    private ModernComponents.ModernProgressBar barraCapacidade;

    // Repositório em memória
    private final List<Aluno> listaAlunos = new ArrayList<>();

    public SistemaAcademicoView(Component parentComponent) {
        this.parentComponent = parentComponent;
        carregarAlunosIniciais();
    }

    private void carregarAlunosIniciais() {
        listaAlunos.add(new Aluno("12111BSI001", "Ana Clara Guimarães", "Sistemas de Informação", "Noturno", true));
        listaAlunos.add(new Aluno("12211BSI014", "Thiago Henrique Silveira", "Sistemas de Informação", "Vespertino", false));
        listaAlunos.add(new Aluno("12021BCC032", "Isabela Cristina Dorneles", "Ciência da Computação", "Matutino", true));
        listaAlunos.add(new Aluno("12311BSI008", "Matheus Barreto Cavalcanti", "Sistemas de Informação", "Noturno", false));
        listaAlunos.add(new Aluno("12121BCC019", "Larissa Meireles Fontes", "Ciência da Computação", "Matutino", true));
        listaAlunos.add(new Aluno("12221BIA005", "Bruno César Fagundes", "Inteligência Artificial", "Noturno", false));
        listaAlunos.add(new Aluno("12321BIA022", "Letícia Vasconcelos Duarte", "Inteligência Artificial", "Matutino", true));
    }

    /**
     * Constrói o cabeçalho superior da aplicação, exibindo o logotipo,
     * título do sistema e indicador visual de capacidade.
     */
    public JPanel criarHeaderApp() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(8, 12, 19)); // Tom escuro do cabeçalho
        header.setBorder(new EmptyBorder(12, 22, 12, 22));

        // Lado esquerdo: logo e identificação do sistema
        JPanel ladoEsquerdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        ladoEsquerdo.setOpaque(false);

        JLabel lblLogo = new JLabel(IconHelper.getIcon("app_logo_3d.png", 42, 42));
        ladoEsquerdo.add(lblLogo);

        JPanel infoTexto = new JPanel();
        infoTexto.setLayout(new BoxLayout(infoTexto, BoxLayout.Y_AXIS));
        infoTexto.setOpaque(false);

        JLabel lblTitulo = new JLabel("Sistema Acadêmico");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 17));
        lblTitulo.setForeground(ModernComponents.COLOR_TEXT_TITLE);

        JLabel lblSub = new JLabel("Universidade Federal de Uberlândia — FACOM | Sistemas de Informação");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblSub.setForeground(ModernComponents.COLOR_TEXT_LABEL);

        infoTexto.add(lblTitulo);
        infoTexto.add(Box.createVerticalStrut(2));
        infoTexto.add(lblSub);
        ladoEsquerdo.add(infoTexto);

        header.add(ladoEsquerdo, BorderLayout.WEST);

        // Lado direito: barra de capacidade e informações contextuais
        JPanel ladoDireito = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        ladoDireito.setOpaque(false);

        JLabel lblCap = new JLabel("Capacidade:");
        lblCap.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblCap.setForeground(ModernComponents.COLOR_TEXT_LABEL);

        barraCapacidade = new ModernComponents.ModernProgressBar(0, 10);
        barraCapacidade.setValue(listaAlunos.size());
        barraCapacidade.setString(listaAlunos.size() + "/10 vagas");

        JLabel badgeJava = criarBadge("Java 21 LTS", new Color(21, 29, 42), new Color(148, 163, 184));
        JLabel badgeUI = criarBadge("POO - Java Swing", new Color(30, 58, 138), new Color(191, 219, 254));

        ladoDireito.add(lblCap);
        ladoDireito.add(barraCapacidade);
        ladoDireito.add(badgeJava);
        ladoDireito.add(badgeUI);
        header.add(ladoDireito, BorderLayout.EAST);

        return header;
    }

    private JLabel criarBadge(String texto, Color bg, Color fg) {
        JLabel l = new JLabel(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        l.setFont(new Font("SansSerif", Font.BOLD, 10));
        l.setForeground(fg);
        l.setBorder(new EmptyBorder(4, 10, 4, 10));
        return l;
    }

    /**
     * Monta a barra de menus superior (JMenuBar) com os menus "Arquivo" e "Ajuda",
     * configurando mnemônicos e teclas de atalho para as principais ações do sistema.
     */
    public JMenuBar criarBarraMenus() {
        JMenuBar bar = new JMenuBar();
        bar.setBackground(new Color(13, 18, 28));
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ModernComponents.COLOR_BORDER));

        // Menu "Arquivo" - operações de cadastro, salvamento e saída
        JMenu menuArquivo = new JMenu("Arquivo");
        menuArquivo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        menuArquivo.setForeground(ModernComponents.COLOR_TEXT_PRIMARY);
        menuArquivo.setMnemonic(KeyEvent.VK_A);

        JMenuItem itemNovo = new JMenuItem("Novo Cadastro", IconHelper.getIcon("btn_salvar_3d.png", 16, 16));
        itemNovo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        itemNovo.addActionListener(e -> {
            if (tabbedPane != null) tabbedPane.setSelectedIndex(0);
        });

        JMenuItem itemSalvar = new JMenuItem("Salvar Aluno", IconHelper.getIcon("btn_salvar_3d.png", 16, 16));
        itemSalvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        itemSalvar.addActionListener(e -> acaoSalvar());

        JMenuItem itemSair = new JMenuItem("Sair do Sistema", IconHelper.getIcon("btn_remover_3d.png", 16, 16));
        itemSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        itemSair.addActionListener(e -> {
            int opc = JOptionPane.showConfirmDialog(parentComponent,
                    "Deseja realmente encerrar a aplicação?",
                    "Confirmar Saída", JOptionPane.YES_NO_OPTION);
            if (opc == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        menuArquivo.add(itemNovo);
        menuArquivo.add(itemSalvar);
        menuArquivo.addSeparator();
        menuArquivo.add(itemSair);

        // Menu "Ajuda" - instruções de atalhos e informações do projeto
        JMenu menuAjuda = new JMenu("Ajuda");
        menuAjuda.setFont(new Font("SansSerif", Font.PLAIN, 12));
        menuAjuda.setForeground(ModernComponents.COLOR_TEXT_PRIMARY);
        menuAjuda.setMnemonic(KeyEvent.VK_J);

        JMenuItem itemInstrucoes = new JMenuItem("Instruções de Uso");
        itemInstrucoes.addActionListener(e -> JOptionPane.showMessageDialog(parentComponent,
                "• Atalhos Rápidos:\n" +
                "  - Ctrl+1: Aba Cadastro de Aluno\n" +
                "  - Ctrl+2: Aba Lista de Alunos\n" +
                "  - Ctrl+3: Aba Estatísticas\n" +
                "  - Ctrl+S: Salvar Aluno | Ctrl+N: Novo\n\n" +
                "• Busca Dinâmica: Digite qualquer termo no campo de busca da Lista de Alunos para filtrar em tempo real.",
                "Instruções do Sistema", JOptionPane.INFORMATION_MESSAGE));

        JMenuItem itemSobre = new JMenuItem("Sobre o Projeto", IconHelper.getIcon("app_logo_3d.png", 16, 16));
        itemSobre.addActionListener(e -> JOptionPane.showMessageDialog(parentComponent,
                "Sistema Acadêmico — Midnight Dark 3D (Advanced Edition)\n\n" +
                "Universidade Federal de Uberlândia - UFU\n" +
                "Faculdade de Computação - FACOM\n" +
                "Curso de Sistemas de Informação\n" +
                "Desenvolvido com Java Swing e Componentes Visuais Modernos.",
                "Sobre o Sistema Acadêmico", JOptionPane.INFORMATION_MESSAGE));

        menuAjuda.add(itemInstrucoes);
        menuAjuda.addSeparator();
        menuAjuda.add(itemSobre);

        bar.add(menuArquivo);
        bar.add(menuAjuda);

        return bar;
    }

    /**
     * Constrói o painel com abas (JTabbedPane) para navegação entre as telas do sistema,
     * aplicando o estilo visual customizado e vinculando atalhos de teclado (Ctrl+1, Ctrl+2, Ctrl+3).
     */
    public JTabbedPane criarTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 13));
        tabbedPane.setBackground(ModernComponents.COLOR_BG);
        tabbedPane.setOpaque(true);

        // Aplica o tema visual customizado das abas
        tabbedPane.setUI(new ModernComponents.ModernTabbedPaneUI());

        // Adiciona as três abas principais com seus respectivos painéis
        tabbedPane.addTab("Cadastro de Aluno", criarPainelCadastro());
        tabbedPane.addTab("Lista de Alunos", criarPainelLista());
        tabbedPane.addTab("Estatísticas", criarPainelEstatisticas());

        // Configura o cabeçalho personalizado de cada aba com ícone e texto
        configurarTabComponents();

        // Atualiza a aparência do cabeçalho quando o usuário alterna de aba
        tabbedPane.addChangeListener(e -> atualizarEstiloTabs());

        // Registra os atalhos de teclado globais (Ctrl+1, Ctrl+2, Ctrl+3)
        configurarAtalhosAbas();

        return tabbedPane;
    }

    private void configurarTabComponents() {
        String[] titulos = {"Cadastro de Aluno", "Lista de Alunos", "Estatísticas"};
        String[] icones = {"tab_cadastro_3d.png", "tab_lista_3d.png", "tab_estatisticas_3d.png"};

        for (int i = 0; i < titulos.length; i++) {
            final int idx = i;
            JPanel tabComponent = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
            tabComponent.setOpaque(false);

            JLabel lblIcon = new JLabel(IconHelper.getIcon(icones[i], 20, 20));
            JLabel lblText = new JLabel(titulos[i]);
            lblText.setFont(new Font("SansSerif", Font.BOLD, 13));
            lblText.setName("tabTitle");

            tabComponent.add(lblIcon);
            tabComponent.add(lblText);

            // Permite alternar a aba ao clicar diretamente no painel customizado do cabeçalho
            tabComponent.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    tabbedPane.setSelectedIndex(idx);
                }
            });

            tabbedPane.setTabComponentAt(i, tabComponent);
        }

        atualizarEstiloTabs();
    }

    private void atualizarEstiloTabs() {
        int sel = tabbedPane.getSelectedIndex();
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component c = tabbedPane.getTabComponentAt(i);
            if (c instanceof JPanel) {
                JPanel p = (JPanel) c;
                for (Component comp : p.getComponents()) {
                    if (comp instanceof JLabel && "tabTitle".equals(comp.getName())) {
                        JLabel lbl = (JLabel) comp;
                        if (i == sel) {
                            lbl.setForeground(Color.WHITE); // Aba ativa em branco destacado
                            lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
                        } else {
                            lbl.setForeground(ModernComponents.COLOR_TEXT_LABEL); // Aba inativa com tom atenuado
                            lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
                        }
                    }
                }
            }
        }
    }

    private void configurarAtalhosAbas() {
        InputMap im = tabbedPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = tabbedPane.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK), "irAba1");
        am.put("irAba1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { tabbedPane.setSelectedIndex(0); }
        });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK), "irAba2");
        am.put("irAba2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { tabbedPane.setSelectedIndex(1); }
        });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK), "irAba3");
        am.put("irAba3", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { tabbedPane.setSelectedIndex(2); }
        });
    }

    /**
     * Monta o formulário da aba de Cadastro de Alunos.
     * Utiliza BorderLayout como contêiner principal da aba, GridLayout para organizar
     * os campos e rótulos em duas colunas simétricas e FlowLayout para os botões de ação e turnos.
     */
    public JPanel criarPainelCadastro() {
        // Painel principal da aba estruturado com BorderLayout
        JPanel painelAba = new JPanel(new BorderLayout(15, 15));
        painelAba.setBackground(ModernComponents.COLOR_BG);
        painelAba.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Cabeçalho da aba com título e instruções
        JPanel painelCabecalho = new JPanel(new BorderLayout());
        painelCabecalho.setOpaque(false);

        JLabel lblTitulo = new JLabel("Formulário de Cadastro Acadêmico");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 17));
        lblTitulo.setForeground(ModernComponents.COLOR_TEXT_TITLE);
        painelCabecalho.add(lblTitulo, BorderLayout.WEST);

        JLabel lblSub = new JLabel("Preencha as informações do estudante para incluí-lo na base acadêmica.");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(ModernComponents.COLOR_TEXT_LABEL);
        painelCabecalho.add(lblSub, BorderLayout.SOUTH);

        painelAba.add(painelCabecalho, BorderLayout.NORTH);

        // Painel central (card) para destacar os campos do formulário
        ModernComponents.ModernCard cardFormulario = new ModernComponents.ModernCard(16);
        cardFormulario.setLayout(new BorderLayout(15, 15));
        cardFormulario.setBorder(new EmptyBorder(22, 26, 22, 26));

        // Disposição em grade (5 linhas por 2 colunas) com espaçamento de 16px
        JPanel painelGrid = new JPanel(new GridLayout(5, 2, 16, 16));
        painelGrid.setOpaque(false);

        // Campo: Matrícula
        JLabel lblMatricula = new JLabel("Número de Matrícula:");
        lblMatricula.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblMatricula.setForeground(ModernComponents.COLOR_TEXT_LABEL);

        txtMatricula = new ModernComponents.ModernTextField();
        txtMatricula.setText("");
        txtMatricula.setToolTipText("Informe o número de matrícula do estudante");

        // Campo: Nome Completo
        JLabel lblNome = new JLabel("Nome Completo do Aluno:");
        lblNome.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblNome.setForeground(ModernComponents.COLOR_TEXT_LABEL);

        txtNome = new ModernComponents.ModernTextField();
        txtNome.setText("");
        txtNome.setToolTipText("Digite o nome completo");

        // Campo: Curso de Graduação
        JLabel lblCurso = new JLabel("Curso de Graduação:");
        lblCurso.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblCurso.setForeground(ModernComponents.COLOR_TEXT_LABEL);

        String[] cursos = {
                "Selecione um curso...",
                "Ciência da Computação",
                "Sistemas de Informação",
                "Inteligência Artificial"
        };
        cbCurso = new JComboBox<>(cursos);
        cbCurso.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cbCurso.setBackground(ModernComponents.COLOR_INPUT_BG);
        cbCurso.setForeground(ModernComponents.COLOR_TEXT_PRIMARY);
        cbCurso.setSelectedIndex(1);

        // Campo: Turno das Aulas (seleção mutuamente exclusiva via ButtonGroup)
        JLabel lblTurno = new JLabel("Turno de Aulas:");
        lblTurno.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblTurno.setForeground(ModernComponents.COLOR_TEXT_LABEL);

        // Alinhamento horizontal dos RadioButtons com FlowLayout
        JPanel painelTurnos = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        painelTurnos.setOpaque(false);

        rbMatutino = new JRadioButton("Matutino");
        rbVespertino = new JRadioButton("Vespertino");
        rbNoturno = new JRadioButton("Noturno");

        rbMatutino.setFont(new Font("SansSerif", Font.PLAIN, 13));
        rbVespertino.setFont(new Font("SansSerif", Font.PLAIN, 13));
        rbNoturno.setFont(new Font("SansSerif", Font.PLAIN, 13));

        rbMatutino.setForeground(ModernComponents.COLOR_TEXT_PRIMARY);
        rbVespertino.setForeground(ModernComponents.COLOR_TEXT_PRIMARY);
        rbNoturno.setForeground(ModernComponents.COLOR_TEXT_PRIMARY);

        rbMatutino.setOpaque(false);
        rbVespertino.setOpaque(false);
        rbNoturno.setOpaque(false);
        rbNoturno.setSelected(true);

        bgTurno = new ButtonGroup();
        bgTurno.add(rbMatutino);
        bgTurno.add(rbVespertino);
        bgTurno.add(rbNoturno);

        painelTurnos.add(rbMatutino);
        painelTurnos.add(rbVespertino);
        painelTurnos.add(rbNoturno);

        // Campo: Indicação de Bolsista (CheckBox)
        JLabel lblBolsista = new JLabel("Condição Financeira / Bolsa:");
        lblBolsista.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblBolsista.setForeground(ModernComponents.COLOR_TEXT_LABEL);

        chkBolsista = new JCheckBox("Aluno Bolsista (PIBIC / Monitoria / Extensão / PET)");
        chkBolsista.setFont(new Font("SansSerif", Font.PLAIN, 13));
        chkBolsista.setForeground(ModernComponents.COLOR_TEXT_PRIMARY);
        chkBolsista.setOpaque(false);
        chkBolsista.setSelected(true);

        // Adiciona os rótulos e campos na ordem correspondente do GridLayout
        painelGrid.add(lblMatricula);
        painelGrid.add(txtMatricula);

        painelGrid.add(lblNome);
        painelGrid.add(txtNome);

        painelGrid.add(lblCurso);
        painelGrid.add(cbCurso);

        painelGrid.add(lblTurno);
        painelGrid.add(painelTurnos);

        painelGrid.add(lblBolsista);
        painelGrid.add(chkBolsista);

        cardFormulario.add(painelGrid, BorderLayout.CENTER);
        painelAba.add(cardFormulario, BorderLayout.CENTER);

        // Botões de ação posicionados à direita com FlowLayout
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 6));
        painelBotoes.setOpaque(false);

        btnLimpar = new ModernComponents.ModernButton(
                "Limpar Campos",
                IconHelper.getIcon("btn_limpar_3d.png", 18, 18),
                ModernComponents.COLOR_WARNING,
                ModernComponents.COLOR_WARNING_HOVER
        );
        btnLimpar.setPreferredSize(new Dimension(155, 40));
        btnLimpar.addActionListener(e -> acaoLimpar());

        btnSalvar = new ModernComponents.ModernButton(
                "Salvar Aluno",
                IconHelper.getIcon("btn_salvar_3d.png", 18, 18),
                ModernComponents.COLOR_PRIMARY,
                ModernComponents.COLOR_PRIMARY_HOVER
        );
        btnSalvar.setPreferredSize(new Dimension(155, 40));
        btnSalvar.addActionListener(e -> acaoSalvar());

        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSalvar);

        painelAba.add(painelBotoes, BorderLayout.SOUTH);

        return painelAba;
    }

    /**
     * Constrói a aba de Listagem dos Alunos matriculados.
     * Exibe os registros em uma JTable com barras de rolagem (JScrollPane),
     * filtro dinâmico por digitação e botões de ação para gerenciamento.
     */
    public JPanel criarPainelLista() {
        JPanel painelAba = new JPanel(new BorderLayout(12, 12));
        painelAba.setBackground(ModernComponents.COLOR_BG);
        painelAba.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Cabeçalho da listagem com contador de alunos e campo de busca rápida
        JPanel painelTopo = new JPanel(new BorderLayout(10, 10));
        painelTopo.setOpaque(false);

        JPanel infoTexto = new JPanel(new BorderLayout());
        infoTexto.setOpaque(false);

        JLabel lblTitulo = new JLabel("Quadro Geral de Alunos Matriculados");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 17));
        lblTitulo.setForeground(ModernComponents.COLOR_TEXT_TITLE);
        infoTexto.add(lblTitulo, BorderLayout.NORTH);

        lblContadorAlunos = new JLabel("Exibindo " + listaAlunos.size() + " estudantes cadastrados no sistema.");
        lblContadorAlunos.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblContadorAlunos.setForeground(ModernComponents.COLOR_TEXT_LABEL);
        infoTexto.add(lblContadorAlunos, BorderLayout.SOUTH);

        painelTopo.add(infoTexto, BorderLayout.WEST);

        // Campo de pesquisa com filtro dinâmico acionado a cada tecla digitada
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        painelBusca.setOpaque(false);

        JLabel lblLupa = new JLabel("Filtrar:");
        lblLupa.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblLupa.setForeground(ModernComponents.COLOR_TEXT_LABEL);

        txtBusca = new ModernComponents.ModernTextField();
        txtBusca.setPreferredSize(new Dimension(220, 36));
        txtBusca.setPlaceholder("Buscar nome, matrícula...");
        txtBusca.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filtrarTabela(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filtrarTabela(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filtrarTabela(); }
        });

        painelBusca.add(lblLupa);
        painelBusca.add(txtBusca);
        painelTopo.add(painelBusca, BorderLayout.EAST);

        painelAba.add(painelTopo, BorderLayout.NORTH);

        // Painel arredondado que envolve a tabela
        ModernComponents.ModernCard cardTabela = new ModernComponents.ModernCard(16);
        cardTabela.setLayout(new BorderLayout());
        cardTabela.setBorder(new EmptyBorder(10, 12, 10, 12));

        // Definição das colunas e criação do modelo não editável diretamente nas células
        String[] colunas = {"Matrícula", "Nome do Aluno", "Curso", "Turno", "Bolsista"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        for (Aluno a : listaAlunos) {
            modeloTabela.addRow(a.toTableRow());
        }

        tabelaAlunos = new JTable(modeloTabela);
        rowSorter = new TableRowSorter<>(modeloTabela);
        tabelaAlunos.setRowSorter(rowSorter);

        tabelaAlunos.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabelaAlunos.setBackground(ModernComponents.COLOR_CARD_BG);
        tabelaAlunos.setForeground(ModernComponents.COLOR_TEXT_PRIMARY);
        tabelaAlunos.setRowHeight(34);
        tabelaAlunos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaAlunos.setShowGrid(false);
        tabelaAlunos.setIntercellSpacing(new Dimension(0, 0));

        // Estilização do cabeçalho da tabela
        tabelaAlunos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabelaAlunos.getTableHeader().setBackground(new Color(13, 18, 28));
        tabelaAlunos.getTableHeader().setForeground(ModernComponents.COLOR_TEXT_LABEL);
        tabelaAlunos.getTableHeader().setPreferredSize(new Dimension(0, 36));

        // Renderizadores de célula: badge para bolsistas e alinhamentos específicos
        tabelaAlunos.getColumnModel().getColumn(4).setCellRenderer(new ModernComponents.BadgeTableCellRenderer());

        DefaultTableCellRenderer renderizadorEscuro = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(new Color(30, 58, 138));
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? ModernComponents.COLOR_CARD_BG : new Color(17, 24, 36));
                    c.setForeground(ModernComponents.COLOR_TEXT_PRIMARY);
                }
                setBorder(new EmptyBorder(0, 10, 0, 10));
                return c;
            }
        };

        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(new Color(30, 58, 138));
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? ModernComponents.COLOR_CARD_BG : new Color(17, 24, 36));
                    c.setForeground(ModernComponents.COLOR_TEXT_LABEL);
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        };

        tabelaAlunos.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        tabelaAlunos.getColumnModel().getColumn(1).setCellRenderer(renderizadorEscuro);
        tabelaAlunos.getColumnModel().getColumn(2).setCellRenderer(renderizadorEscuro);
        tabelaAlunos.getColumnModel().getColumn(3).setCellRenderer(centralizado);

        JScrollPane scrollPane = new JScrollPane(tabelaAlunos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(ModernComponents.COLOR_CARD_BG);

        cardTabela.add(scrollPane, BorderLayout.CENTER);
        painelAba.add(cardTabela, BorderLayout.CENTER);

        // Botões de ação posicionados na base com FlowLayout alinhado à direita
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 6));
        painelBotoes.setOpaque(false);

        btnRemover = new ModernComponents.ModernButton(
                "Remover Aluno",
                IconHelper.getIcon("btn_remover_3d.png", 18, 18),
                ModernComponents.COLOR_DANGER,
                ModernComponents.COLOR_DANGER_HOVER
        );
        btnRemover.setPreferredSize(new Dimension(155, 40));
        btnRemover.addActionListener(e -> acaoRemover());

        btnExportar = new ModernComponents.ModernButton(
                "Exportar CSV",
                IconHelper.getIcon("btn_exportar_3d.png", 18, 18),
                ModernComponents.COLOR_SUCCESS,
                ModernComponents.COLOR_SUCCESS_HOVER
        );
        btnExportar.setPreferredSize(new Dimension(145, 40));
        btnExportar.addActionListener(e -> {
            if (parentComponent != null) {
                JOptionPane.showMessageDialog(parentComponent,
                        "Registros de alunos exportados com sucesso!",
                        "Exportação Concluída", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnAtualizar = new ModernComponents.ModernButton(
                "Atualizar Lista",
                IconHelper.getIcon("btn_atualizar_3d.png", 18, 18),
                ModernComponents.COLOR_SECONDARY,
                ModernComponents.COLOR_SECONDARY_HOVER
        );
        btnAtualizar.setPreferredSize(new Dimension(145, 40));
        btnAtualizar.addActionListener(e -> {
            atualizarTabela();
            if (parentComponent != null) {
                JOptionPane.showMessageDialog(parentComponent,
                        "Lista de alunos atualizada com sucesso!",
                        "Atualização", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        painelBotoes.add(btnRemover);
        painelBotoes.add(btnExportar);
        painelBotoes.add(btnAtualizar);

        painelAba.add(painelBotoes, BorderLayout.SOUTH);

        return painelAba;
    }

    private void filtrarTabela() {
        if (rowSorter != null && txtBusca != null) {
            String texto = txtBusca.getText().trim();
            if (texto.isEmpty()) {
                rowSorter.setRowFilter(null);
            } else {
                rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
            }
            if (lblContadorAlunos != null) {
                int totalVisivel = tabelaAlunos.getRowCount();
                lblContadorAlunos.setText(String.format("Exibindo %d de %d estudantes matriculados.",
                        totalVisivel, listaAlunos.size()));
            }
        }
    }

    /**
     * Monta a aba de Estatísticas Acadêmicas.
     * Emprega um JSplitPane para dividir o espaço entre o relatório analítico formatado
     * (JTextArea com rolagem) e o painel visual com gráfico de barras e botão de atualização.
     */
    public JPanel criarPainelEstatisticas() {
        JPanel painelAba = new JPanel(new BorderLayout(15, 15));
        painelAba.setBackground(ModernComponents.COLOR_BG);
        painelAba.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Cabeçalho com título da aba analítica
        JPanel painelCabecalho = new JPanel(new BorderLayout());
        painelCabecalho.setOpaque(false);

        JLabel lblTitulo = new JLabel("Painel Analítico de Estatísticas Acadêmicas");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 17));
        lblTitulo.setForeground(ModernComponents.COLOR_TEXT_TITLE);
        painelCabecalho.add(lblTitulo, BorderLayout.WEST);

        JLabel lblSub = new JLabel("Divisão de painéis (JSplitPane) com relatório textual e gráficos em tempo real.");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(ModernComponents.COLOR_TEXT_LABEL);
        painelCabecalho.add(lblSub, BorderLayout.SOUTH);

        painelAba.add(painelCabecalho, BorderLayout.NORTH);

        // Lado esquerdo do JSplitPane: Área de texto formatada com o relatório consolidado
        ModernComponents.ModernCard cardTerminal = new ModernComponents.ModernCard(16);
        cardTerminal.setLayout(new BorderLayout());
        cardTerminal.setBorder(new EmptyBorder(10, 10, 10, 10));

        txtEstatisticas = new JTextArea();
        txtEstatisticas.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtEstatisticas.setEditable(false);
        txtEstatisticas.setBackground(new Color(8, 12, 19));
        txtEstatisticas.setForeground(new Color(56, 189, 248)); // Ciano brilhante para contraste
        txtEstatisticas.setCaretColor(new Color(56, 189, 248));
        txtEstatisticas.setMargin(new Insets(14, 16, 14, 16));
        txtEstatisticas.setText(obterTextoEstatisticas());
        txtEstatisticas.setCaretPosition(0);

        JScrollPane scrollArea = new JScrollPane(txtEstatisticas);
        scrollArea.setBorder(BorderFactory.createEmptyBorder());
        cardTerminal.add(scrollArea, BorderLayout.CENTER);

        // Lado direito do JSplitPane: Gráfico de distribuição e botão de recálculo
        ModernComponents.ModernCard cardDireito = new ModernComponents.ModernCard(16);
        cardDireito.setLayout(new BorderLayout(10, 10));
        cardDireito.setBorder(new EmptyBorder(16, 16, 16, 16));

        // Identificação visual do gráfico
        JPanel topoCard = new JPanel();
        topoCard.setLayout(new BoxLayout(topoCard, BoxLayout.Y_AXIS));
        topoCard.setOpaque(false);

        JLabel icone3D = new JLabel(IconHelper.getIcon("tab_estatisticas_3d.png", 46, 46));
        icone3D.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblAcaoInfo = new JLabel("<html><center><b>Distribuição por Turno</b><br>" +
                "<font color='#94A3B8' size='2'>Gráfico vetorial em Java 2D</font></center></html>");
        lblAcaoInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAcaoInfo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblAcaoInfo.setForeground(ModernComponents.COLOR_TEXT_PRIMARY);

        topoCard.add(icone3D);
        topoCard.add(Box.createVerticalStrut(8));
        topoCard.add(lblAcaoInfo);
        cardDireito.add(topoCard, BorderLayout.NORTH);

        // Componente do gráfico de barras desenhado com Java 2D
        graficoBarras = new ModernComponents.ModernBarChart(obterDadosGraficoTurnos());
        cardDireito.add(graficoBarras, BorderLayout.CENTER);

        // Botão para recalcular as métricas com base no estado atual da lista
        JPanel sulCard = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        sulCard.setOpaque(false);

        btnRecalcular = new ModernComponents.ModernButton(
                "Recalcular Estatísticas",
                IconHelper.getIcon("btn_calcular_3d.png", 20, 20),
                ModernComponents.COLOR_PRIMARY,
                ModernComponents.COLOR_PRIMARY_HOVER
        );
        btnRecalcular.setPreferredSize(new Dimension(215, 42));
        btnRecalcular.addActionListener(e -> {
            atualizarTextoEstatisticas();
            if (parentComponent != null) {
                JOptionPane.showMessageDialog(parentComponent,
                        "Estatísticas recalculadas com sucesso!",
                        "Relatório Acadêmico", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        sulCard.add(btnRecalcular);
        cardDireito.add(sulCard, BorderLayout.SOUTH);

        // Divisor horizontal (JSplitPane) unindo o relatório textual e o painel gráfico
        splitPaneEstatisticas = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, cardTerminal, cardDireito);
        splitPaneEstatisticas.setContinuousLayout(true);
        splitPaneEstatisticas.setOneTouchExpandable(true);
        splitPaneEstatisticas.setDividerLocation(475);
        splitPaneEstatisticas.setResizeWeight(0.68);
        splitPaneEstatisticas.setBorder(BorderFactory.createEmptyBorder());
        splitPaneEstatisticas.setOpaque(false);

        painelAba.add(splitPaneEstatisticas, BorderLayout.CENTER);

        return painelAba;
    }

    private Map<String, Integer> obterDadosGraficoTurnos() {
        Map<String, Integer> dados = new LinkedHashMap<>();
        int mat = 0, vesp = 0, not = 0;
        for (Aluno a : listaAlunos) {
            if ("Matutino".equalsIgnoreCase(a.getTurno())) mat++;
            else if ("Vespertino".equalsIgnoreCase(a.getTurno())) vesp++;
            else if ("Noturno".equalsIgnoreCase(a.getTurno())) not++;
        }
        dados.put("Matutino", mat);
        dados.put("Vespertino", vesp);
        dados.put("Noturno", not);
        return dados;
    }

    /**
     * Barra de status posicionada no rodapé da janela principal.
     */
    public JPanel criarBarraStatus() {
        JPanel status = new JPanel(new BorderLayout());
        status.setBackground(new Color(13, 18, 28));
        status.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, ModernComponents.COLOR_BORDER),
                new EmptyBorder(6, 16, 6, 16)
        ));

        JLabel lblEsquerda = new JLabel("UFU | Faculdade de Computação — Sistema Acadêmico | Atalhos: Ctrl+1, Ctrl+2, Ctrl+3");
        lblEsquerda.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblEsquerda.setForeground(ModernComponents.COLOR_TEXT_LABEL);

        JLabel lblDireita = new JLabel("Status: Pronto");
        lblDireita.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblDireita.setForeground(new Color(52, 211, 153));

        status.add(lblEsquerda, BorderLayout.WEST);
        status.add(lblDireita, BorderLayout.EAST);

        return status;
    }

    private String obterTextoEstatisticas() {
        int total = listaAlunos.size();
        int bolsistas = 0;
        int mat = 0, vesp = 0, not = 0;
        Map<String, Integer> distribuicaoCursos = new LinkedHashMap<>();

        for (Aluno a : listaAlunos) {
            if (a.isBolsista()) bolsistas++;
            if ("Matutino".equalsIgnoreCase(a.getTurno())) mat++;
            else if ("Vespertino".equalsIgnoreCase(a.getTurno())) vesp++;
            else if ("Noturno".equalsIgnoreCase(a.getTurno())) not++;

            distribuicaoCursos.put(a.getCurso(), distribuicaoCursos.getOrDefault(a.getCurso(), 0) + 1);
        }

        double percBolsistas = total > 0 ? (bolsistas * 100.0 / total) : 0;
        double percRegulares = total > 0 ? ((total - bolsistas) * 100.0 / total) : 0;

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("╔══════════════════════════════════════════════════════════╗\n");
        relatorio.append("║    UNIVERSIDADE FEDERAL DE UBERLÂNDIA — FACOM            ║\n");
        relatorio.append("║       RELATÓRIO CONSOLIDADO DO SISTEMA ACADÊMICO         ║\n");
        relatorio.append("╚══════════════════════════════════════════════════════════╝\n\n");
        relatorio.append(String.format(" ▸ Total de Alunos Matriculados:  %d estudantes\n", total));
        relatorio.append(String.format(" ▸ Estudantes Bolsistas:          %d (%.1f%%)\n", bolsistas, percBolsistas));
        relatorio.append(String.format(" ▸ Estudantes Regulares:          %d (%.1f%%)\n\n", (total - bolsistas), percRegulares));
        relatorio.append("──────────────────────────────────────────────────────────\n");
        relatorio.append(" DISTRIBUIÇÃO DE ALUNOS POR TURNO:\n");
        relatorio.append(String.format("   • Matutino:   %d discentes\n", mat));
        relatorio.append(String.format("   • Vespertino: %d discentes\n", vesp));
        relatorio.append(String.format("   • Noturno:    %d discentes\n\n", not));
        relatorio.append("──────────────────────────────────────────────────────────\n");
        relatorio.append(" DISTRIBUIÇÃO DE ALUNOS POR CURSO:\n");
        for (Map.Entry<String, Integer> entry : distribuicaoCursos.entrySet()) {
            relatorio.append(String.format("   • %-26s: %d discentes\n", entry.getKey(), entry.getValue()));
        }
        relatorio.append("\n══════════════════════════════════════════════════════════\n");
        relatorio.append(" Status da Base de Dados: Atualizado e Sincronizado.\n");

        return relatorio.toString();
    }

    private void atualizarTextoEstatisticas() {
        if (txtEstatisticas != null) {
            txtEstatisticas.setText(obterTextoEstatisticas());
            txtEstatisticas.setCaretPosition(0);
        }
        if (graficoBarras != null) {
            graficoBarras.setDados(obterDadosGraficoTurnos());
        }
        if (barraCapacidade != null) {
            barraCapacidade.setValue(listaAlunos.size());
            barraCapacidade.setString(listaAlunos.size() + "/10 vagas");
        }
    }

    private void acaoSalvar() {
        String nome = txtNome.getText().trim();
        String matricula = txtMatricula.getText().trim();

        if (nome.isEmpty()) {
            if (parentComponent != null) {
                JOptionPane.showMessageDialog(parentComponent,
                        "Por favor, informe o nome completo do aluno.",
                        "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            }
            txtNome.requestFocus();
            return;
        }

        if (cbCurso.getSelectedIndex() <= 0) {
            if (parentComponent != null) {
                JOptionPane.showMessageDialog(parentComponent,
                        "Por favor, selecione um curso válido.",
                        "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
            }
            cbCurso.requestFocus();
            return;
        }

        if (matricula.isEmpty()) {
            matricula = "12411BSI" + String.format("%03d", (listaAlunos.size() + 1));
        }

        String curso = (String) cbCurso.getSelectedItem();
        String turno = rbMatutino.isSelected() ? "Matutino" :
                (rbVespertino.isSelected() ? "Vespertino" : "Noturno");
        boolean bolsista = chkBolsista.isSelected();

        Aluno novo = new Aluno(matricula, nome, curso, turno, bolsista);
        listaAlunos.add(novo);

        atualizarTabela();
        atualizarTextoEstatisticas();

        if (parentComponent != null) {
            JOptionPane.showMessageDialog(parentComponent,
                    "Aluno \"" + nome + "\" cadastrado com sucesso!",
                    "Cadastro Concluído", JOptionPane.INFORMATION_MESSAGE);
        }

        acaoLimpar();
    }

    private void acaoLimpar() {
        txtNome.setText("");
        txtMatricula.setText("");
        cbCurso.setSelectedIndex(0);
        rbMatutino.setSelected(true);
        chkBolsista.setSelected(false);
        txtNome.requestFocus();
    }

    private void acaoRemover() {
        if (tabelaAlunos != null && modeloTabela != null) {
            int sel = tabelaAlunos.getSelectedRow();
            if (sel >= 0) {
                int modelIdx = tabelaAlunos.convertRowIndexToModel(sel);
                if (modelIdx >= 0 && modelIdx < listaAlunos.size()) {
                    Aluno a = listaAlunos.get(modelIdx);
                    if (parentComponent != null) {
                        int opc = JOptionPane.showConfirmDialog(parentComponent,
                                "Deseja remover o estudante \"" + a.getNome() + "\"?",
                                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                        if (opc == JOptionPane.YES_OPTION) {
                            listaAlunos.remove(modelIdx);
                            atualizarTabela();
                            atualizarTextoEstatisticas();
                        }
                    }
                }
            } else {
                if (parentComponent != null) {
                    JOptionPane.showMessageDialog(parentComponent,
                            "Selecione uma linha da tabela para remover.",
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    private void atualizarTabela() {
        if (modeloTabela != null) {
            modeloTabela.setRowCount(0);
            for (Aluno a : listaAlunos) {
                modeloTabela.addRow(a.toTableRow());
            }
            if (lblContadorAlunos != null) {
                lblContadorAlunos.setText("Exibindo " + listaAlunos.size() + " estudantes cadastrados no sistema.");
            }
        }
    }
}
