package Repositorio;

import Interfac.Repositorio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositorioMemoria<T> implements Repositorio<T> {

    private final List<T> registros = new ArrayList<>();

    @Override
    public void adicionar(T item) {
        registros.add(item);
    }

    @Override
    public boolean remover(T item) {
        return registros.remove(item);
    }

    @Override
    public List<T> listarTodos() {
        return Collections.unmodifiableList(registros);
    }
}