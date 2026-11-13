package util;

/**
 * Classe utilitária com validações reaproveitadas por várias entidades.
 * Evita repetir o mesmo "if (texto == null || texto.isEmpty())" em cada classe.
 */
public final class Validacao {

    // Construtor privado: a classe só tem métodos estáticos, então ninguém deve instanciá-la
    private Validacao() {
    }

    /**
     * Garante que um texto foi informado (não nulo e não vazio).
     * @param texto texto digitado/recebido
     * @param nomeDoCampo nome do campo, usado na mensagem de erro
     * @return o texto sem espaços sobrando no início e no fim
     * @throws IllegalArgumentException se o texto for nulo ou vazio
     */
    public static String textoObrigatorio(String texto, String nomeDoCampo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo " + nomeDoCampo + " é obrigatório.");
        }
        return texto.trim();
    }

    /**
     * Remove tudo que não for dígito. Ex.: "123.456.789-01" vira "12345678901".
     * Usado para comparar CPFs independentemente da pontuação digitada.
     */
    public static String somenteDigitos(String texto) {
        return texto == null ? "" : texto.replaceAll("[^0-9]", "");
    }
}
