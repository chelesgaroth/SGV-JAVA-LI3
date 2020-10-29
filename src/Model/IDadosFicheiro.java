package Model;

public interface IDadosFicheiro {

    void setNumVendasErradas(int numVendasErradas);
    void setTotalProds(int totalProds);
    void setProdsComprados(int prodsComprados);
    void setProdsNComprados(int prodsNComprados);
    void setNumClientes(int numClientes);
    void setClientesCompraram(int clientesCompraram);
    void setClientesNCompraram(int clientesNCompraram);
    void setComprasGratis(int comprasGratis);
    void setFatTotal(double fatTotal);

    int getNumVendasErradas();
    int getTotalProds();
    int getProdsComprados();
    int getProdsNComprados();
    int getNumClientes();
    int getClientesCompraram();
    int getClientesNCompraram();
    int getComprasGratis();
    double getFatTotal();

}
