package Aplicacao;

import Servicos.FuncionarioService;
import Servicos.LivroService;
import Servicos.LeitorService;
//import Servicos.RelatorioService;
import Interfac.ServicoEmprestimo;

public class Biblioteca {
    
//reune todos os seguintes serviços em um único objeto:
// FuncionarioService, LivroService, LeitorService, 
// EmprestimoService e RelatorioService 

private final FuncionarioService funcionarioService;
private final LivroService livroService;
private final LeitorService leitorService;
private final ServicoEmprestimo emprestimoService;
//private final RelatorioService relatorioService;

    public Biblioteca(
        FuncionarioService funcionarioService,  
        LivroService livroService, 
        LeitorService leitorService,
        ServicoEmprestimo emprestimoService) 
        //RelatorioService relatorioService) 
        {

        
        this.funcionarioService = funcionarioService;
        this.livroService = livroService;
        this.leitorService = leitorService;
        this.emprestimoService = emprestimoService;
        //this.relatorioService = relatorioService;
    }

    public FuncionarioService getFuncionarioService() {
        return funcionarioService;
    }

    public LivroService getLivroService() {
        return livroService;
    }

    public LeitorService getLeitorService() {
        return leitorService;
    }

    public ServicoEmprestimo getEmprestimoService() {
        return emprestimoService;
    }

    //public RelatorioService getRelatorioService() {
    //    return relatorioService;
    //}

}
