package util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Centraliza TODA a leitura do teclado. Assim os menus não repetem código de
 * validação (número inválido, data inválida etc.) e existe apenas um Scanner no programa.
 * O Scanner é recebido pelo construtor (injeção de dependência).
 */
public class EntradaConsole {

    private final Scanner scanner;

    public EntradaConsole(Scanner scanner) {
        this.scanner = scanner;
    }

    /** Mostra a mensagem e devolve o texto digitado (sem espaços nas pontas). */
    public String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    /** Usado na ALTERAÇÃO: se o usuário apertar só ENTER, mantém o valor atual. */
    public String lerTextoOuManter(String mensagem, String valorAtual) {
        String digitado = lerTexto(mensagem + " [" + valorAtual + "]: ");
        return digitado.isEmpty() ? valorAtual : digitado;
    }

    /** Repete a pergunta até o usuário digitar um número inteiro válido. */
    public int lerInteiro(String mensagem) {
        while (true) {
            try {
                return Integer.parseInt(lerTexto(mensagem));
            } catch (NumberFormatException erro) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    /** Versão para alteração: ENTER mantém o número atual. */
    public int lerInteiroOuManter(String mensagem, int valorAtual) {
        while (true) {
            String digitado = lerTexto(mensagem + " [" + valorAtual + "]: ");
            if (digitado.isEmpty()) {
                return valorAtual;
            }
            try {
                return Integer.parseInt(digitado);
            } catch (NumberFormatException erro) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    /** Repete a pergunta até o usuário digitar uma data no formato dd/MM/yyyy. */
    public LocalDate lerData(String mensagem) {
        while (true) {
            try {
                return LocalDate.parse(lerTexto(mensagem + " (dd/MM/aaaa): "), FormatoData.PADRAO);
            } catch (DateTimeParseException erro) {
                System.out.println("Data inválida. Use o formato dd/MM/aaaa (ex.: 25/04/2023).");
            }
        }
    }

    /** Pergunta de sim/não. Aceita "s" ou "n". */
    public boolean lerSimNao(String mensagem) {
        while (true) {
            String resposta = lerTexto(mensagem + " (s/n): ").toLowerCase();
            if (resposta.equals("s")) {
                return true;
            }
            if (resposta.equals("n")) {
                return false;
            }
            System.out.println("Responda apenas com 's' ou 'n'.");
        }
    }

    /** Versão para alteração: ENTER mantém o valor atual (sim/não). */
    public boolean lerSimNaoOuManter(String mensagem, boolean valorAtual) {
        while (true) {
            String resposta = lerTexto(mensagem + " (s/n) [" + (valorAtual ? "s" : "n") + "]: ").toLowerCase();
            if (resposta.isEmpty()) {
                return valorAtual;
            }
            if (resposta.equals("s")) {
                return true;
            }
            if (resposta.equals("n")) {
                return false;
            }
            System.out.println("Responda apenas com 's' ou 'n'.");
        }
    }
}
