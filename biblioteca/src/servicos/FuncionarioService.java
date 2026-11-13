package servicos;

import entidades.Funcionario;
import interfaces.Repositorio;
import java.util.Comparator;
import java.util.List;

/**
 * Serviço de funcionários. Herda de ServicoPessoa tudo que é comum
 * e acrescenta apenas o que é específico de Funcionario.
 */
public class FuncionarioService extends ServicoPessoa<Funcionario> {

    /** Recebe o repositório pronto (injeção de dependência) e repassa para a superclasse. */
    public FuncionarioService(Repositorio<Funcionario> repositorio) {
        super(repositorio);
    }

    @Override
    protected String nomeDoTipo() {
        return "funcionário";
    }

    /** ALTERAR: atualiza nome, telefone, e-mail e cargo do funcionário. */
    public void alterar(String matricula, String novoNome, String novoTelefone,
                        String novoEmail, String novoCargo) {
        Funcionario funcionario = exigirPorMatricula(matricula);
        funcionario.setNome(novoNome);
        funcionario.setTelefone(novoTelefone);
        funcionario.setEmail(novoEmail);
        funcionario.setCargo(novoCargo);
    }

    /** LISTAR ORDENADO pelo cargo (ordem alfabética). */
    public List<Funcionario> listarOrdenadosPorCargo() {
        List<Funcionario> funcionarios = listar();
        funcionarios.sort(Comparator.comparing(Funcionario::getCargo, String.CASE_INSENSITIVE_ORDER));
        return funcionarios;
    }

    /** LISTAR ORDENADO pela data de admissão (mais antigos primeiro). */
    public List<Funcionario> listarOrdenadosPorDataAdmissao() {
        List<Funcionario> funcionarios = listar();
        funcionarios.sort(Comparator.comparing(Funcionario::getDataAdmissao));
        return funcionarios;
    }
}
