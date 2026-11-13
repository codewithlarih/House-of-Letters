package aplicacao;

import entidades.Leitor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import servicos.LeitorService;
import util.EntradaConsole;
import util.FormatoData;
import util.Tabela;

/**
 * Menu textual de leitores: incluir, alterar, excluir, listar, buscar e listar ordenado.
 * Só conversa com o usuário; as regras ficam no LeitorService (injetado pelo construtor).
 */
public class MenuLeitor {

    private final LeitorService leitorService;
    private final EntradaConsole entrada;

    public MenuLeitor(LeitorService leitorService, EntradaConsole entrada) {
        this.leitorService = leitorService;
        this.entrada = entrada;
    }

    /** Repete o menu até o usuário escolher 0 (voltar). */
    public void executar() {
        int opcao;
        do {
            System.out.println();
            System.out.println("===== GERENCIAR LEITORES =====");
            System.out.println("1. Incluir leitor");
            System.out.println("2. Alterar leitor");
            System.out.println("3. Excluir leitor");
            System.out.println("4. Listar leitores");
            System.out.println("5. Buscar leitor");
            System.out.println("6. Listar leitores ordenados");
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
            case 2: alterar(); break;
            case 3: excluir(); break;
            case 4: mostrarTabela(leitorService.listar()); break;
            case 5: buscar(); break;
            case 6: listarOrdenados(); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }

    // INCLUIR: o leitor é cadastrado com a data de hoje e como ativo
    private void incluir() {
        String nome = entrada.lerTexto("Nome: ");
        String cpf = entrada.lerTexto("CPF (11 dígitos): ");
        String telefone = entrada.lerTexto("Telefone: ");
        String email = entrada.lerTexto("E-mail: ");
        String matricula = entrada.lerTexto("Matrícula: ");
        leitorService.cadastrar(new Leitor(nome, cpf, telefone, email, matricula, LocalDate.now(), true));
        System.out.println("Leitor cadastrado com sucesso!");
    }

    // ALTERAR: ENTER mantém o valor atual
    private void alterar() {
        Leitor leitor = leitorService.buscarPorMatricula(entrada.lerTexto("Matrícula do leitor a alterar: "));
        if (leitor == null) {
            System.out.println("Leitor não encontrado.");
            return;
        }
        System.out.println("(Pressione ENTER para manter o valor atual)");
        String nome = entrada.lerTextoOuManter("Nome", leitor.getNome());
        String telefone = entrada.lerTextoOuManter("Telefone", leitor.getTelefone());
        String email = entrada.lerTextoOuManter("E-mail", leitor.getEmail());
        boolean ativo = entrada.lerSimNaoOuManter("Leitor ativo?", leitor.isAtivo());
        leitorService.alterar(leitor.getMatricula(), nome, telefone, email, ativo);
        System.out.println("Leitor alterado com sucesso!");
    }

    // EXCLUIR: pede confirmação
    private void excluir() {
        String matricula = entrada.lerTexto("Matrícula do leitor a excluir: ");
        if (entrada.lerSimNao("Confirma a exclusão?")) {
            leitorService.remover(matricula);
            System.out.println("Leitor removido com sucesso!");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    // BUSCAR: por matrícula, CPF ou parte do nome
    private void buscar() {
        System.out.println("Buscar por: 1. Matrícula  2. CPF  3. Nome");
        int criterio = entrada.lerInteiro("Escolha: ");
        List<Leitor> resultado = new ArrayList<>();
        switch (criterio) {
            case 1:
                Leitor porMatricula = leitorService.buscarPorMatricula(entrada.lerTexto("Matrícula: "));
                if (porMatricula != null) {
                    resultado.add(porMatricula);
                }
                break;
            case 2:
                Leitor porCpf = leitorService.buscarPorCpf(entrada.lerTexto("CPF: "));
                if (porCpf != null) {
                    resultado.add(porCpf);
                }
                break;
            case 3:
                resultado = leitorService.buscarPorNome(entrada.lerTexto("Parte do nome: "));
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }
        mostrarTabela(resultado);
    }

    // LISTAR ORDENADO: por nome ou data de cadastro
    private void listarOrdenados() {
        System.out.println("Ordenar por: 1. Nome  2. Data de cadastro");
        int criterio = entrada.lerInteiro("Escolha: ");
        switch (criterio) {
            case 1: mostrarTabela(leitorService.listarOrdenadosPorNome()); break;
            case 2: mostrarTabela(leitorService.listarOrdenadosPorDataCadastro()); break;
            default: System.out.println("Opção inválida.");
        }
    }

    private void mostrarTabela(List<Leitor> leitores) {
        String[] cabecalhos = {"Matrícula", "Nome", "CPF", "Telefone", "E-mail", "Cadastro", "Ativo"};
        List<String[]> linhas = new ArrayList<>();
        for (Leitor leitor : leitores) {
            linhas.add(new String[]{
                leitor.getMatricula(), leitor.getNome(), leitor.getCpf(), leitor.getTelefone(),
                leitor.getEmail(), leitor.getDataCadastro().format(FormatoData.PADRAO),
                leitor.isAtivo() ? "Sim" : "Não"
            });
        }
        Tabela.imprimir(cabecalhos, linhas);
    }
}
