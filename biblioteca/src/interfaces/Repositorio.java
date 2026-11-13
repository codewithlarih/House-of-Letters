package interfaces;

import java.util.List;

/**
 * INTERFACE (contrato) de armazenamento de objetos.
 * Quem usa um Repositorio não sabe SE os dados ficam em memória, arquivo ou banco:
 * isso é o que permite a INVERSÃO DE CONTROLE / INJEÇÃO DE DEPENDÊNCIA nos serviços.
 *
 * @param <T> tipo de objeto guardado (Livro, Leitor, Funcionario, Emprestimo)
 */
public interface Repositorio<T> {

    /** Guarda um novo item. */
    void adicionar(T item);

    /** Remove o item; devolve true se ele existia e foi removido. */
    boolean remover(T item);

    /** Devolve todos os itens guardados. */
    List<T> listarTodos();
}
