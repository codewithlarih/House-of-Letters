package aplicacao;

import entidades.Funcionario;
import entidades.Leitor;
import entidades.Livro;
import enumeracoes.GeneroLivro;
import interfaces.ServicoEmprestimo;
import java.time.LocalDate;
import servicos.FuncionarioService;
import servicos.LeitorService;
import servicos.LivroService;

/**
 * Requisito 8: cadastra automaticamente 7 objetos de cada classe ao iniciar o programa,
 * para que os testes possam ser feitos sem digitar tudo. Usa os próprios serviços,
 * então os dados passam pelas mesmas validações do uso normal.
 */
public final class DadosIniciais {

    private DadosIniciais() {
    }

    /** Cadastra, nesta ordem: livros, funcionários, leitores e empréstimos (que dependem dos anteriores). */
    public static void carregar(LivroService livroService, FuncionarioService funcionarioService,
                                LeitorService leitorService, ServicoEmprestimo emprestimoService) {
        LocalDate hoje = LocalDate.now();

        // ----- 7 livros -----
        livroService.cadastrar(new Livro("L001", "Dom Casmurro", "Machado de Assis", 1899, GeneroLivro.ROMANCE, 3));
        livroService.cadastrar(new Livro("L002", "O Hobbit", "J.R.R. Tolkien", 1937, GeneroLivro.FANTASIA, 2));
        livroService.cadastrar(new Livro("L003", "Clean Code", "Robert C. Martin", 2008, GeneroLivro.TECNOLOGIA, 4));
        livroService.cadastrar(new Livro("L004", "Sapiens", "Yuval Noah Harari", 2011, GeneroLivro.HISTORIA, 3));
        livroService.cadastrar(new Livro("L005", "O Pequeno Príncipe", "Antoine de Saint-Exupéry", 1943, GeneroLivro.INFANTIL, 5));
        livroService.cadastrar(new Livro("L006", "Cosmos", "Carl Sagan", 1980, GeneroLivro.CIENCIAS, 2));
        livroService.cadastrar(new Livro("L007", "Steve Jobs", "Walter Isaacson", 2011, GeneroLivro.BIOGRAFIA, 2));

        // ----- 7 funcionários -----
        funcionarioService.cadastrar(new Funcionario("Carla Mendes", "111.111.111-11", "(71) 99999-0001", "carla@bookflow.com", "FUN001", "Gerente", hoje.minusYears(6)));
        funcionarioService.cadastrar(new Funcionario("Bruno Lima", "222.222.222-22", "(71) 99999-0002", "bruno@bookflow.com", "FUN002", "Bibliotecário", hoje.minusYears(4)));
        funcionarioService.cadastrar(new Funcionario("Daniela Souza", "333.333.333-33", "(71) 99999-0003", "daniela@bookflow.com", "FUN003", "Atendente", hoje.minusYears(3)));
        funcionarioService.cadastrar(new Funcionario("Eduardo Reis", "444.444.444-44", "(71) 99999-0004", "eduardo@bookflow.com", "FUN004", "Atendente", hoje.minusYears(2)));
        funcionarioService.cadastrar(new Funcionario("Fernanda Alves", "555.555.555-55", "(71) 99999-0005", "fernanda@bookflow.com", "FUN005", "Auxiliar", hoje.minusYears(1)));
        funcionarioService.cadastrar(new Funcionario("Gustavo Pires", "666.666.666-66", "(71) 99999-0006", "gustavo@bookflow.com", "FUN006", "Estagiário", hoje.minusMonths(8)));
        funcionarioService.cadastrar(new Funcionario("Helena Costa", "777.777.777-77", "(71) 99999-0007", "helena@bookflow.com", "FUN007", "Estagiária", hoje.minusMonths(3)));

        // ----- 7 leitores (o último está inativo, para testar a regra de empréstimo) -----
        leitorService.cadastrar(new Leitor("Ana Paula", "100.000.000-01", "(71) 98888-0001", "ana@email.com", "LEI001", hoje.minusYears(3), true));
        leitorService.cadastrar(new Leitor("Bruna Dias", "100.000.000-02", "(71) 98888-0002", "bruna@email.com", "LEI002", hoje.minusYears(2), true));
        leitorService.cadastrar(new Leitor("Caio Santos", "100.000.000-03", "(71) 98888-0003", "caio@email.com", "LEI003", hoje.minusMonths(20), true));
        leitorService.cadastrar(new Leitor("Diego Rocha", "100.000.000-04", "(71) 98888-0004", "diego@email.com", "LEI004", hoje.minusMonths(14), true));
        leitorService.cadastrar(new Leitor("Elisa Nunes", "100.000.000-05", "(71) 98888-0005", "elisa@email.com", "LEI005", hoje.minusMonths(9), true));
        leitorService.cadastrar(new Leitor("Felipe Moura", "100.000.000-06", "(71) 98888-0006", "felipe@email.com", "LEI006", hoje.minusMonths(5), true));
        leitorService.cadastrar(new Leitor("Giovana Prado", "100.000.000-07", "(71) 98888-0007", "giovana@email.com", "LEI007", hoje.minusMonths(1), false));

        // ----- 7 empréstimos (alguns atrasados, um já devolvido) -----
        emprestimoService.registrarEmprestimo("E001", "LEI001", "L001", "FUN001", hoje.minusDays(20), 14); // atrasado
        emprestimoService.registrarEmprestimo("E002", "LEI002", "L002", "FUN002", hoje.minusDays(5), 14);
        emprestimoService.registrarEmprestimo("E003", "LEI003", "L003", "FUN001", hoje.minusDays(30), 14); // será devolvido abaixo
        emprestimoService.registrarEmprestimo("E004", "LEI004", "L004", "FUN003", hoje.minusDays(2), 7);
        emprestimoService.registrarEmprestimo("E005", "LEI005", "L005", "FUN002", hoje.minusDays(12), 10); // atrasado
        emprestimoService.registrarEmprestimo("E006", "LEI006", "L002", "FUN004", hoje.minusDays(1), 14);
        emprestimoService.registrarEmprestimo("E007", "LEI001", "L007", "FUN005", hoje, 7);

        // Finaliza um empréstimo para existir pelo menos um com situação FINALIZADO
        emprestimoService.registrarDevolucao("E003");
    }
}
