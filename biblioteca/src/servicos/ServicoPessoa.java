package servicos;

import entidades.Pessoa;
import interfaces.Repositorio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe ABSTRATA com as operações que Leitor e Funcionário têm em comum
 * (cadastrar, buscar, listar, ordenar, remover). Antes esse código estava duplicado
 * em FuncionarioService e LeitorService; agora fica num lugar só (HERANÇA para reuso).
 *
 * @param <T> tipo de pessoa tratado (Leitor ou Funcionario)
 */
public abstract class ServicoPessoa<T extends Pessoa> {

    // O repositório é RECEBIDO pelo construtor: a classe não faz "new" dele (INJEÇÃO DE DEPENDÊNCIA)
    protected final Repositorio<T> repositorio;

    protected ServicoPessoa(Repositorio<T> repositorio) {
        this.repositorio = repositorio;
    }

    /** Cada subclasse informa seu nome ("leitor", "funcionário") para as mensagens de erro. */
    protected abstract String nomeDoTipo();

    /**
     * CADASTRAR: guarda a pessoa, desde que matrícula e CPF ainda não existam.
     * @throws IllegalArgumentException se for nula ou duplicada
     */
    public void cadastrar(T pessoa) {
        if (pessoa == null) {
            throw new IllegalArgumentException("Os dados do " + nomeDoTipo() + " não foram informados.");
        }
        if (buscarPorMatricula(pessoa.getMatricula()) != null) {
            throw new IllegalArgumentException("Já existe " + nomeDoTipo() + " com a matrícula "
                    + pessoa.getMatricula() + ".");
        }
        if (buscarPorCpf(pessoa.getCpf()) != null) {
            throw new IllegalArgumentException("Já existe " + nomeDoTipo() + " com este CPF.");
        }
        repositorio.adicionar(pessoa);
    }

    /** BUSCAR por matrícula (ignora maiúsculas/minúsculas). Devolve null se não achar. */
    public T buscarPorMatricula(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            return null;
        }
        for (T pessoa : repositorio.listarTodos()) {
            if (pessoa.getMatricula().equalsIgnoreCase(matricula.trim())) {
                return pessoa;
            }
        }
        return null;
    }

    /** BUSCAR por CPF (aceita com ou sem pontuação). Devolve null se não achar. */
    public T buscarPorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return null;
        }
        for (T pessoa : repositorio.listarTodos()) {
            if (pessoa.temCpf(cpf)) {
                return pessoa;
            }
        }
        return null;
    }

    /** BUSCAR por parte do nome (ignora maiúsculas/minúsculas). Devolve lista, possivelmente vazia. */
    public List<T> buscarPorNome(String trechoDoNome) {
        List<T> encontradas = new ArrayList<>();
        if (trechoDoNome == null || trechoDoNome.trim().isEmpty()) {
            return encontradas;
        }
        String trecho = trechoDoNome.trim().toLowerCase();
        for (T pessoa : repositorio.listarTodos()) {
            if (pessoa.getNome().toLowerCase().contains(trecho)) {
                encontradas.add(pessoa);
            }
        }
        return encontradas;
    }

    /** LISTAR: devolve uma cópia da lista (quem recebe pode ordenar sem afetar o repositório). */
    public List<T> listar() {
        return new ArrayList<>(repositorio.listarTodos());
    }

    /** LISTAR ORDENADO por nome, usando a ordem natural definida em Pessoa.compareTo. */
    public List<T> listarOrdenadosPorNome() {
        List<T> pessoas = listar();
        Collections.sort(pessoas);
        return pessoas;
    }

    /**
     * REMOVER pela matrícula.
     * @throws IllegalArgumentException se não existir ninguém com essa matrícula
     */
    public void remover(String matricula) {
        repositorio.remover(exigirPorMatricula(matricula));
    }

    /** Busca por matrícula e lança erro se não achar (evita repetir o "if null" nas subclasses). */
    protected T exigirPorMatricula(String matricula) {
        T pessoa = buscarPorMatricula(matricula);
        if (pessoa == null) {
            throw new IllegalArgumentException("Nenhum " + nomeDoTipo() + " encontrado com a matrícula informada.");
        }
        return pessoa;
    }
}
