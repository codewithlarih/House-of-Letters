package aplicacao;

import entidades.Funcionario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import servicos.FuncionarioService;
import util.EntradaConsole;
import util.FormatoData;
import util.Tabela;

/**
 * Menu textual de funcionários: incluir, alterar, excluir, listar, buscar e listar ordenado.
 * Só conversa com o usuário; as regras ficam no FuncionarioService (injetado pelo construtor).
 */
public class MenuFuncionario {

    private final FuncionarioService funcionarioService;
    private final EntradaConsole entrada;

    public MenuFuncionario(FuncionarioService funcionarioService, EntradaConsole entrada) {
        this.funcionarioService = funcionarioService;
        this.entrada = entrada;
    }

    /** Repete o menu até o usuário escolher 0 (voltar). */
    public void executar() {
        int opcao;
        do {
            System.out.println();
            System.out.println("===== GERENCIAR FUNCIONÁRIOS =====");
            System.out.println("1. Incluir funcionário");
            System.out.println("2. Alterar funcionário");
            System.out.println("3. Excluir funcionário");
            System.out.println("4. Listar funcionários");
            System.out.println("5. Buscar funcionário");
            System.out.println("6. Listar funcionários ordenados");
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
            case 4: mostrarTabela(funcionarioService.listar()); break;
            case 5: buscar(); break;
            case 6: listarOrdenados(); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }

    // INCLUIR: lê os dados e cria o Funcionario (que valida tudo)
    private void incluir() {
        String nome = entrada.lerTexto("Nome: ");
        String cpf = entrada.lerTexto("CPF (11 dígitos): ");
        String telefone = entrada.lerTexto("Telefone: ");
        String email = entrada.lerTexto("E-mail: ");
        String matricula = entrada.lerTexto("Matrícula: ");
        String cargo = entrada.lerTexto("Cargo: ");
        LocalDate dataAdmissao = entrada.lerData("Data de admissão");
        funcionarioService.cadastrar(
                new Funcionario(nome, cpf, telefone, email, matricula, cargo, dataAdmissao));
        System.out.println("Funcionário cadastrado com sucesso!");
    }

    // ALTERAR: ENTER mantém o valor atual
    private void alterar() {
        Funcionario funcionario = funcionarioService.buscarPorMatricula(
                entrada.lerTexto("Matrícula do funcionário a alterar: "));
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }
        System.out.println("(Pressione ENTER para manter o valor atual)");
        String nome = entrada.lerTextoOuManter("Nome", funcionario.getNome());
        String telefone = entrada.lerTextoOuManter("Telefone", funcionario.getTelefone());
        String email = entrada.lerTextoOuManter("E-mail", funcionario.getEmail());
        String cargo = entrada.lerTextoOuManter("Cargo", funcionario.getCargo());
        funcionarioService.alterar(funcionario.getMatricula(), nome, telefone, email, cargo);
        System.out.println("Funcionário alterado com sucesso!");
    }

    // EXCLUIR: pede confirmação
    private void excluir() {
        String matricula = entrada.lerTexto("Matrícula do funcionário a excluir: ");
        if (entrada.lerSimNao("Confirma a exclusão?")) {
            funcionarioService.remover(matricula);
            System.out.println("Funcionário removido com sucesso!");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    // BUSCAR: por matrícula, CPF ou parte do nome
    private void buscar() {
        System.out.println("Buscar por: 1. Matrícula  2. CPF  3. Nome");
        int criterio = entrada.lerInteiro("Escolha: ");
        List<Funcionario> resultado = new ArrayList<>();
        switch (criterio) {
            case 1:
                Funcionario porMatricula = funcionarioService.buscarPorMatricula(entrada.lerTexto("Matrícula: "));
                if (porMatricula != null) {
                    resultado.add(porMatricula);
                }
                break;
            case 2:
                Funcionario porCpf = funcionarioService.buscarPorCpf(entrada.lerTexto("CPF: "));
                if (porCpf != null) {
                    resultado.add(porCpf);
                }
                break;
            case 3:
                resultado = funcionarioService.buscarPorNome(entrada.lerTexto("Parte do nome: "));
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }
        mostrarTabela(resultado);
    }

    // LISTAR ORDENADO: por nome, cargo ou data de admissão
    private void listarOrdenados() {
        System.out.println("Ordenar por: 1. Nome  2. Cargo  3. Data de admissão");
        int criterio = entrada.lerInteiro("Escolha: ");
        switch (criterio) {
            case 1: mostrarTabela(funcionarioService.listarOrdenadosPorNome()); break;
            case 2: mostrarTabela(funcionarioService.listarOrdenadosPorCargo()); break;
            case 3: mostrarTabela(funcionarioService.listarOrdenadosPorDataAdmissao()); break;
            default: System.out.println("Opção inválida.");
        }
    }

    private void mostrarTabela(List<Funcionario> funcionarios) {
        String[] cabecalhos = {"Matrícula", "Nome", "CPF", "Telefone", "E-mail", "Cargo", "Admissão"};
        List<String[]> linhas = new ArrayList<>();
        for (Funcionario funcionario : funcionarios) {
            linhas.add(new String[]{
                funcionario.getMatricula(), funcionario.getNome(), funcionario.getCpf(),
                funcionario.getTelefone(), funcionario.getEmail(), funcionario.getCargo(),
                funcionario.getDataAdmissao().format(FormatoData.PADRAO)
            });
        }
        Tabela.imprimir(cabecalhos, linhas);
    }
}
