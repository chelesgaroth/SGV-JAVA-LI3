package TestesPerformance;

import Model.ICatalogoProdutos;
import Model.IProduto;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe de Teste:
 * Tem a mesma Interface que a classe CatalogoProdutos do Model. Permite assim que possamos usar esta classe com umas
 * ligeiras diferenças nas classes das coleções usadas, em vez de HashSet usamos TreeSet, mas os métodos são os mesmos.
 * O que não muda muito relativamente à classe original do Model.
 */

public class CatalogoProdutosTeste implements Serializable, ICatalogoProdutos {
    private Set<IProduto> produtos;

    /**Comparator para o TreeSet, compara os códigos de dois IProdutos, de modo a organizar a árvore alfabeticamente
     * o que melhora a procura de um produto.
     */
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

    /**Construtor vazio*/
    public CatalogoProdutosTeste(){
        this.produtos = new TreeSet<>(new The_Comparator());
    }

    /**Adiciona um produto ao catálogo*/
    public ICatalogoProdutos addProduct(IProduto p){
        this.produtos.add(p);
        return this;
    }

    /**Verifica se existe o produto ou não no catálogo*/
    public boolean existsProduct(IProduto p){
        return this.produtos.contains(p);
    }

    /**Devolve o catálogo de prdoutos*/
    public Set<IProduto> getProdutos() {
        return produtos;
    }
}