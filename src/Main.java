import Controller.Controller;
import Controller.IController;
import Model.*;
import TestesPerformance.*;
import View.*;

/**
 * Correr Projeto no Terminal:
 * java -jar libs/artifacts/projJava_jar/projJava.jar
 */
public class Main {
    public static void main(String[] args){

        IGestaoVendas model = new GestaoVendas();
        IView view = new View();
        IController controller = new Controller();

        controller.setModel(model);
        controller.setView(view);

        controller.runController();

        /*Testes de Performance*/
        TestesQ t = new TestesQ();
        t.setModel(model);
        t.queriesTeste();

        /*Model de teste, troca as classes da collection Map, do Set e do List*/
        /*
        IFilial f1 = new FilialTeste();
        IFilial f2 = new FilialTeste();
        IFilial f3 = new FilialTeste();
        IFaturacao ft = new FaturacaoTeste();
        ICatalogoProdutos catPd = new CatalogoProdutosTeste();
        ICatalogoClientes catCl = new CatalogoClientesTeste();

        model.setF1(f1);
        model.setF2(f2);
        model.setF3(f3);
        model.setFt(ft);
        model.setCatPd(catPd);
        model.setCatCl(catCl);

        controller.setModel(model);
        controller.runController();
        t.setModel(model);
        t.queriesTeste();
        */
    }
}
