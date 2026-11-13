package entidades;

import java.util.Objects;
import util.Validacao;

/**
 * Classe ABSTRATA que representa qualquer pessoa do sistema.
 * Conceitos demonstrados: HERANÇA (Leitor e Funcionario estendem esta classe),
 * CLASSE ABSTRATA (não pode ser instanciada com "new Pessoa(...)") e
 * POLIMORFISMO (cada subclasse implementa getTipoPessoa() e exibirResumo() do seu jeito).
 * Implementa Comparable para que listas de pessoas possam ser ordenadas por nome.
 */
public abstract class Pessoa implements Comparable<Pessoa> {

    // Atributos comuns a Leitor e Funcionario (ficam aqui para não repetir nas duas classes)
    private String nome;
    private final String cpf;        // final: o CPF não muda depois de cadastrado
    private final String matricula;  // final: é o identificador usado para buscar/alterar/excluir
    private String telefone;
    private String email;

    /**
     * Construtor chamado pelas subclasses (por isso protected). Valida todos os campos.
     * @throws IllegalArgumentException se algum campo estiver vazio ou o CPF não tiver 11 dígitos
     */
    protected Pessoa(String nome, String cpf, String telefone, String email, String matricula) {
        this.nome = Validacao.textoObrigatorio(nome, "nome");
        this.cpf = Validacao.textoObrigatorio(cpf, "CPF");
        if (Validacao.somenteDigitos(this.cpf).length() != 11) {
            throw new IllegalArgumentException("O CPF deve ter 11 dígitos.");
        }
        this.telefone = Validacao.textoObrigatorio(telefone, "telefone");
        this.email = Validacao.textoObrigatorio(email, "email");
        this.matricula = Validacao.textoObrigatorio(matricula, "matrícula");
    }

    // ----- Getters e setters (CPF e matrícula não têm setter: não podem ser alterados) -----

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = Validacao.textoObrigatorio(nome, "nome");
    }

    public String getCpf() {
        return cpf;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = Validacao.textoObrigatorio(telefone, "telefone");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Validacao.textoObrigatorio(email, "email");
    }

    // ----- Métodos ABSTRATOS: cada subclasse é obrigada a implementar -----

    /** Devolve o tipo da pessoa, ex.: "Leitor" ou "Funcionário". */
    public abstract String getTipoPessoa();

    /** Devolve uma frase-resumo com os dados específicos da subclasse. */
    public abstract String exibirResumo();

    /** Compara o CPF (só dígitos) com o informado. Usado nas buscas por CPF. */
    public boolean temCpf(String outroCpf) {
        return Validacao.somenteDigitos(cpf).equals(Validacao.somenteDigitos(outroCpf));
    }

    // Ordem natural das pessoas: alfabética pelo nome (ignorando maiúsculas/minúsculas)
    @Override
    public int compareTo(Pessoa outra) {
        return nome.compareToIgnoreCase(outra.nome);
    }

    // Duas pessoas são a mesma se tiverem o mesmo CPF (só dígitos)
    @Override
    public boolean equals(Object outro) {
        if (this == outro) {
            return true;
        }
        if (!(outro instanceof Pessoa)) {
            return false;
        }
        return temCpf(((Pessoa) outro).cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Validacao.somenteDigitos(cpf));
    }
}
