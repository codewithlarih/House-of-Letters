package entidades;

import java.time.LocalDate;
import util.FormatoData;
import util.Validacao;

/**
 * Funcionário da biblioteca: é uma Pessoa (HERANÇA, relação "é-um")
 * que além dos dados comuns possui cargo e data de admissão.
 */
public class Funcionario extends Pessoa {

    private String cargo;
    private LocalDate dataAdmissao;

    /** Chama super(...) para guardar os dados comuns e valida os dados próprios. */
    public Funcionario(String nome, String cpf, String telefone, String email,
                       String matricula, String cargo, LocalDate dataAdmissao) {
        super(nome, cpf, telefone, email, matricula);
        this.cargo = Validacao.textoObrigatorio(cargo, "cargo");
        this.dataAdmissao = validarDataAdmissao(dataAdmissao);
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = Validacao.textoObrigatorio(cargo, "cargo");
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = validarDataAdmissao(dataAdmissao);
    }

    // POLIMORFISMO: implementação específica do método abstrato de Pessoa
    @Override
    public String getTipoPessoa() {
        return "Funcionário";
    }

    // POLIMORFISMO: resumo específico de funcionário
    @Override
    public String exibirResumo() {
        return cargo + ", admitido em " + dataAdmissao.format(FormatoData.PADRAO);
    }

    // A data de admissão é obrigatória e não pode estar no futuro
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
