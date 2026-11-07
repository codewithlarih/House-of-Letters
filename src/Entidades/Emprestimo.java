package Entidades;

import java.time.LocalDate;
import java.util.Objects;

import Enumeracoes.StatusEmprestimo; 

public class Emprestimo {

   //Atributos
    private String codigo;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;
    private StatusEmprestimo status;

   //Associação com outras entidades
    private Leitor leitor;
    private Livro livro;
    private Funcionario funcionario;

    //Construtor
    public Emprestimo(String codigo, Leitor leitor, Livro livro, Funcionario funcionario, int diasParaDevolucao) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("O código do empréstimo é obrigatório.");
        }
        if (leitor == null) {
            throw new IllegalArgumentException("O leitor é obrigatório.");
        }
        if (livro == null) {
            throw new IllegalArgumentException("O livro é obrigatório.");
        }
        if (funcionario == null) {
            throw new IllegalArgumentException("O funcionário é obrigatório.");
        }

        if (!livro.emprestarExemplar()) {
            throw new IllegalStateException("Não há exemplares disponíveis para empréstimo.");
        }

        this.codigo = codigo.trim();
        this.leitor = leitor;
        this.livro = livro;
        this.funcionario = funcionario;
        this.dataEmprestimo = LocalDate.now();
        this.dataPrevistaDevolucao = this.dataEmprestimo.plusDays(diasParaDevolucao);
        this.status = StatusEmprestimo.ATIVO;
    }

    //Getters e setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public void setDataPrevistaDevolucao(LocalDate dataPrevistaDevolucao) {
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public void registrarDevolucao() {
        if (this.status == StatusEmprestimo.FINALIZADO) {
            throw new IllegalStateException("Este empréstimo já foi finalizado.");
        }
        
        this.dataDevolucao = LocalDate.now();
        this.status = StatusEmprestimo.FINALIZADO;
        this.livro.devolverExemplar();
    }

    public boolean estaAtrasado() {
        if (this.status == StatusEmprestimo.FINALIZADO) {
            return false;
        }
        return LocalDate.now().isAfter(dataPrevistaDevolucao);
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) return true;
        if (outro == null || getClass() != outro.getClass()) return false;
        Emprestimo que = (Emprestimo) outro;
        return Objects.equals(codigo, que.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Empréstimo [" + codigo + "] - Livro: " + livro.getTitulo() 
                + " | Leitor: " + leitor.getNome() 
                + " | Data: " + dataEmprestimo 
                + " | Previsão: " + dataPrevistaDevolucao 
                + " | Status: " + status;
    }
}