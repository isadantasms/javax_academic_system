/**
 * Classe de modelo que representa um estudante no sistema acadêmico.
 * Guarda os atributos cadastrais com encapsulamento (getters e setters)
 * e oferece o método utilitário toTableRow para preenchimento da JTable.
 */
public class Aluno {
    private String matricula;
    private String nome;
    private String curso;
    private String turno;
    private boolean bolsista;

    public Aluno(String matricula, String nome, String curso, String turno, boolean bolsista) {
        this.matricula = matricula;
        this.nome = nome;
        this.curso = curso;
        this.turno = turno;
        this.bolsista = bolsista;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public boolean isBolsista() {
        return bolsista;
    }

    public void setBolsista(boolean bolsista) {
        this.bolsista = bolsista;
    }

    public String getBolsistaFormatado() {
        return bolsista ? "Sim" : "Não";
    }

    // Converte os dados do aluno em um array de objetos para inserção direta no modelo da tabela
    public Object[] toTableRow() {
        return new Object[]{matricula, nome, curso, turno, getBolsistaFormatado()};
    }
}
