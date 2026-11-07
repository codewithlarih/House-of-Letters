package Entidades;

public abstract class Pessoa {

    private String nome;
    private final String cpf;
    private String telefone;
    private String email;

    public Pessoa(String nome, String cpf, String telefone, String email) {
        this.nome = validarTextoObrigatorio(nome, "nome");
        this.cpf = validarTextoObrigatorio(cpf, "CPF");
        this.telefone = validarTextoObrigatorio(telefone, "telefone");
        this.email = validarTextoObrigatorio(email, "email");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = validarTextoObrigatorio(nome, "nome");
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = validarTextoObrigatorio(telefone, "telefone");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = validarTextoObrigatorio(email, "email");
    }

    public abstract String getTipoPessoa();

    public abstract String exibirResumo();

    protected static String validarTextoObrigatorio(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo " + campo + " é obrigatório.");
        }
        return texto.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa)) return false;
        return cpf.equals(((Pessoa) o).cpf);
    }

    @Override
    public int hashCode() {
        return cpf.hashCode();
    }
}
