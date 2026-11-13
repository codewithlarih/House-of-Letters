package aplicacao;

import entidades.Livro;
import enumeracoes.GeneroLivro;
import java.util.ArrayList;
import java.util.List;
import servicos.LivroService;
import util.EntradaConsole;
import util.Tabela;

/**
 * Menu textual de livros: incluir, alterar, excluir, listar, buscar e listar ordenado.
 * Só cuida da conversa com o usuário; as regras ficam no LivroService (injetado pelo construtor).
 */
public class MenuLivro {

    private final LivroService livroService;
    private final EntradaConsole entrada;

    public MenuLivro(LivroService livroService, EntradaConsole entrada) {
        this.livroService = livroService;
        this.entrada = entrada;
    }

    /** Repete o menu até o usuário escolher 0 (voltar). Erros de regra são mostrados sem derrubar o programa. */
    public void executar() {
        int opcao;
        do {
            System.out.println();
            System.out.println("===== GERENCIAR LIVROS =====");
            System.out.println("1. Incluir livro");
            System.out.println("2. Alterar livro");
            System.out.println("3. Excluir livro");
            System.out.println("4. Listar livros");
            System.out.println("5. Buscar livro");
            System.out.println("6. Listar livros ordenados");
            System.out.println("0. Voltar");
            opcao = entrada.lerInteiro("Escolha uma opção: ");
            try {
                tratarOpcao(opcao);
            } catch (IllegalArgumentException | IllegalStateException erro) {
                System.out.println("Erro: " + erro.getMessage());
            }
        } while (opcao != 0);
    }

    // Decide qual operação executar conforme a opção digitada
    private void tratarOpcao(int opcao) {
        switch (opcao) {
            case 1: incluir(); break;
            case 2: alterar(); break;
            case 3: excluir(); break;
            case 4: mostrarTabela(livroService.listar()); break;
            case 5: buscar(); break;
            case 6: listarOrdenados(); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }

    // INCLUIR: lê os dados, cria o Livro (que valida tudo) e pede ao serviço para cadastrar
    private void incluir() {
        String id = entrada.lerTexto("Id: ");
        String titulo = entrada.lerTexto("Título: ");
        String autor = entrada.lerTexto("Autor: ");
        int ano = entrada.lerInteiro("Ano de publicação: ");
        GeneroLivro genero = lerGenero(null);
        int quantidade = entrada.lerInteiro("Quantidade de exemplares: ");
        livroService.cadastrar(new Livro(id, titulo, autor, ano, genero, quantidade));
        System.out.println("Livro cadastrado com sucesso!");
    }

    // ALTERAR: mostra os valores atuais; ENTER mantém o valor
    private void alterar() {
        Livro livro = livroService.buscarPorId(entrada.lerTexto("Id do livro a alterar: "));
        if (livro == null) {
            System.out.println("Livro não encontrado.");
            return;
        }
        System.out.println("(Pressione ENTER para manter o valor atual)");
        String titulo = entrada.lerTextoOuManter("Título", livro.getTitulo());
        String autor = entrada.lerTextoOuManter("Autor", livro.getAutor());
        int ano = entrada.lerInteiroOuManter("Ano", livro.getAnoPublicacao());
        GeneroLivro genero = lerGenero(livro.getGenero());
        int total = entrada.lerInteiroOuManter("Quantidade total", livro.getQuantidadeTotal());
        livroService.alterar(livro.getId(), titulo, autor, ano, genero, total);
        System.out.println("Livro alterado com sucesso!");
    }

    // EXCLUIR: pede confirmação antes de remover
    private void excluir() {
        String id = entrada.lerTexto("Id do livro a excluir: ");
        if (entrada.lerSimNao("Confirma a exclusão?")) {
            livroService.remover(id);
            System.out.println("Livro removido com sucesso!");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    // BUSCAR: o usuário escolhe por qual atributo pesquisar
    private void buscar() {
        System.out.println("Buscar por: 1. Id  2. Título  3. Autor");
        int criterio = entrada.lerInteiro("Escolha: ");
        List<Livro> resultado = new ArrayList<>();
        switch (criterio) {
            case 1:
                Livro livro = livroService.buscarPorId(entrada.lerTexto("Id: "));
                if (livro != null) {
                    resultado.add(livro);
                }
                break;
            case 2:
                resultado = livroService.buscarPorTitulo(entrada.lerTexto("Parte do título: "));
                break;
            case 3:
                resultado = livroService.buscarPorAutor(entrada.lerTexto("Parte do nome do autor: "));
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }
        mostrarTabela(resultado);
    }

    // LISTAR ORDENADO: o usuário escolhe o atributo da ordenação
    private void listarOrdenados() {
        System.out.println("Ordenar por: 1. Título  2. Autor  3. Ano");
        int criterio = entrada.lerInteiro("Escolha: ");
        switch (criterio) {
            case 1: mostrarTabela(livroService.listarOrdenadosPorTitulo()); break;
            case 2: mostrarTabela(livroService.listarOrdenadosPorAutor()); break;
            case 3: mostrarTabela(livroService.listarOrdenadosPorAno()); break;
            default: System.out.println("Opção inválida.");
        }
    }

    // Mostra os gêneros numerados e devolve o escolhido; ENTER mantém o atual (quando há atual)
    private GeneroLivro lerGenero(GeneroLivro atual) {
        GeneroLivro[] generos = GeneroLivro.values();
        System.out.println("Gêneros:");
        for (int i = 0; i < generos.length; i++) {
            System.out.println("  " + (i + 1) + ". " + generos[i].getDescricao());
        }
        while (true) {
            int escolha = (atual == null)
                    ? entrada.lerInteiro("Número do gênero: ")
                    : entrada.lerInteiroOuManter("Número do gênero", atual.ordinal() + 1);
            if (escolha >= 1 && escolha <= generos.length) {
                return generos[escolha - 1];
            }
            System.out.println("Número de gênero inválido.");
        }
    }

    // Converte a lista de livros em linhas de tabela e imprime
    private void mostrarTabela(List<Livro> livros) {
        String[] cabecalhos = {"ID", "Título", "Autor", "Ano", "Gênero", "Disp./Total"};
        List<String[]> linhas = new ArrayList<>();
        for (Livro livro : livros) {
            linhas.add(new String[]{
                livro.getId(), livro.getTitulo(), livro.getAutor(),
                String.valueOf(livro.getAnoPublicacao()), livro.getGenero().getDescricao(),
                livro.getQuantidadeDisponivel() + "/" + livro.getQuantidadeTotal()
            });
        }
        Tabela.imprimir(cabecalhos, linhas);
    }
}
