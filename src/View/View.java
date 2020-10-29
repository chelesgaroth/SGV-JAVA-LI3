package View;

import Model.IDadosEstado;
import Model.IDadosFicheiro;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Classe responsável pela apresentação de resultados. Contém as show_query que através do input que recebem ( resultado da
 * query em questão ) apresenta-o ao utilizador.
 *
 * Tem o Navegador e a Tabela. De modo a apresentar os resultados ao utilizador de uma forma mais organizada.
 */
public class View implements IView {
    INavegador navegador;
    ITabela tabela;

    /** Construtor */
    public View(){
        this.navegador= new Navegador();
        this.tabela = new Tabela();
    }

    /** Getters */
    public INavegador getNavegador() {
        return navegador;
    }

    public ITabela getTabela() {
        return tabela;
    }

    /**
     * Método que apresenta o menu
     */
    public void  menu (){

        System.out.println();
        System.out.println ("\033[1;35m"+"*************************** MENU ***************************");
        System.out.println ("\033[1;35m"+"                     Selecione uma opção");
        linha();
        System.out.println("0- Carregar ficheiros");
        linha();
        System.out.println("1- Lista dos produtos nunca comprados");
        linha();
        System.out.println("2- Nº total de vendas e de clientes de um mês, por filial");
        linha();
        System.out.println("3- Nº de compras, nº de produtos e total comprado de cliente");
        linha();
        System.out.println("4- Nº de compras de produto,nº clientes e total € por mês");
        linha();
        System.out.println("5- Lista de produtos que um cliente mais comprou");
        linha();
        System.out.println("6- N produtos mais vendidos e nº de clientes que comprou");
        linha();
        System.out.println("7- Lista dos 3 maiores comprados de cada filial");
        linha();
        System.out.println("8- Lista dos N clientes que compraram mais produtos ");
        linha();
        System.out.println("9- N clientes que mais compraram um produto");
        linha();
        System.out.println("10- Faturação mês a mês, por filial de produto");
        linha();
        System.out.println("11- Dados referentes ao último ficheiro de vendas lido");
        linha();
        System.out.println("12- Dados registados nas estruturas");
        linha();
        System.out.println("\033[1;35m13- \033[1;37mSave");
        linha();
        System.out.println("\033[1;35m14- \033[1;37mLoad");
        linha();
        System.out.println("\033[1;35m"+"15- SAIR\n"+"\033[0m");

    }
    public static void linha(){
        System.out.println("\033[1;36m"+" ----------------------------------------------------------"+"\033[0m");
    }

    /**
     * Método que dá as opções dos ficheiros assim como os pedidos de ficheiros
     */
    public void files(int x){
        if (x==0) {
            System.out.println("1- Pretendo carregar os ficheiros default");
            System.out.println("2- Pretendo carregar os meus ficheiros");
        }
        if (x==1) System.out.println("Insira o ficheiro dos produtos");
        if (x==2) System.out.println("Insira o ficheiro dos clientes");
        if (x==3) System.out.println("Insira o ficheiro das vendas");
    }
    public void printMensagem (String mensagem){
        System.out.println (mensagem);
    }

    public void yesOrNo (){
        System.out.print("(Y)Yes                                  (N)No\n");
    }

    /**
     * Método que apresenta o resultado da leitura dos ficheiros.
     */
    public void ficheiros(int res,String fCl, String fPd, String fVd){
        if (res == 0) {
            System.out.println(" ");
            System.out.println("\033[0;96m"+"Ficheiros lidos com sucesso"+"\033[0m");
            System.out.println("\033[0;96m"+"Ficheiros: " + fCl + " " + fPd + " " + fVd+"\033[0m");
        } else if (res == -1) {

            System.out.println("\033[0;96m"+"Ficheiros não encontrados"+"\033[0m");
            System.out.println("\033[0;96m"+"Lamentamos o sucedido, por favor tente novamente"+"\033[0m");
        }
    }

    /** Métodos para apresentar os resultados das queries de estatística */
    public void estatistica(IDadosFicheiro data,String file){
        System.out.println();
        System.out.println("\033[1;35m" +"FICHEIRO: " + "\033[1;37m" + file);
        System.out.println("\033[1;37m" + "«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»");
        System.out.println("\033[1;36m" + "«1»" + "\033[1;37m" + " Nº total de produtos: " + "\033[0m" + data.getTotalProds());
        System.out.println("\033[1;36m" + "«2»" + "\033[1;37m" + " Nº de produtos comprados: " + "\033[0m" + data.getProdsComprados());
        System.out.println("\033[1;36m" + "«3»" + "\033[1;37m" + " Nº de produtos não comprados: " + "\033[0m" + data.getProdsNComprados());
        System.out.println("\033[1;36m" + "«4»" + "\033[1;37m" + " Nº total de clientes: " + "\033[0m" + data.getNumClientes());
        System.out.println("\033[1;36m" + "«5»" + "\033[1;37m" + " Nº de clientes que compraram: " + "\033[0m" + data.getClientesCompraram());
        System.out.println("\033[1;36m" + "«6»" + "\033[1;37m" + " Nº de clientes que não compraram: " + "\033[0m" + data.getClientesNCompraram());
        System.out.println("\033[1;36m" + "«7»" + "\033[1;37m" + " Compras feitas a valor 0.0: " + "\033[0m" + data.getComprasGratis());
        System.out.println("\033[1;36m" + "«8»" + "\033[1;37m" + " Faturação Total: " + "\033[0m" + data.getFatTotal());
        System.out.println();
    }

    public void estatistica2(IDadosEstado data){
        System.out.println();
        tabela.setColunas(2);
        List<String> sub = new ArrayList<>();
        sub.add("Mês");
        sub.add("                                                      Nº Compras");
        tabela.setSubtitulos(sub);
        tabela.setTitulo("Número total de compras por mês");
        tabela.printaTitulo();
        tabela.printaSubtitulos(15);
        tabela.printaLinha(1,"«»");
        tabela.nrCompras(data.getComprasTMes());

        tabela.setColunas(5);
        List<String> sub2 = new ArrayList<>();
        sub2.add("Mês");
        sub2.add("         FatGlobal ");
        sub2.add("         FatFilial1  ");
        sub2.add("         FatFilial2");
        sub2.add("         FatFilial3");
        tabela.setSubtitulos(sub2);
        tabela.setTitulo("«»«»«»«»«»«»«»Faturação por mês«»«»«»«»«»«»«»");
        tabela.printaTitulo();
        tabela.printaSubtitulos(15);
        tabela.printaLinha(1,"«»«»«»«»«»«»«»«»");
        tabela.faturacao(data.getFatTotalG(),data.getFatFilial1(),data.getFatFilial2(),data.getFatFilial3());

        tabela.setColunas(4);
        List<String> sub3 = new ArrayList<>();
        sub3.add("Mês");
        sub3.add("           ClientesFil 1");
        sub3.add("           ClientesFil 2");
        sub3.add("           ClientesFil 3");
        tabela.setSubtitulos(sub3);
        tabela.setTitulo("«»«»«»Nº de compradores por mês«»«»«»");
        tabela.printaTitulo();
        tabela.printaSubtitulos(15);
        tabela.printaLinha(1,"«»«»«»«»");
        tabela.clientes(data.getClientMes());

    }

    /**
     * Métodos de apresentação de resultados das queries 2,3,4,6,7,8 e 9.
     */
     public void show_query_2 (int [] res, int mes) {
        System.out.println("No mês "+ mes);
        System.out.println("\033[1;36m"+"-------------------------------------"+"\033[0m");
        System.out.println("FILIAL 1");
        System.out.println("Número de vendas "+ res[0]);
        System.out.println("Número de clientes distintos "+ res[1]);
        System.out.println("\033[1;36m"+"-------------------------------------"+"\033[0m");
        System.out.println("FILIAL 2");
        System.out.println("Número de vendas "+ res[2]);
        System.out.println("Número de clientes distintos "+ res[3]);
        System.out.println("\033[1;36m"+"-------------------------------------"+"\033[0m");
        System.out.println("FILIAL 3");
        System.out.println("Número de vendas "+ res[4]);
        System.out.println("Número de clientes distintos "+ res[4]);
        System.out.println("\033[1;36m"+"-------------------------------------"+"\033[0m");
    }

    public void show_query_3(List<Double> res){
        DecimalFormat df = new DecimalFormat("#.00");

        int i =0;
        while (res.size()>i) {
            System.out.println("MES: "+res.get(i).intValue());
            i++;
            System.out.println("Nº de compras: "+res.get(i).intValue());
            i++;
            System.out.println("Nº de produtos distintos: "+res.get(i).intValue());
            i++;
            System.out.println("Total gasto "+df.format(res.get(i)));
            System.out.println("-------------------------------------");
            i++;
        }
    }

    public void show_query4(Map<Integer,List<Double>> resposta, String prod) {

        tabela.setColunas(4);
        List<String> sub = new ArrayList<>();
        sub.add("Mês");
        sub.add("           Vendas");
        sub.add("           Clientes");
        sub.add("           TOTAL");
        tabela.setSubtitulos(sub);
        tabela.setTitulo("Produto: " + prod);
        tabela.printaTitulo();
        tabela.printaSubtitulos(15);
        tabela.printaLinha(0,"");
        tabela.printaLinhaQuery4(resposta);
        System.out.println();

    }

    public void showQuery6(Map<Integer,List<String>> res, int N){
        tabela.setColunas(3);
        List<String> sub = new ArrayList<>();
        sub.add("Produto");
        sub.add("                    Unidades");
        sub.add("                   Clientes");
        tabela.setSubtitulos(sub);
        tabela.setTitulo("Top " + N + " Produtos Mais Vendidos");
        tabela.printaTitulo();
        tabela.printaSubtitulos(15);
        tabela.printaLinha(1,"");
        tabela.printaLinhaQuery6(res,N);
        System.out.println();
    }
    public void show_query7 (List<String> resultado) {
        int i=0;
        DecimalFormat df = new DecimalFormat("#.00");
        for(String c : resultado) {
            if (i==0) System.out.println("--------Filial 1--------");
            if (i==6) System.out.println("--------Filial 2--------");
            if (i==12) System.out.println("--------Filial 3--------");
            if(i % 2 == 0)
                System.out.print("Cliente "+c);
            else   System.out.println("       Quantidade "+Float.valueOf(c));
            i++;
        }
    }

    public void show_query8 (Map<String,Integer> resultado) {
        int i=0;
      Set <String> keys = resultado.keySet();
      List<String> list = new ArrayList<>();
        for (String t : keys){
            list.add(t);
        }
        for (int qtd : resultado.values()) {
          System.out.println("\033[1;36m"+"Cliente: "+"\033[0m" + list.get(i) + "\033[1;36m"+ "    Quantidade: "+"\033[0m" +qtd);
          i++;
      }
    }

    public void show_query9(Map<Integer,List<String>> res, int N){
        tabela.setColunas(3);
        List<String> sub = new ArrayList<>();
        sub.add("Cliente");
        sub.add("                    Unidades");
        sub.add("                   Lucro");
        tabela.setSubtitulos(sub);
        tabela.setTitulo("»«»«»«Top " + N + " Compradores«»«»«»");
        tabela.printaTitulo();
        tabela.printaSubtitulos(15);
        tabela.printaLinha(1,"");
        tabela.printaLinhaQuery6(res,N);
        System.out.println();
    }

}
