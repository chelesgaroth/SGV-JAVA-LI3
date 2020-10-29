package View;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Classe Tabela , organiza os resultados em colunas e linhas.
 * De acordo com o titulo , subtitulos , numero de colunas, adequadamente com a resposta que queremos apresentar.
 */

public class Tabela implements ITabela {
    private String titulo;
    private List<String> subtitulos;
    private int colunas;
    private int linhas;
    private List<Double> res;

    public Tabela(){
        this.titulo = " ";
        this.subtitulos= new ArrayList<>();
        this.colunas = 0;
        this.linhas = 0;
        this.res = new ArrayList<>();
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setSubtitulos(List<String> subtitulos) {
        this.subtitulos = subtitulos;
    }

    public void setColunas(int colunas) {
        this.colunas = colunas;
    }

    public void setRes(List<Double> res) {
        this.res = res;
    }

    public void printaTitulo(){
        System.out.print("\n»«»«»«»«»«»«»«»«»«»«" + titulo + "»«»«»«»«»«»«»«»«»«»«»\n");
    }

    public void printaSubtitulos(int x){
        for(int i=0; i<colunas; i++){
            System.out.print(subtitulos.get(i));
        }
    }
    public void printaLinha(int x,String add){

        if(x==0)System.out.print("\n«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»\n");
        if(x==1)System.out.print("\n«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»«»" + add + "\n");
    }

    /**
     * Função que printa as linhas da query 4
     */
    public void printaLinhaQuery4(Map<Integer,List<Double>> resposta){
        DecimalFormat df = new DecimalFormat("#.00");

        for (int i = 1; i <= 12; i++){
            System.out.printf("%-16d",i);
            System.out.printf("%-16d",((resposta.get(i).get(2)).intValue()));
            System.out.printf("%-16d",(resposta.get(i).get(1)).intValue());
            System.out.print(df.format(resposta.get(i).get(0)));
            System.out.println();
        }
    }

    /**
     * Funlão que printa as linhas da query 6
     */
    public void printaLinhaQuery6(Map<Integer,List<String>> res,int N){
        for(int i=0; i<N ; i++) {
            System.out.printf("%-27s",res.get(i).get(0));
            System.out.printf("%-27s",res.get(i).get(1));
            System.out.print(res.get(i).get(2));
            System.out.println();
        }
    }

    /**
     * Funções que disponibilizam os resultados das queries estatisticas do Estado do programa.
     */
    public void nrCompras(HashMap<Integer,Integer> comprasTMes) {
        for(int i=1; i<13; i++){
            System.out.printf("%-50s       ",i);
            System.out.printf("%-45s",comprasTMes.get(i));
            System.out.println();
        }
    }


    public void faturacao(HashMap<Integer,Double> fatTotalG, HashMap<Integer,Double> fatFilial1,HashMap<Integer,Double> fatFilial2,HashMap<Integer,Double> fatFilial3) {
        DecimalFormat df = new DecimalFormat("#.00");
        for(int i=1; i<13; i++){
            System.out.printf("%-10s",i);
            System.out.printf("%-20s",df.format(fatTotalG.get(i)));
            System.out.printf("%-20s",df.format(fatFilial1.get(i)));
            System.out.printf("%-20s",df.format(fatFilial2.get(i)));
            System.out.printf("%-20s",df.format(fatFilial3.get(i)));
            System.out.println();
        }
    }


    public void clientes(HashMap<Integer, List<Integer>> clientMes) {
        for(int i=1; i<13; i++) {
            System.out.printf("%-18s", i);
            System.out.printf("%-23s", clientMes.get(i).get(0));
            System.out.printf("%-23s", clientMes.get(i).get(1));
            System.out.printf("%-23s", clientMes.get(i).get(2));
            System.out.println();
        }
        System.out.println();
    }

    /**
     * As próximas funções fazem parte de um navegador especial "misturado com uma tabela" de forma a apresentar os resultados
     * de uma forma organizada de acordo com os meses do ano e a filial
     */
    public void query10() {
        this.titulo = "«»«»Faturação de cada Produto«»«»";
        printaTitulo();
    }


    public void navegadorTable( String prod, List<Double> res, int pagina,  int nTPag){
        System.out.print("\033[H\033[2J"); //limpa ecra
        System.out.flush(); //limpa ecra
        if(pagina<0) System.out.println("Página Inválida");
        if(pagina>=nTPag) System.out.println("Página Inválida");
        if((pagina>=0)&&(pagina<nTPag)) {
            DecimalFormat df = new DecimalFormat("#.00");

            System.out.print("PRODUTO: " + prod);
            printaLinha(1,"«»«»");
            System.out.print("Mês            FatFilial1              FatFilial2             FatFilial3");
            printaLinha(1,"«»«»");

            for (int i = 0; i < 12; i++){
                System.out.printf("%-15s", i);
                System.out.printf("%-24s", df.format(res.get(i)));
                System.out.printf("%-24s", df.format(res.get(12+i)));
                System.out.printf("%-20s", df.format(res.get(24+i)));
                System.out.println();
            }
            if (pagina == (nTPag-1)) System.out.println("Fim dos resultados.");
            System.out.printf("\nPágina <%d/%d> \n\n", pagina, (nTPag-1) );
        }

        System.out.println("Próxima Página(P)        Menu(M)        Página Anterior(A)");
        System.out.println("Escolha o nº da Página(N)               Total(T)      ");
    }

}
