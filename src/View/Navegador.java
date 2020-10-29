package View;

import java.util.*;

/**
 * Classe responsável por apresentar um grande conjunto de dados ao utilizador de uma forma mais limitada , apresentando
 * apenas o subconjunto de dados que o utilizador deseja consultar.
 */

public class Navegador implements INavegador {
    private int tamPag; /**nº de strings por pagina ou seja tamanho da pagina*/
    private int nTPag; /**nº total de páginas*/
    private int pagina; /** nº da página atual*/
    private int inseridos; /**nº de strings já inseridas*/


    public Navegador(){
        this.tamPag = 20;
        this.nTPag = 0;
        this.pagina = 1;
        this.inseridos = 0;
    }

    /**
     * Método que divide as respostas em subconjuntos de 20 elementos.
     * Tendo em conta o número total de elementos por página.
     * O número de elementos inseridos e página em que o navegador se encontra.
     *
     * Primeiramente dividimos o número total de elemntos da resposta por 20 de modo a obtermos o número total de páginas
     * que o nosso navegador terá.
     * Após isto, apresentamos as respostas em duas colunas de 10 linhas , obtemos assim 20 elementos a apresentar na página.
     * Assim que chegamos ao último elemento das respostas apresentamos ao utilizador "fim do resultados".
     */
    public void divide(List<String> resposta, List<Integer> resposta2,String mensagem,int q){
        System.out.print("\033[H\033[2J"); //limpa ecra
        System.out.flush(); //limpa ecra
        int i;
        this.nTPag = resposta.size() / 20;
        if((this.pagina <= this.nTPag) && (this.inseridos <= resposta.size())){
            System.out.println("\u001B[1m");
            System.out.println("\033[1;35m" + mensagem);
            for(i = this.inseridos; (i< (this.tamPag + this.inseridos)) && (i<resposta.size()); i++ ){
                if (q==1) {
                    System.out.print("\033[1;36m" + i + ": " + "\033[1;37m" + resposta.get(i));
                }
                else if (q==4) {
                    System.out.print("\033[1;36m"+i + ": " + "\033[1;37m"+resposta.get(i));
                    System.out.print("\033[1;36m"+ "  Quantidade: "+"\033[1;37m"+resposta2.get(i));
                }
                int j=i+10;
                if(j<resposta.size()) {
                    if (q == 1) {
                        System.out.print("\033[1;36m" + "    " + j + ": " + "\033[1;37m" + resposta.get(j) + "\n");
                    } else if (q == 4) {
                        System.out.print("\033[1;36m" + "    " + j + ": " + "\033[1;37m" + resposta.get(j));
                        System.out.print("\033[1;36m" + "  Quantidade: " + "\033[1;37m" + resposta2.get(j) + "\n");
                    }
                }
                else System.out.print("\n");
                if(i==(this.inseridos+9)) break;
            }
            if(i>=resposta.size()) System.out.println("Fim dos resultados.");
            System.out.printf("\033[1;36m"+"\nPágina <%d/%d> \n\n"+ "\033[0m",this.pagina ,this.nTPag);
        }
    }


    /**
     * Método responsável por apresentar a próxima página ao utilizador.
     * Adicionamos +1 à variavel de instancia responsável por nos indicar em que página estamos.
     * E adicionamos +20 aos elementos inseridos, de forma a termos acesso aos próximos 20 elementos da List<>.
     */
    public void proxima(List<String> resposta, List<Integer> resposta2, String mensagem,int q){
        this.pagina += 1;
        this.inseridos += 20;
        divide(resposta,resposta2,mensagem,q);
    }

    /**
     * Responsável por mostrar a página anterior do navegador.
     * Ao contrário da função anterior, subtraimos 1 ao número da página em que estamos. E subtraimos 20 ao número de
     * elementos inseridos, para termos acesso ao indice equivalente a menos 20 daquele em que nós estamos.
     * A página é inválida, caso os inseridos sejam menor do que 0 e a página também seja menor do que 0.
     */
    public void anterior(List<String> resposta, List<Integer> resposta2, String mensagem, int q){
        this.pagina -= 1;
        this.inseridos -= 20;
        if((this.pagina >= 0) && (this.inseridos >=0)){
            divide(resposta,resposta2, mensagem,1);
        }
        else {
            System.out.print("\033[1;31m\nPágina Inválida\n\n" + "\033[0m");
        }
    }

    /**
     * Disponibiliza ao utilizador o número total de respostas.
     */
    public void total(List<String> resposta){
        System.out.print("\033[1;36mTotal: " + "\033[1;37m" + resposta.size() + "\n\n" + "\033[0m");
    }

    /**
     *Caso o utilizador pretenda consultar uma determinada página, multiplicamos essa página por 20 de forma a obtermos
     * o indice da lista de respostas que queremos apresentar.
     */
    public void escolha(List<String> resposta, List<Integer> resposta2, String mensagem, int q, int num){
        this.pagina = num;
        this.inseridos = num*20;
        divide(resposta,resposta2,mensagem, q);
    }

    /**
     * Menu do navegador
     */
    public void menu (){
        System.out.println("\033[1;35m"+"Próxima Página(P)        Menu(M)        Página Anterior(A)"+ "\033[0m");
        System.out.println("\033[1;35m"+"Escolha o nº da Página(N)               Total(T)       "+ "\033[0m");
    }
}
