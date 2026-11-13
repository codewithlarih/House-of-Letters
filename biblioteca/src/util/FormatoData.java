package util;

import java.time.format.DateTimeFormatter;

/**
 * Guarda o formato de data usado em todo o sistema (dd/MM/yyyy),
 * tanto para ler o que o usuário digita quanto para exibir nas tabelas.
 */
public final class FormatoData {

    // Formato único e compartilhado: se quiser mudar o padrão de data, muda só aqui
    public static final DateTimeFormatter PADRAO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private FormatoData() {
    }
}
