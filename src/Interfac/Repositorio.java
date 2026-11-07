package Interfac;

import java.util.List;

public interface Repositorio<T> {
    
    void adicionar(T item);

    boolean remover(T item);

    List<T> listarTodos();
}  