package Entidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Leitor extends Pessoa {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    //Atributos
    private String matricula;
    private LocalDate dataCadastro;
    private boolean ativo;

    // Construtor
    public Leitor(String nome, String cpf, String telefone, String email, 
                  String matricula, LocalDate dataCadastro, boolean ativo) {
        super(nome, cpf, telefone, email);
        this.matricula = validarTextoObrigatorio(matricula, "matrícula");
        this.dataCadastro = dataCadastro != null ? dataCadastro : LocalDate.now();
        this.ativo = ativo;
    }

    // Getters e Setters

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = validarTextoObrigatorio(matricula, "matrícula");
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String getTipoPessoa() {
        return "Leitor";
    }

    @Override
    public String exibirResumo() {
        return getNome() + " (matrícula " + matricula + ") - Cadastrado em " 
                + dataCadastro.format(FORMATO_DATA) + " | Ativo: " + (ativo ? "Sim" : "Não");
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) return true;
        if (outro == null || getClass() != outro.getClass()) return false;
        Leitor outroLeitor = (Leitor) outro;
        return Objects.equals(matricula, outroLeitor.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }
}