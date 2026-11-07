package Servicos;

import Entidades.Funcionario;
import Interfac.Repositorio;
import java.util.ArrayList;
import java.util.Comparator;

public class FuncionarioService {

   private final Repositorio<Funcionario> repositorio;
   
   public FuncionarioService(Repositorio<Funcionario> repositorio) {
        this.repositorio = repositorio;
    }

//métodos:
//3. cadastrar Funcionario
public boolean cadastrarFuncionario(Funcionario funcionario){

    if (funcionario == null){
        return false;
    }

    if (buscarPorMatricula(funcionario.getMatricula()) != null){
        return false;
    }

    if (buscarPorCpf(funcionario.getCpf()) != null){
        return false;
    }

    repositorio.adicionar(funcionario);

    return true;

}

//4.1. buscar por matricula
public Funcionario buscarPorMatricula(String matricula){

    if (matricula == null || matricula.trim().isEmpty()){
        return null;
    }

    for (Funcionario funcionario : repositorio.listarTodos()){

        if (funcionario.getMatricula().equalsIgnoreCase(matricula.trim())){
            return funcionario;
        }
    }          

    return null;
}

//4.2 buscar por cpf
public Funcionario buscarPorCpf(String cpf) {

        if (cpf == null || cpf.trim().isEmpty()) {
            return null;
        }

        String cpfPesquisado = cpf.replaceAll("[^0-9]", "");

        for (Funcionario funcionario : repositorio.listarTodos()) {

            String cpfFuncionario =
                funcionario.getCpf().replaceAll("[^0-9]", "");

            if (cpfFuncionario.equals(cpfPesquisado)) {
                return funcionario;
            }
        }

        return null;
    }

// 5. listar funcionarios
public ArrayList<Funcionario> listarFuncionarios(){
    return new ArrayList<>(repositorio.listarTodos());
}

// 6. atualizar 
public boolean atualizarFuncionario(String matricula, String novoNome, String novoTelefone, String novoEmail, String novoCargo) {

    Funcionario funcionario = buscarPorMatricula(matricula);

    if (funcionario == null){
        return false;
    }

       
    if (novoNome == null || novoNome.trim().isEmpty()
            || novoTelefone == null || novoTelefone.trim().isEmpty()
            || novoEmail == null || novoEmail.trim().isEmpty()
            || novoCargo == null || novoCargo.trim().isEmpty()){

        return false;
    }

    funcionario.setNome(novoNome);
    funcionario.setTelefone(novoTelefone); 
    funcionario.setEmail(novoEmail);
    funcionario.setCargo(novoCargo);

        return true;
    }

// 7. remover funcionario
public boolean removerFuncionario(String matricula){

    Funcionario funcionario = buscarPorMatricula(matricula);

    if(funcionario == null){
        return false;
    }

    return repositorio.remover(funcionario);

}

// 8. ordenar 
public ArrayList<Funcionario> listarOrdenadosPorNome(){

    ArrayList<Funcionario> funcionarios = listarFuncionarios();

    funcionarios.sort(
        Comparator.comparing(Funcionario::getNome, String.CASE_INSENSITIVE_ORDER));

    return funcionarios;

    }
}

