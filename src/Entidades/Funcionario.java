package Entidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Funcionario extends Pessoa {

    private static final DateTimeFormatter FORMATO_DATA = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String matricula;
    private String cargo;
    private LocalDate dataAdmissao;

    public Funcionario(String nome, String cpf, String telefone, String email,
                       String matricula, String cargo, LocalDate dataAdmissao) {
        super(nome, cpf, telefone, email); // o construtor de Pessoa guarda os dados comuns
        this.matricula = validarTextoObrigatorio(matricula, "matrícula"); // método herdado de Pessoa
        this.cargo = validarTextoObrigatorio(cargo, "cargo");
        this.dataAdmissao = validarDataAdmissao(dataAdmissao);
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = validarTextoObrigatorio(matricula, "matrícula");
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = validarTextoObrigatorio(cargo, "cargo");
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = validarDataAdmissao(dataAdmissao);
    }

    @Override
    public String getTipoPessoa() {
        return "Funcionário";
    }

    @Override
    public String exibirResumo() {
        return getNome() + " (matrícula " + matricula + ") - " 
        + cargo + ", admissão em " 
        + dataAdmissao.format(FORMATO_DATA);
    }

    @Override
    public boolean equals(Object outro) {

        if (this == outro) {
            return true;
        }
        
        if (outro == null || getClass() != outro.getClass()) {
            return false;
        }

        Funcionario outroFuncionario = (Funcionario) outro;

        return Objects.equals(matricula, outroFuncionario.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }

    private static LocalDate validarDataAdmissao(LocalDate data) {

        if (data == null) {
            throw new IllegalArgumentException("A data de admissão é obrigatória.");
        }
        
        if (data.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de admissão não pode ser futura.");
        }
        return data;
    }
}