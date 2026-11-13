package entidades;

import enumeracoes.GeneroLivro;
import java.time.LocalDate;
import java.util.Objects;
import util.Validacao;

/**
 * Livro do acervo. Controla o estoque: quantidade total de exemplares e
 * quantos estão disponíveis (os demais estão emprestados).
 * Implementa Comparable: a ordem natural dos livros é alfabética pelo título.
 */
public class Livro implements Comparable<Livro> {

    private final String id;           // identificador único; não muda
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private GeneroLivro genero;
    private int quantidadeTotal;       // exemplares que a biblioteca possui
    private int quantidadeDisponivel;  // exemplares que estão na estante agora

    /** No cadastro, todos os exemplares começam disponíveis. */
    public Livro(String id, String titulo, String autor, int anoPublicacao,
                 GeneroLivro genero, int quantidadeTotal) {
        this.id = Validacao.textoObrigatorio(id, "id");
        this.titulo = Validacao.textoObrigatorio(titulo, "título");
        this.autor = Validacao.textoObrigatorio(autor, "autor");
        this.anoPublicacao = validarAno(anoPublicacao);
        this.genero = validarGenero(genero);
        if (quantidadeTotal < 1) {
            throw new IllegalArgumentException("A quantidade de exemplares deve ser de pelo menos 1.");
        }
        this.quantidadeTotal = quantidadeTotal;
        this.quantidadeDisponivel = quantidadeTotal;
    }

    // ----- Getters e setters com validação -----

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = Validacao.textoObrigatorio(titulo, "título");
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = Validacao.textoObrigatorio(autor, "autor");
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = validarAno(anoPublicacao);
    }

    public GeneroLivro getGenero() {
        return genero;
    }

    public void setGenero(GeneroLivro genero) {
        this.genero = validarGenero(genero);
    }

    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    /**
     * Altera o total de exemplares mantendo o estoque coerente:
     * os exemplares emprestados continuam emprestados.
     * @throws IllegalArgumentException se o novo total for menor que a quantidade emprestada
     */
    public void alterarQuantidadeTotal(int novoTotal) {
        int emprestados = quantidadeTotal - quantidadeDisponivel;
        if (novoTotal < emprestados || novoTotal < 1) {
            throw new IllegalArgumentException("O total não pode ser menor que os exemplares emprestados ("
                    + emprestados + ") nem menor que 1.");
        }
        quantidadeTotal = novoTotal;
        quantidadeDisponivel = novoTotal - emprestados;
    }

    // ----- Regras de estoque -----

    /** Tira um exemplar da estante. Devolve false se não houver nenhum disponível. */
    public boolean emprestarExemplar() {
        if (quantidadeDisponivel > 0) {
            quantidadeDisponivel--;
            return true;
        }
        return false;
    }

    /** Coloca um exemplar de volta. Devolve false se todos já estavam na estante. */
    public boolean devolverExemplar() {
        if (quantidadeDisponivel < quantidadeTotal) {
            quantidadeDisponivel++;
            return true;
        }
        return false;
    }

    /** Indica se há pelo menos um exemplar para emprestar. */
    public boolean estaDisponivel() {
        return quantidadeDisponivel > 0;
    }

    // ----- Validações privadas -----

    // O ano precisa ser positivo e não pode ser maior que o ano atual
    private static int validarAno(int ano) {
        if (ano <= 0 || ano > LocalDate.now().getYear()) {
            throw new IllegalArgumentException("Ano de publicação inválido.");
        }
        return ano;
    }

    private static GeneroLivro validarGenero(GeneroLivro genero) {
        if (genero == null) {
            throw new IllegalArgumentException("O gênero é obrigatório.");
        }
        return genero;
    }

    // ----- Comparação / igualdade -----

    // Ordem natural: alfabética pelo título
    @Override
    public int compareTo(Livro outro) {
        return titulo.compareToIgnoreCase(outro.titulo);
    }

    // Dois livros são iguais se tiverem o mesmo id
    @Override
    public boolean equals(Object outro) {
        if (this == outro) {
            return true;
        }
        if (outro == null || getClass() != outro.getClass()) {
            return false;
        }
        return id.equalsIgnoreCase(((Livro) outro).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.toLowerCase());
    }

    @Override
    public String toString() {
        return "[" + id + "] " + titulo + " - " + autor + " (" + anoPublicacao + ")";
    }
}
