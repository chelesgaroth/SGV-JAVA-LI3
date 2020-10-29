package Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public interface IGestaoVendas {
    /**
     * Getters
     */
    ICatalogoClientes getCatCl();
    ICatalogoProdutos getCatPd();
    IFilial getF1();
    IFilial getF2();
    IFilial getF3();
    IFaturacao getFt();
    IDadosFicheiro getDataF();
    IDadosEstado getDataE();

    /**
     *  Setters
     */
    void setF1(IFilial f1);
    void setF2(IFilial f2);
    void setF3(IFilial f3);
    void setCatCl(ICatalogoClientes cl);
    void setCatPd(ICatalogoProdutos pd);
    void setFt(IFaturacao fat);
    void setDataF(IDadosFicheiro dataF);

    /**
     * Inserções
     */
    void insereCliente(ICliente cliente);
    void insereProduto(IProduto prod);

    void filesData();
    void estadoData();

    /**
     * Queries
     */
    List<String> query_1();
    int[] query_2(int mes);
    List<Double> query_3(String key);
    Map<Integer,List<Double>> query_4(String keyProd);
    Map<String,Integer> query_5(String cliente);
    Map<Integer, List<String>> query_6(int N);
    List<String> query_7();
    Map<String,Integer> query_8(int nrclientes);
    Map<Integer,List<String>> query_9(String prod, int x);
    Map<String,List<Double>> query_10();
}
