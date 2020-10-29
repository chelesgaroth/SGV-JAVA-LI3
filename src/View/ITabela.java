package View;

import Model.IDadosEstado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ITabela {
    void setTitulo(String titulo);
    void setSubtitulos(List<String> subtitulos);
    void setColunas(int colunas);
    void setRes(List<Double> res);

    void printaTitulo();
    void printaSubtitulos(int x);
    void printaLinha(int x,String add);
    void printaLinhaQuery4(Map<Integer, List<Double>> resposta);
    void printaLinhaQuery6(Map<Integer,List<String>> res, int N);

    void nrCompras(HashMap<Integer,Integer> comprasTMes);
    void faturacao(HashMap<Integer,Double> fatTotalG, HashMap<Integer,Double> fatFilial1,HashMap<Integer,Double> fatFilial2,HashMap<Integer,Double> fatFilial3);
    void clientes(HashMap<Integer, List<Integer>> clientMes);

    void query10();
    void navegadorTable( String prod, List<Double> res, int pagina , int nTPag);
}
