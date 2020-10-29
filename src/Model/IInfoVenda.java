package Model;

public interface IInfoVenda {
    /**Set  */
    InfoVenda setInfoVenda(String venda);

    /** Getters */
    String getCliente();
    String getProduto();
    double getPreco();
    int getQuantidade();
    double getLucro();
    char getTipo();
    int getMes();
    int getFilial();

    /** toString */
    String toString();

}
