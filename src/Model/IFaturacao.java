package Model;

import java.util.*;

public interface IFaturacao {

    public Map<IProduto, Set<IInfoFatur>> getFaturacao();
    public void setFaturacao(Map<IProduto, Set<IInfoFatur>> faturacao);
    String toString();
    void addProduto(String keyPd);
    int addInfos(IInfoFatur fat);

    double faturacaoGlobal(int mes,int filial);

    Set<String> get_naoComprados(ICatalogoProdutos catalogo, int x);

    double getFatMes(IProduto prod, int mes, int fil);

    int nrUnidVendidas(IProduto prod);
    IProduto[] topNVendidos();

    int comprasMes(int mes);
}
