package aplicacao;

import entidades.Emprestimo;
import interfaces.ServicoEmprestimo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import util.EntradaConsole;
import util.FormatoData;
import util.Tabela;

/**
 * Menu textual de empréstimos. Repare que ele depende da INTERFACE ServicoEmprestimo,
 * e não da classe concreta: quem decide qual implementação usar é o Programa (injeção).
 */
public class MenuEmprestimo {

    private final ServicoEmprestimo emprestimoService;
    private final EntradaConsole entrada;

    public MenuEmprestimo(ServicoEmprestimo emprestimoService, EntradaConsole entrada) {
        this.emprestimoService = emprestimoService;
        this.entrada = entrada;
    }

    /** Repete o menu até o usuário escolher 0 (voltar). */
    public void executar() {
        int opcao;
        do {
            System.out.println();
            System.out.println("===== GERENCIAR EMPRÉSTIMOS =====");
            System.out.println("1. Incluir empréstimo");
            System.out.println("2. Alterar empréstimo (renovar prazo)");
            System.out.println("3. Registrar devolução");
            System.out.println("4. Excluir empréstimo");
            System.out.println("5. Listar empréstimos");
            System.out.println("6. Buscar empréstimo");
            System.out.println("7. Listar empréstimos ordenados");
            System.out.println("0. Voltar");
            opcao = entrada.lerInteiro("Escolha uma opção: ");
            try {
                tratarOpcao(opcao);
            } catch (IllegalArgumentException | IllegalStateException erro) {
                System.out.println("Erro: " + erro.getMessage());
            }
        } while (opcao != 0);
    }

    private void tratarOpcao(int opcao) {
        switch (opcao) {
            case 1: incluir(); break;
            case 2: renovar(); break;
            case 3: devolver(); break;
            case 4: excluir(); break;
            case 5: mostrarTabela(emprestimoService.listar()); break;
            case 6: buscar(); break;
            case 7: listarOrdenados(); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }

    // INCLUIR: a data do empréstimo é hoje; o serviço valida leitor, livro, funcionário e estoque
    private void incluir() {
        String codigo = entrada.lerTexto("Código do empréstimo: ");
        String matriculaLeitor = entrada.lerTexto("Matrícula do leitor: ");
        String idLivro = entrada.lerTexto("Id do livro: ");
        String matriculaFuncionario = entrada.lerTexto("Matrícula do funcionário: ");
        int dias = entrada.lerInteiro("Prazo de devolução (dias): ");
        Emprestimo emprestimo = emprestimoService.registrarEmprestimo(
                codigo, matriculaLeitor, idLivro, matriculaFuncionario, LocalDate.now(), dias);
        System.out.println("Empréstimo registrado! Devolver até "
                + emprestimo.getDataPrevistaDevolucao().format(FormatoData.PADRAO));
    }

    // ALTERAR: prorroga o prazo de um empréstimo ativo
    private void renovar() {
        String codigo = entrada.lerTexto("Código do empréstimo: ");
        int dias = entrada.lerInteiro("Quantos dias a mais: ");
        emprestimoService.renovarEmprestimo(codigo, dias);
        System.out.println("Prazo renovado com sucesso!");
    }

    // Marca como devolvido e devolve o exemplar ao estoque
    private void devolver() {
        emprestimoService.registrarDevolucao(entrada.lerTexto("Código do empréstimo: "));
        System.out.println("Devolução registrada com sucesso!");
    }

    // EXCLUIR: pede confirmação
    private void excluir() {
        String codigo = entrada.lerTexto("Código do empréstimo a excluir: ");
        if (entrada.lerSimNao("Confirma a exclusão?")) {
            emprestimoService.remover(codigo);
            System.out.println("Empréstimo removido com sucesso!");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    // BUSCAR: por código ou por matrícula do leitor
    private void buscar() {
        System.out.println("Buscar por: 1. Código  2. Matrícula do leitor");
        int criterio = entrada.lerInteiro("Escolha: ");
        List<Emprestimo> resultado = new ArrayList<>();
        switch (criterio) {
            case 1:
                Emprestimo emprestimo = emprestimoService.buscarPorCodigo(entrada.lerTexto("Código: "));
                if (emprestimo != null) {
                    resultado.add(emprestimo);
                }
                break;
            case 2:
                resultado = emprestimoService.buscarPorMatriculaLeitor(entrada.lerTexto("Matrícula do leitor: "));
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }
        mostrarTabela(resultado);
    }

    // LISTAR ORDENADO: por data do empréstimo ou data prevista de devolução
    private void listarOrdenados() {
        System.out.println("Ordenar por: 1. Data do empréstimo  2. Data prevista de devolução");
        int criterio = entrada.lerInteiro("Escolha: ");
        switch (criterio) {
            case 1: mostrarTabela(emprestimoService.listarOrdenadosPorDataEmprestimo()); break;
            case 2: mostrarTabela(emprestimoService.listarOrdenadosPorDataPrevista()); break;
            default: System.out.println("Opção inválida.");
        }
    }

    private void mostrarTabela(List<Emprestimo> emprestimos) {
        String[] cabecalhos = {"Código", "Livro", "Leitor", "Funcionário", "Empréstimo",
                               "Previsão", "Devolução", "Situação"};
        List<String[]> linhas = new ArrayList<>();
        for (Emprestimo emprestimo : emprestimos) {
            String devolucao = emprestimo.getDataDevolucao() == null
                    ? "-" : emprestimo.getDataDevolucao().format(FormatoData.PADRAO);
            linhas.add(new String[]{
                emprestimo.getCodigo(), emprestimo.getLivro().getTitulo(),
                emprestimo.getLeitor().getNome(), emprestimo.getFuncionario().getNome(),
                emprestimo.getDataEmprestimo().format(FormatoData.PADRAO),
                emprestimo.getDataPrevistaDevolucao().format(FormatoData.PADRAO),
                devolucao, emprestimo.getSituacao()
            });
        }
        Tabela.imprimir(cabecalhos, linhas);
    }
}
