package Model;

import java.util.*;

public interface IFilial {
    /**
     * Métodos para efetuar get e set . Método add para adicionar elementos à estrutura. ListaClientes para get das keys todas do map.
     */
    Map<ICliente, Set<IInfoVenda>> getFilial();
    void setFilial(Map<ICliente, Set<IInfoVenda>> filial);
    void addVenda(ICliente cliente, IInfoVenda v);
    Set<ICliente> listaClientes();

    /**
     * Métodos auxiliares para as queries
     */
    int[] produtoMes(int mes, Set<IInfoVenda> tree);
    Set<IInfoVenda> getTreeFil(ICliente key);
    double[] clienteCompras(String key, int mes);
    Set<Integer> getMesCliente(String key);
    List<Integer> prodMesNrClientes_Compras(String prod,int mes,int fil);
    Map<String,Integer> comprasCliente(String cliente);
    Set<String> nrClientesProd(String prod);
    List<String> top3Clientes();
    Set<String> clientesFilial(ICatalogoClientes cat, int x, int mes);
    Set<String> produtosDoCliente(ICliente cliente);
    double[] quantidadeCompradaDocliente (ICliente cliente, String prod);
}