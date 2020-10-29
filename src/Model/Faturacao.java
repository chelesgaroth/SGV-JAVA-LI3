package Model;

import java.io.Serializable;
import java.util.*;

/**
 * Classe responsável pela faturação global de cada produto:
 * Como key temos os produtos que contêm o seu código.
 * Como value temos um set, mais concretamente um TreeSet que organiza as InfoFatur de acordo com a suas filial primeiramente,
 * e após isso de acordo com o mês (para isso usamos a classe CompareFat).
 */
public class Faturacao implements IFaturacao, Serializable {

    private Map<IProduto, Set<IInfoFatur>> faturacao;

    /**
     *Construtores
     */
    public Faturacao(){
        this.faturacao = new HashMap<>();
    }

    public Faturacao (Map<IProduto, Set<IInfoFatur>> faturacao0){
        this.faturacao = faturacao0;
    }

    /**
     *Getters e Setters
     */
    public Map<IProduto, Set<IInfoFatur>> getFaturacao() {
        return faturacao;
    }

    public void setFaturacao(Map<IProduto, Set<IInfoFatur>> faturacao) {
        this.faturacao = faturacao;
    }

    /**
     * Equals
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faturacao)) return false;
        Faturacao faturacao1 = (Faturacao) o;
        return Objects.equals(getFaturacao(), faturacao1.getFaturacao());
    }

    /**
     * HashCode
     */
    public int hashCode() {
        return Objects.hash(getFaturacao());
    }

    /**
     * toString
     */
    public String toString() {
        return "Faturacao:" + "\n" +
                faturacao;
    }

    /**
     * Método que vai buscar o Set correspondente ao produto.
     */
    public Set<IInfoFatur> getTreeFat (IProduto prod) {
        return this.faturacao.get(prod);
    }

    /**Adiciona um produto à faturação.
     * Método usado quando estamos a preencher o Catálogo de Produtos.
     */
    public void addProduto(String keyPd){
        IProduto prod = new Produto();
        prod.setCodigoP(keyPd);
        Set<IInfoFatur> treeFat = new TreeSet<>(new CompareFat());
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
                if(fat.get_unidT()!=0) {
                    res += ((fat.get_FatN()) + fat.get_FatP());
                    if ((fat.get_mes() == mes) && (filial == 0)) res0 += ((fat.get_FatN()) + fat.get_FatP());
                    if ((fat.get_mes() == mes) && (fat.get_fil() == filial)) res2 += ((fat.get_FatN()) + fat.get_FatP());
                }
                else break;
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
     *  Return de um hashset para não conter produtos repetidos.
     */
    public Set<String> get_naoComprados(ICatalogoProdutos catalogo, int x){
        HashSet<String> lista = new HashSet<>();
        HashSet<String> lista2 = new HashSet<>();
        Set<IProduto> cat2 = catalogo.getProdutos();
        Set<IInfoFatur> tree ;
	    int i = 0;
        for(IProduto prod : cat2){
            tree = this.getTreeFat(prod);
            for(IInfoFatur fat : tree) {
                if (fat.get_unidT() == 0) {
                    lista.add(prod.toString());
                    i++;
                } else lista2.add(prod.toString());
                break;
            }
        }
        if(x==0) return lista;
        else return lista2;
    }

    /**
     * Método que para cada IInfoFatur da tree do produto, caso o inteiro fil seja 0 (reutilização de código, neste caso para
     *  a query 4) vai somando a faturação de cada IInfoFatur, tanto do tipo N como do tipo P, isto se o mês dessa IInfoVenda
     *  for igual ao do input.
     * Para a query 10, é necessaŕio, além de verificar o mês verificar a filial, e assim só soma as faturações relativas àquele
     * mes e àquela filial.
     * No final, caso se trate da query 4, então a fil está a zero, o que significa que não procuramos distinguir as filiais,
     * portanto devolve-se res, caso contrário devolve-se o res0.
     *
     * Além disto, é de realçar que como a tree está organizada pelas filiais, caso a filial da IInfoFatur seja superior à que
     * pretendemos, fazemos break do ciclo.
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
            if((fil!=0)&&(fat.get_fil()>fil)) break;
        }
        if(fil==0)return res;
        else return res0;
    }

    /**
     * Método que devolve o nº de unidades vendidas do produto, para isso percorre-se as IInfoFatur da tree do produto
     * e vai-se somando as unidades de cada IInfoFatur em análise.
     */
    public int nrUnidVendidas(IProduto prod){
        Set<IInfoFatur> tree = this.faturacao.get(prod);
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
