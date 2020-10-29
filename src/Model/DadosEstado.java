package Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Classe responsável pelas queries estatísticas relacionadas com a leitura de um ficheiro .dat / load de um estado
 */

public class DadosEstado implements IDadosEstado, Serializable {
    HashMap<Integer,Integer> comprasTMes; /**Número total de compras por mês */
    HashMap<Integer,Double> fatTotalG ; /**Faturação total por mês (valor total das compras/vendas) para cada filial e o valor total global*/
    HashMap<Integer,Double> fatFilial1;
    HashMap<Integer,Double> fatFilial2;
    HashMap<Integer,Double> fatFilial3;
    HashMap<Integer, List<Integer>> clientMes;/**Número de distintos clientes que compraram em cada mês filial a filial*/

    public DadosEstado(){
        this.comprasTMes = new HashMap<>();
        this.fatTotalG = new HashMap<>();
        this.fatFilial1 = new HashMap<>();
        this.fatFilial2 = new HashMap<>();
        this.fatFilial3 = new HashMap<>();
        this.clientMes = new HashMap<>();
    }

    public HashMap<Integer, Integer> getComprasTMes() {
        return comprasTMes;
    }

    public HashMap<Integer,Double> getFatTotalG() {
        return fatTotalG;
    }

    public HashMap<Integer,Double> getFatFilial1() {
        return fatFilial1;
    }

    public HashMap<Integer,Double> getFatFilial2() {
        return fatFilial2;
    }

    public HashMap<Integer,Double> getFatFilial3() {
        return fatFilial3;
    }

    public HashMap<Integer, List<Integer>> getClientMes() {
        return clientMes;
    }

    public void setComprasTMes(HashMap<Integer, Integer>  comprasTMes) {
        this.comprasTMes = comprasTMes;
    }

    public void setFatTotalG(HashMap<Integer,Double> fatTotalG) {
        this.fatTotalG = fatTotalG;
    }

    public void setFatFilial1( HashMap<Integer,Double> fatFilial1) {
        this.fatFilial1 = fatFilial1;
    }

    public void setFatFilial2( HashMap<Integer,Double> fatFilial2) {
        this.fatFilial2 = fatFilial2;
    }

    public void setFatFilial3( HashMap<Integer,Double> fatFilial3) {
        this.fatFilial3 = fatFilial3;
    }

    public void setClientMes(HashMap<Integer, List<Integer>> clientMes) {
        this.clientMes = clientMes;
    }
}
