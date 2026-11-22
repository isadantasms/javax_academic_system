#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from reportlab.lib.pagesizes import A4
from reportlab.lib import colors
from reportlab.platypus import (
    SimpleDocTemplate, Paragraph, Spacer, Image, Table, TableStyle, PageBreak, HRFlowable
)
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib.units import cm

def gerar_pdf():
    pdf_filename = "Trabalho06_SistemaAcademico_Prints.pdf"
    doc = SimpleDocTemplate(
        pdf_filename,
        pagesize=A4,
        leftMargin=1.5 * cm,
        rightMargin=1.5 * cm,
        topMargin=1.2 * cm,
        bottomMargin=1.2 * cm
    )

    styles = getSampleStyleSheet()

    title_univ = ParagraphStyle(
        'UnivHeader',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=10,
        leading=13,
        alignment=1,
        textColor=colors.HexColor('#0F172A')
    )

    title_doc = ParagraphStyle(
        'DocTitle',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=13.5,
        leading=16,
        alignment=1,
        textColor=colors.HexColor('#1D4ED8'),
        spaceBefore=4,
        spaceAfter=2
    )

    sub_doc = ParagraphStyle(
        'DocSub',
        parent=styles['Normal'],
        fontName='Helvetica',
        fontSize=9,
        leading=11,
        alignment=1,
        textColor=colors.HexColor('#475569'),
        spaceAfter=6
    )

    h1_style = ParagraphStyle(
        'SectionH1',
        parent=styles['Normal'],
        fontName='Helvetica-Bold',
        fontSize=11.5,
        leading=14,
        textColor=colors.HexColor('#0F172A'),
        spaceBefore=6,
        spaceAfter=3
    )

    body_style = ParagraphStyle(
        'BodyCustom',
        parent=styles['Normal'],
        fontName='Helvetica',
        fontSize=8.5,
        leading=11.5,
        textColor=colors.HexColor('#334155'),
        alignment=4
    )

    caption_style = ParagraphStyle(
        'Caption',
        parent=styles['Normal'],
        fontName='Helvetica-Oblique',
        fontSize=8,
        leading=10,
        alignment=1,
        textColor=colors.HexColor('#64748B'),
        spaceBefore=3,
        spaceAfter=4
    )

    story = []

    # ==================== PÁGINA 1 ====================
    header_text = """
    <b>MINISTÉRIO DA EDUCAÇÃO — UNIVERSIDADE FEDERAL DE UBERLÂNDIA (UFU)</b><br/>
    <b>FACULDADE DE COMPUTAÇÃO (FACOM) — SISTEMAS DE INFORMAÇÃO</b>
    """
    story.append(Paragraph(header_text, title_univ))
    story.append(Spacer(1, 0.2 * cm))
    story.append(HRFlowable(width="100%", thickness=1.5, color=colors.HexColor('#2563EB'), spaceAfter=5))

    story.append(Paragraph("TRABALHO 06: INTERFACES GRÁFICAS EM JAVA SWING", title_doc))
    story.append(Paragraph("<b>Tema: Sistema Acadêmico</b> | Relatório de Modelagem Visual e Conferência de Telas", sub_doc))

    req_data = [
        [Paragraph("<b>Requisito do Trabalho</b>", body_style), Paragraph("<b>Componentes e Layouts Implementados</b>", body_style), Paragraph("<b>Status</b>", body_style)],
        [
            Paragraph("<b>1. Janela Principal (JFrame)</b>", body_style),
            Paragraph("JFrame centralizado (820x570), Look and Feel nativo do SO, JMenuBar com 'Arquivo' e 'Ajuda' via JMenu e JMenuItem.", body_style),
            Paragraph("<font color='#16A34A'><b>Atendido</b></font>", body_style)
        ],
        [
            Paragraph("<b>2. JTabbedPane e Cadastro</b>", body_style),
            Paragraph("3 abas distintas. Aba Cadastro: JLabel, JTextField, JComboBox, JRadioButton (ButtonGroup), JCheckBox e JButton (BorderLayout, GridLayout, FlowLayout).", body_style),
            Paragraph("<font color='#16A34A'><b>Atendido</b></font>", body_style)
        ],
        [
            Paragraph("<b>3. Lista de Alunos</b>", body_style),
            Paragraph("JTable em JScrollPane com colunas apropriadas (Matrícula, Nome, Curso, Turno, Bolsista) e botões organizados.", body_style),
            Paragraph("<font color='#16A34A'><b>Atendido</b></font>", body_style)
        ],
        [
            Paragraph("<b>4. Painel Estatísticas</b>", body_style),
            Paragraph("JSplitPane dividindo a tela: JTextArea em JScrollPane à esquerda e JButton de recálculo à direita.", body_style),
            Paragraph("<font color='#16A34A'><b>Atendido</b></font>", body_style)
        ],
        [
            Paragraph("<b>5. Layouts e Look & Feel</b>", body_style),
            Paragraph("Uso de múltiplos layouts (BorderLayout, GridLayout, FlowLayout, GridBagLayout) e Look and Feel do sistema operacional.", body_style),
            Paragraph("<font color='#16A34A'><b>Atendido</b></font>", body_style)
        ]
    ]

    req_table = Table(req_data, colWidths=[4.2 * cm, 11.2 * cm, 2.4 * cm])
    req_table.setStyle(TableStyle([
        ('BACKGROUND', (0, 0), (-1, 0), colors.HexColor('#F8FAFC')),
        ('TEXTCOLOR', (0, 0), (-1, 0), colors.HexColor('#0F172A')),
        ('ALIGN', (0, 0), (-1, -1), 'LEFT'),
        ('VALIGN', (0, 0), (-1, -1), 'MIDDLE'),
        ('INNERGRID', (0, 0), (-1, -1), 0.5, colors.HexColor('#CBD5E1')),
        ('BOX', (0, 0), (-1, -1), 1, colors.HexColor('#94A3B8')),
        ('TOPPADDING', (0, 0), (-1, -1), 3),
        ('BOTTOMPADDING', (0, 0), (-1, -1), 3),
        ('LEFTPADDING', (0, 0), (-1, -1), 5),
        ('RIGHTPADDING', (0, 0), (-1, -1), 5),
    ]))
    story.append(req_table)
    story.append(Spacer(1, 0.25 * cm))

    story.append(Paragraph("1. Aba 'Cadastro de Aluno' (Formulário e Controles de Entrada)", h1_style))
    story.append(Paragraph(
        "Demonstração dos componentes solicitados: <b>JLabel</b> para rótulos, <b>JTextField</b> para nome/matrícula, <b>JComboBox</b> para seleção de curso, <b>JRadioButton</b> com seleção única via <b>ButtonGroup</b> para escolha de turno, <b>JCheckBox</b> para identificação de bolsista e <b>JButton</b> para as ações de Salvar e Limpar. Layouts empregados: <b>BorderLayout</b> (conteiner da aba), <b>GridLayout</b> (5 linhas x 2 colunas com espaçamento padronizado) e <b>FlowLayout</b> (grupo de radio buttons e barra inferior de botões).",
        body_style
    ))
    story.append(Spacer(1, 0.2 * cm))

    img_aba1 = Image("print_aba1_cadastro.png", width=16.8 * cm, height=10.2 * cm)
    story.append(img_aba1)
    story.append(Paragraph("Figura 1: Tela da Aba 'Cadastro de Aluno' com todos os componentes e layouts em funcionamento.", caption_style))

    story.append(PageBreak())

    # ==================== PÁGINA 2 ====================
    story.append(Paragraph("2. Aba 'Lista de Alunos' (JTable em JScrollPane e Ações)", h1_style))
    story.append(Paragraph(
        "Apresentação da <b>JTable</b> inserida dentro de um <b>JScrollPane</b> para rolagem fluida e cabeçalho fixo. A tabela contém colunas devidamente dimensionadas para apresentação de todas as informações cadastradas (Matrícula, Nome do Aluno, Curso, Turno e Condição de Bolsista). O rodapé conta com botões alinhados com <b>FlowLayout</b> ('Remover Aluno', 'Exportar Dados' e 'Atualizar Lista').",
        body_style
    ))
    story.append(Spacer(1, 0.3 * cm))

    img_aba2 = Image("print_aba2_lista.png", width=17.5 * cm, height=11.8 * cm)
    story.append(img_aba2)
    story.append(Paragraph("Figura 2: Tela da Aba 'Lista de Alunos' apresentando o JTable dentro de JScrollPane e botões organizados.", caption_style))

    story.append(Spacer(1, 0.6 * cm))
    story.append(Paragraph("<b>Organização Arquitetural da Aba:</b>", body_style))
    story.append(Paragraph("• <b>Norte (BorderLayout.NORTH):</b> Painel de título e subtítulo com identificação visual clara.", body_style))
    story.append(Paragraph("• <b>Centro (BorderLayout.CENTER):</b> Componente JScrollPane integrando a JTable com DefaultTableModel não-editável diretamente pelas células.", body_style))
    story.append(Paragraph("• <b>Sul (BorderLayout.SOUTH):</b> Painel de controle com FlowLayout(FlowLayout.RIGHT) para os botões de ação.", body_style))

    story.append(PageBreak())

    # ==================== PÁGINA 3 ====================
    story.append(Paragraph("3. Aba 'Estatísticas' (Divisão de Painéis com JSplitPane)", h1_style))
    story.append(Paragraph(
        "Demonstração do componente <b>JSplitPane</b> (orientação horizontal) dividindo a tela em dois painéis redimensionáveis pelo usuário: à esquerda, um <b>JTextArea</b> encapsulado em <b>JScrollPane</b> exibindo relatório consolidado de métricas acadêmicas com fonte monoespaçada; à direita, um painel estruturado com <b>GridBagLayout</b> contendo o <b>JButton</b> 'Recalcular Estatísticas'.",
        body_style
    ))
    story.append(Spacer(1, 0.3 * cm))

    img_aba3 = Image("print_aba3_estatisticas.png", width=17.5 * cm, height=11.8 * cm)
    story.append(img_aba3)
    story.append(Paragraph("Figura 3: Tela da Aba 'Estatísticas' demonstrando o uso de JSplitPane com JTextArea de um lado e JButton do outro.", caption_style))

    story.append(Spacer(1, 0.4 * cm))
    story.append(HRFlowable(width="100%", thickness=1, color=colors.HexColor('#CBD5E1'), spaceAfter=6))

    instrucoes_exec = """
    <b>INSTRUÇÕES PARA COMPILAÇÃO E EXECUÇÃO DO PROJETO EM JAVA:</b><br/>
    O código-fonte foi desenvolvido em Java puro compatível com qualquer IDE (IntelliJ, Eclipse, VS Code, NetBeans) ou via terminal linha de comando:<br/>
    • <b>Compilação no Terminal:</b> <code>javac SistemaAcademico.java SistemaAcademicoView.java</code><br/>
    • <b>Execução no Terminal:</b> <code>java SistemaAcademico</code><br/>
    • <b>Requisitos de Ambiente:</b> Java JDK 8 ou superior (testado no OpenJDK 21 LTS).
    """
    story.append(Paragraph(instrucoes_exec, body_style))

    doc.build(story)
    print("PDF gerado com sucesso:", pdf_filename)

if __name__ == "__main__":
    gerar_pdf()
