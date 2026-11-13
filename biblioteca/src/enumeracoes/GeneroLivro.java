package enumeracoes;

/**
 * ENUMERAÇÃO com os gêneros possíveis de um livro.
 * Usar enum evita erros de digitação (ex.: "Ficçao", "ficcao") e deixa o código mais legível.
 */
public enum GeneroLivro {
    FICCAO("Ficção"),
    NAO_FICCAO("Não-Ficção"),
    ROMANCE("Romance"),
    FANTASIA("Fantasia"),
    TECNOLOGIA("Tecnologia"),
    CIENCIAS("Ciências"),
    HISTORIA("História"),
    BIOGRAFIA("Biografia"),
    INFANTIL("Infantil"),
    DIDATICO("Didático"),
    OUTROS("Outros");

    // Nome "bonito" que será exibido para o usuário
    private final String descricao;

    // Construtor do enum: recebe o texto de cada constante acima
    GeneroLivro(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    // Ao imprimir o gênero, aparece a descrição (ex.: "Ficção") em vez de "FICCAO"
    @Override
    public String toString() {
        return descricao;
    }
}
