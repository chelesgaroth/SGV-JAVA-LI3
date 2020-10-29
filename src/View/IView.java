package View;

import Model.IDadosEstado;
import Model.IDadosFicheiro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;  

public interface IView {
    /** Getters */
    INavegador getNavegador();
    ITabela getTabela();

    /** Apresentação de menus, opções e resultados */
    void  menu();
    void files(int x);
    void printMensagem(String mensagem);
    void ficheiros(int res, String fCl, String fPd, String fVd);
    void yesOrNo();
    void estatistica(IDadosFicheiro data,String file);
    void estatistica2(IDadosEstado data);

    void show_query_2(int[] res, int mes);
    void show_query_3(List<Double> res);
    void show_query4(Map<Integer, List<Double>> resposta, String prod);
    void showQuery6(Map<Integer, List<String>> res, int N);
    void show_query7(List<String> resultado);
    void show_query8 (Map<String,Integer> resultado);
    void show_query9(Map<Integer, List<String>> res, int N);
}
