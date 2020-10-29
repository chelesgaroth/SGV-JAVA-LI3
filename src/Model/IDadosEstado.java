package Model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface IDadosEstado {
    HashMap<Integer, Integer> getComprasTMes();
    HashMap<Integer,Double> getFatTotalG();
    HashMap<Integer,Double> getFatFilial1();
    HashMap<Integer,Double> getFatFilial2();
    HashMap<Integer,Double> getFatFilial3();
    HashMap<Integer, List<Integer>> getClientMes();
    void setComprasTMes(HashMap<Integer, Integer> comprasTMes);
    void setFatTotalG( HashMap<Integer,Double> fatTotalG);
    void setFatFilial1 (HashMap<Integer,Double> fatFilial1);
    void setFatFilial2( HashMap<Integer,Double> fatFilial2);
    void setFatFilial3( HashMap<Integer,Double> fatFilial3);
    void setClientMes(HashMap<Integer, List<Integer>> clientMes);
}
