package TestesPerformance;

import Model.*;

import java.io.Serializable;
import java.util.*;

/**
 * Classe de Teste:
 * Desempenha a mesma função que a classe Faturação do Model.
 * Implementa também a mesma interface.
 * Deste modo, podemos ter uma estrutura diferente , ou seja com um uso diferente de classes das Coleções usadas nas
 * interfaces.
 * Neste caso, temos um TreeMap em que o value é em HashSet.
 * Ao longo da classe, temos métodos em que usamos List, aqui optamos por substituir os ArrayList por Vector.
 */
public class FaturacaoTeste implements IFaturacao, Serializable {

    private Map<IProduto, Set<IInfoFatur>> faturacao;

    class The_Comparator implements Comparator<IProduto> {
        public int compare(IProduto c1, IProduto c2)
        {
            String first_Str;
            String second_Str;
            first_Str = c1.getCodigoP();
            second_Str = c2.getCodigoP();
            return second_Str.compareTo(first_Str);
        }
    }

    /**Construtores*/
    public FaturacaoTeste(){
        this.faturacao = new TreeMap<>(new The_Comparator());
    }

    public FaturacaoTeste(Map<IProduto, Set<IInfoFatur>> faturacao0){
        this.faturacao = faturacao0;
    }

    /**Getters e Setters*/
    public Map<IProduto, Set<IInfoFatur>> getFaturacao() {
        return faturacao;
    }

    public void setFaturacao(Map<IProduto, Set<IInfoFatur>> faturacao) {
        this.faturacao = faturacao;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FaturacaoTeste)) return false;
        FaturacaoTeste faturacao1 = (FaturacaoTeste) o;
        return Objects.equals(getFaturacao(), faturacao1.getFaturacao());
    }

    public int hashCode() {
        return Objects.hash(getFaturacao());
    }

    public String toString() {
        return "Faturacao:" + "\n" +
                faturacao;
    }

    /**Devolve o value de uma key*/
    public Set<IInfoFatur> getTreeFat (IProduto prod) {
        return this.faturacao.get(prod);
    }

    /**Adiciona um produto à faturação.
     * Método usado quando estamos a preencher o Catálogo de Produtos.
     */
    public void addProduto(String keyPd){
        IProduto prod = new Produto();
        prod.setCodigoP(keyPd);
        HashSet<IInfoFatur> treeFat = new HashSet<>();
        InfoFatur fat = new InfoFatur();
        treeFat.add(fat); //Adiciona uma InfoFatur a zeros
        faturacao.put(prod,treeFat);
    }

    /**
     *Quando estamos a ler o ficheiro de vendas, adicionamos as informações da faturação ao value de Produtos que foram
     * comprados. Caso duas InfoFatur tenham a mesma filial e mês , adicionamos as duas , ficando apenas ma infoFatur de
     * modo a diminuir o comprimento do Set.
     */
    public int addInfos(IInfoFatur fat) {
        int x = 0;
        IProduto prod = new Produto();
        prod.setCodigoP(fat.get_produto());
        Set<IInfoFatur> treeFat = this.faturacao.get(prod);

        for(IInfoFatur aux : treeFat) {
            if (aux.get_unidT() == 0) {        //Para o caso de estar a adicionar pela 1ª vez uma InfoFatur
                aux.newInfo(fat);
                x = 1;
                break;
            } else {
                if (aux.compareInfos(fat)) {
                    aux.addInfos(fat);
                    x = 1;
                    break;
                }
            }
        }
        if(x==0){  //Para o caso de ainda nao haver uma info que tenha a mesma filial ou mes que aquela que queremos adicionar
            treeFat.add(fat);
            faturacao.replace(prod,treeFat);
        }
        return 1;
    }

    /**
     * Caso filial==0 , devolve a faturação global conseguida num  certo mês ou nos 12 meses caso mes==0.
     * Caso contrário, devolve a faturação da dada filial num mês ou nos 12 meses.
     */
    public double faturacaoGlobal (int mes , int filial){
        double res = 0;
        double res0 = 0;
        double res2 = 0;
        for(IProduto prod : this.faturacao.keySet()){
            Set<IInfoFatur> tree = this.getTreeFat(prod);
                for (IInfoFatur fat : tree) {
                    res += ((fat.get_FatN()) + fat.get_FatP());
                    if((fat.get_mes()==mes)&& (filial==0)) res0 += ((fat.get_FatN()) + fat.get_FatP());
                    if((fat.get_mes()==mes)&& (fat.get_fil()==filial)) res2 += ((fat.get_FatN()) + fat.get_FatP());
                }
        }
        if((mes==0) && (filial ==0)) return res;
        else if((mes!=0 ) && (filial==0)) return res0;
        else return res2;
    }

    /**
    *  Recebe como input o catálogo produto e verifica quais são aqueles que
    *  que têm como unidades compradas 0
    *  Caso x!=0, devolve os produtos comprados -> utilizado para as queries estatísticas
    * Quando x==0, devolve os produtos não comprados -> usado na query 1, por exemplo
    */
    public Set<String> get_naoComprados(ICatalogoProdutos catalogo, int x){
        TreeSet<String> lista = new TreeSet<>();
        TreeSet<String> lista2 = new TreeSet<>();
        Set<IProduto> cat2 = catalogo.getProdutos();
        Set<IInfoFatur> tree ;
	    int i = 0;
        for(IProduto prod : cat2){
            tree = this.getTreeFat(prod);
            for(IInfoFatur fat : tree) {
                if (fat.get_unidT() == 0) {
                    lista.add(prod.toString());
                    i++;
                }
                else lista2.add(prod.toString());
            }
        }
        if(x==0) return lista;
        else return lista2;
    }

    /**
     * Devolve-nos a faturação de um produto num mês globalmente (fil==0)
     * Ou por filial (fil!=0)
     */
    public double getFatMes(IProduto prod, int mes, int fil){
        double res = 0.0;
        double res0 = 0.0;
        Set<IInfoFatur> tree = getTreeFat(prod);
        for(IInfoFatur fat : tree){
            if((fat.get_mes()==mes)&&(fil==0)) res += ((fat.get_FatN()) + fat.get_FatP());
            else if((fat.get_mes()==mes)&&(fat.get_fil()==fil)){
                res0 += ((fat.get_FatN()) + fat.get_FatP());
            }
        }
        if(fil==0)return res;
        else return res0;
    }

    /**
     * Método que devolve o nº de unidades vendidas do produto, para isso percorre-se as IInfoFatur da tree do produto
     * e vai-se somando as unidades de cada IInfoFatur em análise.
     */
    public int nrUnidVendidas(IProduto prod){
        Set<IInfoFatur> tree;
        tree = this.faturacao.get(prod);
        int res = 0;
        for(IInfoFatur fat : tree){
            res += fat.get_unidT();
        }
        return res;
    }

    /** Este método devolve um array dos top produtos mais vendidos.
     * Primeiro, começa por passar preencher o tempArray com as keys (produtos).
     * Após isso, vai fazer sort do array conforme o nr de unidades vendidas de cada produto utilizando o método definido
     * anteriormente.
     * @return Array organizado conforme os produtos mais vendidos.
     */
    public IProduto[] topNVendidos (){
        IProduto[] tempArray = new IProduto[this.faturacao.size()];
        int i = 0;
        for(IProduto prod : this.faturacao.keySet()){
            tempArray[i] = prod;
            i++;
        }
        Arrays.sort(tempArray, new Comparator<IProduto>(){

            public int compare(IProduto p1, IProduto p2) {
                int res1 = nrUnidVendidas(p1);
                int res2 = nrUnidVendidas(p2);
                if(res1>res2) return -1;
                if(res1==res2) return 0;
                else return 1;
            }
        });
        return tempArray;
    }

    /**
     * Método que vai buscar o nº de unidades compradas de um mês.
     * Para tal, vai produto a produto (keys) e IInfoFatur a IInfoFatur de cada produto, buscar o mês, caso
     * esse mês seja o dado como input, então adiciona as unidades compradas dessa IInfoFatur.
     */
    public int comprasMes(int mes){
        int compras =0;
        for(IProduto prod : this.faturacao.keySet()){
            Set<IInfoFatur> tree = this.getTreeFat(prod);
                for (IInfoFatur fat : tree) {
                    if(fat.get_mes()==mes) compras += fat.get_unidT();
                }
        }
        return compras;
    }

}
