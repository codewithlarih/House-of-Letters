package Enumeracoes;

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

    private final String descricao;

    // Construtor da enum
    GeneroLivro(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}