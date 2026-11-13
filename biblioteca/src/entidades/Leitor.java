package entidades;

import java.time.LocalDate;
import util.FormatoData;

/**
 * Leitor (usuário que pega livros emprestados): é uma Pessoa (HERANÇA)
 * com data de cadastro e indicação de ativo/inativo.
 */
public class Leitor extends Pessoa {

    private LocalDate dataCadastro;
    private boolean ativo;

    /** Se a data de cadastro vier nula, assume a data de hoje. */
    public Leitor(String nome, String cpf, String telefone, String email,
                  String matricula, LocalDate dataCadastro, boolean ativo) {
        super(nome, cpf, telefone, email, matricula);
        this.dataCadastro = dataCadastro != null ? dataCadastro : LocalDate.now();
        this.ativo = ativo;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    // POLIMORFISMO: implementação específica do método abstrato de Pessoa
    @Override
    public String getTipoPessoa() {
        return "Leitor";
    }

    // POLIMORFISMO: resumo específico de leitor
    @Override
    public String exibirResumo() {
        return "Cadastrado em " + dataCadastro.format(FormatoData.PADRAO)
                + " | Ativo: " + (ativo ? "Sim" : "Não");
    }
}
