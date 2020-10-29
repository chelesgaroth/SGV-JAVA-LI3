package Model;

import java.io.Serializable;

/**
 * Classe responsável pelas queries estatísticas referentes à leitura dos ficheiros
 */

public class DadosFicheiro implements IDadosFicheiro, Serializable {
    private int numVendasErradas;       /**número total de registos de venda errados*/
    private int totalProds;             /**número total de produtos*/
    private int prodsComprados;         /**total de diferentes produtos comprados*/
    private int prodsNComprados;        /**total de não comprados*/
    private int numClientes;            /**número total de clientes*/
    private int clientesCompraram;      /**total dos clientes que realizaram compras*/
    private int clientesNCompraram;     /**total de clientes que nada compraram*/
    private int comprasGratis;          /**total de compras de valor total igual a 0.0*/
    private double fatTotal;            /**faturação total*/


    public DadosFicheiro(){
        this.numVendasErradas = 0;
        this.totalProds = 0;
        this.prodsComprados = 0;
        this.prodsNComprados = 0;
        this.numClientes = 0;
        this.clientesCompraram = 0;
        this.clientesNCompraram = 0;
        this.comprasGratis = 0;
        this.fatTotal = 0;
    }


    public void setNumVendasErradas(int numVendasErradas) {
        this.numVendasErradas = numVendasErradas;
    }

    public void setTotalProds(int totalProds) {
        this.totalProds = totalProds;
    }

    public void setProdsComprados(int prodsComprados) {
        this.prodsComprados = prodsComprados;
    }

    public void setProdsNComprados(int prodsNComprados) {
        this.prodsNComprados = prodsNComprados;
    }

    public void setNumClientes(int numClientes) {
        this.numClientes = numClientes;
    }

    public void setClientesCompraram(int clientesCompraram) {
        this.clientesCompraram = clientesCompraram;
    }

    public void setClientesNCompraram(int clientesNCompraram) {
        this.clientesNCompraram = clientesNCompraram;
    }

    public void setComprasGratis(int comprasGratis) {
        this.comprasGratis = comprasGratis;
    }

    public void setFatTotal(double fatTotal) {
        this.fatTotal = fatTotal;
    }


    public int getNumVendasErradas() {
        return numVendasErradas;
    }

    public int getTotalProds() {
        return totalProds;
    }

    public int getProdsComprados() {
        return prodsComprados;
    }

    public int getProdsNComprados() {
        return prodsNComprados;
    }

    public int getNumClientes() {
        return numClientes;
    }

    public int getClientesCompraram() {
        return clientesCompraram;
    }

    public int getClientesNCompraram() {
        return clientesNCompraram;
    }

    public int getComprasGratis() {
        return comprasGratis;
    }

    public double getFatTotal() {
        return fatTotal;
    }
}
