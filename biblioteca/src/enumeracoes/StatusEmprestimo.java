package enumeracoes;

/**
 * ENUMERAÇÃO com o estado salvo de um empréstimo.
 * O "atraso" NÃO é um status guardado: ele é calculado comparando a data de hoje com
 * a data prevista (ver Emprestimo.estaAtrasado()), pois muda sozinho com o passar dos dias.
 */
public enum StatusEmprestimo {
    ATIVO,       // livro ainda está com o leitor
    FINALIZADO   // livro já foi devolvido
}
