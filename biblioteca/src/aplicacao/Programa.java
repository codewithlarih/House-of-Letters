package aplicacao;

import entidades.Emprestimo;
import entidades.Funcionario;
import entidades.Leitor;
import entidades.Livro;
import interfaces.Repositorio;
import interfaces.ServicoEmprestimo;
import java.util.Scanner;
import repositorios.RepositorioMemoria;
import servicos.EmprestimoService;
import servicos.FuncionarioService;
import servicos.LeitorService;
import servicos.LivroService;
import util.EntradaConsole;

/**
 * Classe principal do sistema House of Letters (biblioteca) - requisito 9.
 *
 * O que o programa faz: gerencia livros, leitores, funcionários e empréstimos de uma
 * biblioteca por meio de menus no console (incluir, alterar, excluir, listar, buscar e ordenar).
 *
 * Esta classe é a "raiz de composição" da INVERSÃO DE CONTROLE: é AQUI, e só aqui, que
 * os objetos são criados com "new" e ligados uns aos outros (INJEÇÃO DE DEPENDÊNCIA pelos construtores).
 * As demais classes apenas recebem o que precisam e não criam suas próprias dependências.
 */
public class Programa {

    public static void main(String[] args) {
        // Um único Scanner para todo o programa (lê o teclado)
        Scanner scanner = new Scanner(System.in);
        EntradaConsole entrada = new EntradaConsole(scanner);

        // 1 Repositórios: guardam os objetos em memória. Declarados pelo tipo da INTERFACE.
        Repositorio<Livro> repositorioLivros = new RepositorioMemoria<>();
        Repositorio<Leitor> repositorioLeitores = new RepositorioMemoria<>();
        Repositorio<Funcionario> repositorioFuncionarios = new RepositorioMemoria<>();
        Repositorio<Emprestimo> repositorioEmprestimos = new RepositorioMemoria<>();

        // 2 Serviços: recebem o repositório (e outros serviços) pelo construtor
        LivroService livroService = new LivroService(repositorioLivros);
        LeitorService leitorService = new LeitorService(repositorioLeitores);
        FuncionarioService funcionarioService = new FuncionarioService(repositorioFuncionarios);
        ServicoEmprestimo emprestimoService = new EmprestimoService(
                repositorioEmprestimos, livroService, leitorService, funcionarioService);

        // 3 Carrega os 7 objetos de cada classe para facilitar os testes (requisito 8)
        DadosIniciais.carregar(livroService, funcionarioService, leitorService, emprestimoService);

        // 4 Menus: recebem os serviços pelo construtor
        MenuPrincipal menuPrincipal = new MenuPrincipal(
                new MenuLivro(livroService, entrada),
                new MenuLeitor(leitorService, entrada),
                new MenuFuncionario(funcionarioService, entrada),
                new MenuEmprestimo(emprestimoService, entrada),
                leitorService, funcionarioService, entrada);

        // 5 Inicia a interação com o usuário; só volta quando ele escolher "Sair"
        menuPrincipal.executar();

        // 6 Libera o recurso do teclado
        scanner.close();
    }
}
