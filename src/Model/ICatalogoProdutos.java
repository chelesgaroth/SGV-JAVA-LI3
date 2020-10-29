package Model;

import java.util.Set;

public interface ICatalogoProdutos{
    ICatalogoProdutos addProduct(IProduto p);
    boolean existsProduct(IProduto p);
    Set<IProduto> getProdutos();
}
