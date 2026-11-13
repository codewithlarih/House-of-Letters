package servicos;

import entidades.Livro;
import enumeracoes.GeneroLivro;
import interfaces.Repositorio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Serviço de livros: regras de cadastro, alteração, remoção, busca e ordenação.
 * O repositório é injetado pelo construtor (INVERSÃO DE CONTROLE): este serviço
 * não sabe onde os livros ficam guardados, só usa o contrato Repositorio.
 */
public class LivroService {

    private final Repositorio<Livro> repositorio;

    public LivroService(Repositorio<Livro> repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * CADASTRAR: guarda o livro se o id ainda não existir.
     * @throws IllegalArgumentException se for nulo ou se o id já existir
     */
    public void cadastrar(Livro livro) {
        if (livro == null) {
            throw new IllegalArgumentException("Os dados do livro não foram informados.");
        }
        if (buscarPorId(livro.getId()) != null) {
            throw new IllegalArgumentException("Já existe um livro com o id " + livro.getId() + ".");
        }
        repositorio.adicionar(livro);
    }

    /** BUSCAR por id (ignora maiúsculas/minúsculas). Devolve null se não achar. */
    public Livro buscarPorId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        for (Livro livro : repositorio.listarTodos()) {
            if (livro.getId().equalsIgnoreCase(id.trim())) {
                return livro;
            }
        }
        return null;
    }

    /** BUSCAR por parte do título. Devolve lista, possivelmente vazia. */
    public List<Livro> buscarPorTitulo(String trechoDoTitulo) {
        List<Livro> encontrados = new ArrayList<>();
        if (trechoDoTitulo == null || trechoDoTitulo.trim().isEmpty()) {
            return encontrados;
        }
        String trecho = trechoDoTitulo.trim().toLowerCase();
        for (Livro livro : repositorio.listarTodos()) {
            if (livro.getTitulo().toLowerCase().contains(trecho)) {
                encontrados.add(livro);
            }
        }
        return encontrados;
    }

    /** BUSCAR por parte do nome do autor. Devolve lista, possivelmente vazia. */
    public List<Livro> buscarPorAutor(String trechoDoAutor) {
        List<Livro> encontrados = new ArrayList<>();
        if (trechoDoAutor == null || trechoDoAutor.trim().isEmpty()) {
            return encontrados;
        }
        String trecho = trechoDoAutor.trim().toLowerCase();
        for (Livro livro : repositorio.listarTodos()) {
            if (livro.getAutor().toLowerCase().contains(trecho)) {
                encontrados.add(livro);
            }
        }
        return encontrados;
    }

    /** LISTAR: devolve uma cópia da lista de livros. */
    public List<Livro> listar() {
        return new ArrayList<>(repositorio.listarTodos());
    }

    /** LISTAR ORDENADO por título (ordem natural definida em Livro.compareTo). */
    public List<Livro> listarOrdenadosPorTitulo() {
        List<Livro> livros = listar();
        Collections.sort(livros);
        return livros;
    }

    /** LISTAR ORDENADO por autor. */
    public List<Livro> listarOrdenadosPorAutor() {
        List<Livro> livros = listar();
        livros.sort(Comparator.comparing(Livro::getAutor, String.CASE_INSENSITIVE_ORDER));
        return livros;
    }

    /** LISTAR ORDENADO por ano de publicação (mais antigos primeiro). */
    public List<Livro> listarOrdenadosPorAno() {
        List<Livro> livros = listar();
        livros.sort(Comparator.comparingInt(Livro::getAnoPublicacao));
        return livros;
    }

    /**
     * ALTERAR: atualiza os dados do livro identificado pelo id.
     * @throws IllegalArgumentException se o livro não existir ou algum dado for inválido
     */
    public void alterar(String id, String novoTitulo, String novoAutor, int novoAno,
                        GeneroLivro novoGenero, int novaQuantidadeTotal) {
        Livro livro = buscarPorId(id);
        if (livro == null) {
            throw new IllegalArgumentException("Nenhum livro encontrado com o id informado.");
        }
        livro.setTitulo(novoTitulo);
        livro.setAutor(novoAutor);
        livro.setAnoPublicacao(novoAno);
        livro.setGenero(novoGenero);
        livro.alterarQuantidadeTotal(novaQuantidadeTotal);
    }

    /**
     * REMOVER pelo id.
     * @throws IllegalArgumentException se o livro não existir
     */
    public void remover(String id) {
        Livro livro = buscarPorId(id);
        if (livro == null) {
            throw new IllegalArgumentException("Nenhum livro encontrado com o id informado.");
        }
        repositorio.remover(livro);
    }
}
