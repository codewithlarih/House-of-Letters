package entidades;

import enumeracoes.StatusEmprestimo;
import java.time.LocalDate;
import java.util.Objects;
import util.Validacao;

/**
 * Empréstimo de um livro a um leitor, registrado por um funcionário.
 * COMPOSIÇÃO ("tem-um"): um Emprestimo TEM um Leitor, TEM um Livro e TEM um Funcionario.
 * Esta classe só guarda dados e regras do próprio empréstimo; mexer no estoque do livro
 * é responsabilidade do EmprestimoService (cada classe com uma responsabilidade).
 */
public class Emprestimo {

    private final String codigo;
    private final Leitor leitor;
    private final Livro livro;
    private final Funcionario funcionario;
    private final LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;   // pode ser prorrogada (renovação)
    private LocalDate dataDevolucao;           // null enquanto o livro não for devolvido
    private StatusEmprestimo status;

    /**
     * @param diasPrazo quantidade de dias que o leitor tem para devolver (deve ser maior que zero)
     * @throws IllegalArgumentException se algum dado obrigatório for inválido
     */
    public Emprestimo(String codigo, Leitor leitor, Livro livro, Funcionario funcionario,
                      LocalDate dataEmprestimo, int diasPrazo) {
        this.codigo = Validacao.textoObrigatorio(codigo, "código");
        if (leitor == null || livro == null || funcionario == null) {
            throw new IllegalArgumentException("Leitor, livro e funcionário são obrigatórios.");
        }
        if (dataEmprestimo == null || dataEmprestimo.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data do empréstimo é obrigatória e não pode ser futura.");
        }
        if (diasPrazo <= 0) {
            throw new IllegalArgumentException("O prazo deve ser de pelo menos 1 dia.");
        }
        this.leitor = leitor;
        this.livro = livro;
        this.funcionario = funcionario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = dataEmprestimo.plusDays(diasPrazo);
        this.status = StatusEmprestimo.ATIVO;
    }

    // ----- Getters (os dados principais não têm setter: um empréstimo não "troca" de livro/leitor) -----

    public String getCodigo() {
        return codigo;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public Livro getLivro() {
        return livro;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    // ----- Regras do empréstimo -----

    /** Marca o empréstimo como finalizado na data informada. */
    public void registrarDevolucao(LocalDate data) {
        if (status == StatusEmprestimo.FINALIZADO) {
            throw new IllegalStateException("Este empréstimo já foi finalizado.");
        }
        if (data == null || data.isBefore(dataEmprestimo)) {
            throw new IllegalArgumentException("Data de devolução inválida.");
        }
        this.dataDevolucao = data;
        this.status = StatusEmprestimo.FINALIZADO;
    }

    /** Prorroga o prazo de devolução em alguns dias (só vale para empréstimo ativo). */
    public void renovar(int diasExtras) {
        if (status == StatusEmprestimo.FINALIZADO) {
            throw new IllegalStateException("Não é possível renovar um empréstimo já finalizado.");
        }
        if (diasExtras <= 0) {
            throw new IllegalArgumentException("A renovação deve ser de pelo menos 1 dia.");
        }
        this.dataPrevistaDevolucao = dataPrevistaDevolucao.plusDays(diasExtras);
    }

    /** Está atrasado se ainda não foi devolvido e hoje já passou da data prevista. */
    public boolean estaAtrasado() {
        return status == StatusEmprestimo.ATIVO && LocalDate.now().isAfter(dataPrevistaDevolucao);
    }

    /** Texto de situação mostrado na tabela: ATRASADO, ATIVO ou FINALIZADO. */
    public String getSituacao() {
        return estaAtrasado() ? "ATRASADO" : status.name();
    }

    // Dois empréstimos são iguais se tiverem o mesmo código
    @Override
    public boolean equals(Object outro) {
        if (this == outro) {
            return true;
        }
        if (outro == null || getClass() != outro.getClass()) {
            return false;
        }
        return codigo.equalsIgnoreCase(((Emprestimo) outro).codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo.toLowerCase());
    }

    @Override
    public String toString() {
        return "Empréstimo [" + codigo + "] " + livro.getTitulo() + " -> " + leitor.getNome()
                + " (" + getSituacao() + ")";
    }
}
