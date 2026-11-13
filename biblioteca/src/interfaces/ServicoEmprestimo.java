package interfaces;

import entidades.Emprestimo;
import java.time.LocalDate;
import java.util.List;

/**
 * INTERFACE (contrato) das operações de empréstimo.
 * Os menus dependem DESTA interface e não da classe concreta EmprestimoService:
 * se a regra de empréstimo mudar, o menu não precisa ser alterado (baixo acoplamento).
 */
public interface ServicoEmprestimo {

    /** Cria um empréstimo, validando leitor/livro/funcionário e baixando o estoque do livro. */
    Emprestimo registrarEmprestimo(String codigo, String matriculaLeitor, String idLivro,
                                   String matriculaFuncionario, LocalDate dataEmprestimo, int diasPrazo);

    /** ALTERAR: prorroga a data prevista de devolução de um empréstimo ativo. */
    void renovarEmprestimo(String codigo, int diasExtras);

    /** Marca o empréstimo como finalizado e devolve o exemplar ao estoque. */
    void registrarDevolucao(String codigo);

    /** EXCLUIR: remove o empréstimo (se ainda ativo, devolve o exemplar ao estoque). */
    void remover(String codigo);

    /** BUSCAR por código; devolve null se não achar. */
    Emprestimo buscarPorCodigo(String codigo);

    /** BUSCAR todos os empréstimos de um leitor, pela matrícula dele. */
    List<Emprestimo> buscarPorMatriculaLeitor(String matriculaLeitor);

    /** LISTAR todos, na ordem de cadastro. */
    List<Emprestimo> listar();

    /** LISTAR ordenado pela data em que o empréstimo foi feito. */
    List<Emprestimo> listarOrdenadosPorDataEmprestimo();

    /** LISTAR ordenado pela data prevista de devolução. */
    List<Emprestimo> listarOrdenadosPorDataPrevista();
}
