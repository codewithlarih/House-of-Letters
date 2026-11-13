package servicos;

import entidades.Leitor;
import interfaces.Repositorio;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Serviço de leitores. Herda de ServicoPessoa tudo que é comum (cadastrar, buscar, listar...)
 * e acrescenta apenas o que é específico de Leitor.
 */
public class LeitorService extends ServicoPessoa<Leitor> {

    /** Recebe o repositório pronto (injeção de dependência) e repassa para a superclasse. */
    public LeitorService(Repositorio<Leitor> repositorio) {
        super(repositorio);
    }

    @Override
    protected String nomeDoTipo() {
        return "leitor";
    }

    /** ALTERAR: atualiza nome, telefone, e-mail e situação (ativo/inativo) do leitor. */
    public void alterar(String matricula, String novoNome, String novoTelefone,
                        String novoEmail, boolean novoAtivo) {
        Leitor leitor = exigirPorMatricula(matricula);
        leitor.setNome(novoNome);
        leitor.setTelefone(novoTelefone);
        leitor.setEmail(novoEmail);
        leitor.setAtivo(novoAtivo);
    }

    /** LISTAR ORDENADO pela data de cadastro (mais antigos primeiro). */
    public List<Leitor> listarOrdenadosPorDataCadastro() {
        List<Leitor> leitores = new ArrayList<>(listar());
        leitores.sort(Comparator.comparing(Leitor::getDataCadastro));
        return leitores;
    }
}
