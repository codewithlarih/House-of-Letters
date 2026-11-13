package servicos;

import entidades.Emprestimo;
import entidades.Funcionario;
import entidades.Leitor;
import entidades.Livro;
import enumeracoes.StatusEmprestimo;
import interfaces.Repositorio;
import interfaces.ServicoEmprestimo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementação da interface ServicoEmprestimo (cumpre o "contrato").
 * Recebe TODAS as suas dependências pelo construtor (INJEÇÃO DE DEPENDÊNCIA):
 * o repositório de empréstimos e os serviços de livro, leitor e funcionário.
 */
public class EmprestimoService implements ServicoEmprestimo {

    private final Repositorio<Emprestimo> repositorio;
    private final LivroService livroService;
    private final LeitorService leitorService;
    private final FuncionarioService funcionarioService;

    public EmprestimoService(Repositorio<Emprestimo> repositorio, LivroService livroService,
                             LeitorService leitorService, FuncionarioService funcionarioService) {
        this.repositorio = repositorio;
        this.livroService = livroService;
        this.leitorService = leitorService;
        this.funcionarioService = funcionarioService;
    }

    @Override
    public Emprestimo registrarEmprestimo(String codigo, String matriculaLeitor, String idLivro,
                                          String matriculaFuncionario, LocalDate dataEmprestimo, int diasPrazo) {
        // 1) Regras de existência: código novo, leitor, livro e funcionário precisam existir
        if (buscarPorCodigo(codigo) != null) {
            throw new IllegalArgumentException("Já existe um empréstimo com o código " + codigo + ".");
        }
        Leitor leitor = leitorService.buscarPorMatricula(matriculaLeitor);
        if (leitor == null) {
            throw new IllegalArgumentException("Leitor não encontrado.");
        }
        if (!leitor.isAtivo()) {
            throw new IllegalStateException("O leitor está inativo e não pode pegar livros emprestados.");
        }
        Livro livro = livroService.buscarPorId(idLivro);
        if (livro == null) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
        Funcionario funcionario = funcionarioService.buscarPorMatricula(matriculaFuncionario);
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não encontrado.");
        }

        // 2) Cria o empréstimo (valida datas e prazo) ANTES de mexer no estoque,
        //    assim, se algo for inválido, o estoque do livro não é alterado
        Emprestimo emprestimo = new Emprestimo(codigo, leitor, livro, funcionario, dataEmprestimo, diasPrazo);

        // 3) Baixa o estoque; se não houver exemplar disponível, cancela
        if (!livro.emprestarExemplar()) {
            throw new IllegalStateException("Não há exemplares disponíveis de \"" + livro.getTitulo() + "\".");
        }

        // 4) Guarda o empréstimo
        repositorio.adicionar(emprestimo);
        return emprestimo;
    }

    @Override
    public void renovarEmprestimo(String codigo, int diasExtras) {
        exigirPorCodigo(codigo).renovar(diasExtras);
    }

    @Override
    public void registrarDevolucao(String codigo) {
        Emprestimo emprestimo = exigirPorCodigo(codigo);
        emprestimo.registrarDevolucao(LocalDate.now());   // lança erro se já estiver finalizado
        emprestimo.getLivro().devolverExemplar();          // devolve o exemplar ao estoque
    }

    @Override
    public void remover(String codigo) {
        Emprestimo emprestimo = exigirPorCodigo(codigo);
        // Se o livro ainda estava emprestado, o exemplar volta para a estante ao excluir
        if (emprestimo.getStatus() == StatusEmprestimo.ATIVO) {
            emprestimo.getLivro().devolverExemplar();
        }
        repositorio.remover(emprestimo);
    }

    @Override
    public Emprestimo buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }
        for (Emprestimo emprestimo : repositorio.listarTodos()) {
            if (emprestimo.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return emprestimo;
            }
        }
        return null;
    }

    @Override
    public List<Emprestimo> buscarPorMatriculaLeitor(String matriculaLeitor) {
        List<Emprestimo> encontrados = new ArrayList<>();
        if (matriculaLeitor == null || matriculaLeitor.trim().isEmpty()) {
            return encontrados;
        }
        for (Emprestimo emprestimo : repositorio.listarTodos()) {
            if (emprestimo.getLeitor().getMatricula().equalsIgnoreCase(matriculaLeitor.trim())) {
                encontrados.add(emprestimo);
            }
        }
        return encontrados;
    }

    @Override
    public List<Emprestimo> listar() {
        return new ArrayList<>(repositorio.listarTodos());
    }

    @Override
    public List<Emprestimo> listarOrdenadosPorDataEmprestimo() {
        List<Emprestimo> emprestimos = listar();
        emprestimos.sort(Comparator.comparing(Emprestimo::getDataEmprestimo));
        return emprestimos;
    }

    @Override
    public List<Emprestimo> listarOrdenadosPorDataPrevista() {
        List<Emprestimo> emprestimos = listar();
        emprestimos.sort(Comparator.comparing(Emprestimo::getDataPrevistaDevolucao));
        return emprestimos;
    }

    // Busca por código e lança erro se não encontrar (evita repetir o "if null")
    private Emprestimo exigirPorCodigo(String codigo) {
        Emprestimo emprestimo = buscarPorCodigo(codigo);
        if (emprestimo == null) {
            throw new IllegalArgumentException("Nenhum empréstimo encontrado com o código informado.");
        }
        return emprestimo;
    }
}
