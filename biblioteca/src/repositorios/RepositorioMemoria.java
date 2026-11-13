package repositorios;

import interfaces.Repositorio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementação da interface Repositorio que guarda os objetos em um ArrayList (na memória).
 * Requisito 10: "armazenar os objetos em uma estrutura de dados".
 * Uma única classe genérica serve para os 4 tipos de objeto (sem repetir código).
 *
 * @param <T> tipo de objeto guardado
 */
public class RepositorioMemoria<T> implements Repositorio<T> {

    // Estrutura de dados que guarda os registros; final = a lista em si nunca é trocada
    private final List<T> registros = new ArrayList<>();

    @Override
    public void adicionar(T item) {
        registros.add(item);
    }

    @Override
    public boolean remover(T item) {
        return registros.remove(item);
    }

    // Devolve uma visão somente-leitura para ninguém alterar a lista por fora do repositório
    @Override
    public List<T> listarTodos() {
        return Collections.unmodifiableList(registros);
    }
}
