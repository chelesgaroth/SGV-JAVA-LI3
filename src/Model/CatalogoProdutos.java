package Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Esta classe é responsável por definir a estrutura que agrega todos os Produtos.
 * Definimos o catálogo de produtos como um Set<IProduto>
 */
public class CatalogoProdutos implements Serializable,ICatalogoProdutos {
    private Set<IProduto> produtos;

    /**
     *Construtor vazio
     */
    public CatalogoProdutos (){
        this.produtos = new HashSet<>();
    }

    /**
     *Adiciona um produto ao catálogo
     */
    public ICatalogoProdutos addProduct(IProduto p){
        this.produtos.add(p);
        return this;
    }

    /**
     * Verifica se existe o produto ou não no catálogo
     */

    public boolean existsProduct(IProduto p){
        return this.produtos.contains(p);
    }

    /**
     * Devolve o catálogo de prdoutos
     */
     public Set<IProduto> getProdutos() {
        return produtos;
    }

}