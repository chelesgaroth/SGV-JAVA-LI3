package Controller;

import Model.IGestaoVendas;
import View.IView;

import java.util.List;

public interface IController {
    void setModel(IGestaoVendas model);
    void setView(IView view);
    void navegar(List<String> prods, List<Integer> res2, String mensagem, int q);
    void runController();
}
