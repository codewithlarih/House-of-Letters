package aplicacao;

import entidades.Pessoa;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import servicos.FuncionarioService;
import servicos.LeitorService;
import util.EntradaConsole;
import util.Tabela;

/**
 * Menu principal do sistema BookFlow. Direciona para os submenus e também mostra
 * a listagem de TODAS as pessoas juntas, que demonstra o POLIMORFISMO.
 * Todas as dependências chegam pelo construtor (injeção de dependência).
 */
public class MenuPrincipal {

    private final MenuLivro menuLivro;
    private final MenuLeitor menuLeitor;
    private final MenuFuncionario menuFuncionario;
    private final MenuEmprestimo menuEmprestimo;
    private final LeitorService leitorService;
    private final FuncionarioService funcionarioService;
    private final EntradaConsole entrada;

    public MenuPrincipal(MenuLivro menuLivro, MenuLeitor menuLeitor, MenuFuncionario menuFuncionario,
                         MenuEmprestimo menuEmprestimo, LeitorService leitorService,
                         FuncionarioService funcionarioService, EntradaConsole entrada) {
        this.menuLivro = menuLivro;
        this.menuLeitor = menuLeitor;
        this.menuFuncionario = menuFuncionario;
        this.menuEmprestimo = menuEmprestimo;
        this.leitorService = leitorService;
        this.funcionarioService = funcionarioService;
        this.entrada = entrada;
    }

    /** Laço principal: repete o menu até o usuário escolher 0 (sair). */
    public void executar() {
        int opcao;
        do {
            exibirOpcoes();
            opcao = entrada.lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1: menuLivro.executar(); break;
                case 2: menuLeitor.executar(); break;
                case 3: menuFuncionario.executar(); break;
                case 4: menuEmprestimo.executar(); break;
                case 5: listarTodasAsPessoas(); break;
                case 0: System.out.println("Encerrando o BookFlow. Até logo!"); break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    // Desenha o menu em uma "caixa" de largura fixa
    private void exibirOpcoes() {
        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println(linha("BOOKFLOW - SISTEMA DE BIBLIOTECA"));
        System.out.println("------------------------------------------");
        System.out.println(linha("1. Gerenciar livros"));
        System.out.println(linha("2. Gerenciar leitores"));
        System.out.println(linha("3. Gerenciar funcionários"));
        System.out.println(linha("4. Gerenciar empréstimos"));
        System.out.println(linha("5. Listar todas as pessoas"));
        System.out.println(linha("0. Sair"));
        System.out.println("------------------------------------------");
    }

    // Completa o texto com espaços para todas as linhas da caixa terem o mesmo tamanho
    private String linha(String texto) {
        return String.format("| %-38s |", texto);
    }

    /**
     * POLIMORFISMO: funcionários e leitores entram na MESMA lista de Pessoa.
     * Ao chamar getTipoPessoa() e exibirResumo(), o Java executa a versão
     * da subclasse real de cada objeto (Leitor ou Funcionario).
     */
    private void listarTodasAsPessoas() {
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.addAll(funcionarioService.listar());
        pessoas.addAll(leitorService.listar());
        Collections.sort(pessoas);   // usa Pessoa.compareTo (ordem alfabética por nome)

        String[] cabecalhos = {"Tipo", "Nome", "Matrícula", "Resumo"};
        List<String[]> linhas = new ArrayList<>();
        for (Pessoa pessoa : pessoas) {
            linhas.add(new String[]{
                pessoa.getTipoPessoa(), pessoa.getNome(), pessoa.getMatricula(), pessoa.exibirResumo()
            });
        }
        Tabela.imprimir(cabecalhos, linhas);
    }
}
