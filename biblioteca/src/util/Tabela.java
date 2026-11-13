package util;

import java.util.List;

/**
 * Imprime dados em formato TABULAR no console (requisito 4 do projeto).
 * Calcula a largura de cada coluna automaticamente, então serve para qualquer entidade.
 */
public final class Tabela {

    private Tabela() {
    }

    /**
     * Imprime a tabela completa: separador, cabeçalho, separador, linhas e total.
     * @param cabecalhos títulos das colunas
     * @param linhas cada item da lista é uma linha; cada posição do vetor é uma célula
     */
    public static void imprimir(String[] cabecalhos, List<String[]> linhas) {
        if (linhas.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
            return;
        }

        // 1) Descobre a largura de cada coluna: o maior texto entre cabeçalho e células
        int[] larguras = new int[cabecalhos.length];
        for (int coluna = 0; coluna < cabecalhos.length; coluna++) {
            larguras[coluna] = cabecalhos[coluna].length();
        }
        for (String[] linha : linhas) {
            for (int coluna = 0; coluna < linha.length; coluna++) {
                larguras[coluna] = Math.max(larguras[coluna], linha[coluna].length());
            }
        }

        // 2) Imprime a tabela
        String separador = montarSeparador(larguras);
        System.out.println(separador);
        System.out.println(montarLinha(cabecalhos, larguras));
        System.out.println(separador);
        for (String[] linha : linhas) {
            System.out.println(montarLinha(linha, larguras));
        }
        System.out.println(separador);
        System.out.println("Total de registros: " + linhas.size());
    }

    // Monta uma linha como: +------+-------+
    private static String montarSeparador(int[] larguras) {
        StringBuilder separador = new StringBuilder("+");
        for (int largura : larguras) {
            for (int i = 0; i < largura + 2; i++) {
                separador.append('-');
            }
            separador.append('+');
        }
        return separador.toString();
    }

    // Monta uma linha de dados como: | texto | outro |  (cada célula preenchida com espaços à direita)
    private static String montarLinha(String[] celulas, int[] larguras) {
        StringBuilder linha = new StringBuilder("|");
        for (int coluna = 0; coluna < celulas.length; coluna++) {
            linha.append(' ')
                 .append(String.format("%-" + larguras[coluna] + "s", celulas[coluna]))
                 .append(" |");
        }
        return linha.toString();
    }
}
