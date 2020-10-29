package Controller;

import Model.*;
import TestesPerformance.Crono;
import View.IView;

import java.io.IOException;
import java.util.*;

/**
 * Classe responsável pela leitura da opção do utilizador e dos inputs que pretende para cada query.
 * Invoca as queries definidas no GestaoVendas e no final os métodos definidos na view para apresentar os resultados
 */
public class Controller implements IController {
    private Crono crono;
    private IGestaoVendas model;
    private IView view;
    private IReadFile lerFiles;
    private String fCl;
    private String fPd;
    private String fVd;
    private int opcao;
    private IRWEstado rw;

    /**
     * Construtor vazio
     */
    public Controller(){
        this.fCl = " ";
        this.fPd = " ";
        this.fVd = " ";
        this.opcao = 0;
        this.lerFiles = new ReadFile();
        this.crono = new Crono();
        this.rw = new RWEstado();
    }

    /**
     * Sets
     */
    public void setModel(IGestaoVendas model) {
        this.model = model;
    }

    public void setView(IView view) {
        this.view = view;
    }

    /** Método auxiliar ao navegador.
     *  faz do while, enquanto o utilizador não selecionar o M de voltar ao menu.
     *  o inteiro q é utilizado para sabermos qual é a query que está em questão.
     *  Os cases funcionam para avaliar o pedido do utilizador para as diferentes alternativas que o navegador apresenta, e chama
     *  os métodos que efetuam os pedidos.
     */
    public void navegar(List<String> prods,List<Integer> res2, String mensagem, int q){
        String x = " ";
        int num = 0;
        Scanner ler;
        view.getNavegador().divide(prods,res2,mensagem,q);
        do {
            view.getNavegador().menu();
            ler = new Scanner(System.in);
            x = ler.nextLine();
            switch (x) {
                case "P":
                    view.getNavegador().proxima(prods, res2, mensagem,q);
                    break;
                case "A":
                    view.getNavegador().anterior(prods,res2, mensagem,q);
                    break;
                case "N":
                    view.printMensagem("Insira o nº da Página:");
                    ler = new Scanner(System.in);
                    num = ler.nextInt();
                    view.getNavegador().escolha(prods,res2, mensagem, q ,num);
                    break;
                case "T":
                    view.getNavegador().total(prods);
                    break;
                default:
                    if(!(x.equals("M")))view.printMensagem("\033[1;31mPor favor insira uma opção válida!" + "\033[0m");
                    break;
            }
        }while(!(x.equals("M")));
    }


    /**
     * Método que faz scanner do que o utilizador introduz para a opção de query que o utilizador pretende
     * Faz do while enquanto o utilizador não introduz a opção 15 (Sair) para apresentar o menu.
     * Quando o utilizador coloca a opção, entra num case e pede novos dados ao utilizador e chama as funções do GestaoVendas para as queries.
     * No final, chama os métodos da view para apresentar os resultados.
     */
    public void runController() {
        do {
            view.menu();
            Scanner ler = new Scanner(System.in);
            opcao = ler.nextInt();
            switch (opcao) {

                case 0: {
                    view.files(0);
                    ler = new Scanner(System.in);
                    opcao = ler.nextInt();
                    if (opcao == 1) {
                        fPd = "Produtos.txt";
                        fCl = "Clientes.txt";
                        fVd = "Vendas_1M.txt";
                    } else if (opcao == 2) {
                        view.files(1);
                        fPd = ler.next();
                        view.files(2);
                        fCl = ler.next();
                        view.files(3);
                        fVd = ler.next();
                    }
                    view.printMensagem("Loading ...");
                    //System.out.println(System.getProperty("user.dir"));
                    crono.start();
                    int res = lerFiles.readFile(fCl,fPd,fVd,model);
                    System.out.println(crono.getTImeString());
                    model.filesData();
                    view.ficheiros(res,fCl,fPd,fVd);
                    break;
                }
                case 1:{
                    List<String> prods = model.query_1();
                    System.out.println(crono.getTImeString());
                    navegar(prods,null,"Lista dos produtos nunca comprados",1);
                    break;
                }
                case 2:{
                    view.printMensagem("Insira o mês");
                    ler = new Scanner(System.in);
                    int mes = ler.nextInt();
                    while((mes<=0)||(mes>12)){
                        view.printMensagem("\033[1;31m" + "Insira novamente!!" + "\033[0m");
                        ler = new Scanner(System.in);
                        mes = ler.nextInt();
                    }
                    int[] resposta= new int[6];
                    resposta = model.query_2(mes);
                    view.show_query_2(resposta,mes);
                    break;
                }
                case 3: {
                    List<Double> resposta;
                    view.printMensagem("Insira o código de cliente");
                    ler = new Scanner(System.in);
                    String cliente = ler.nextLine();
                    ICliente c = new Cliente();
                    c.setCodigoC(cliente);
                    while(!(model.getCatCl().existsClient(c))){
                        view.printMensagem("\033[1;31mCódigo de Cliente inválido!!" + "\033[0m");
                        ler = new Scanner(System.in);
                        cliente = ler.nextLine();
                        c = new Cliente();
                        c.setCodigoC(cliente);
                    }
                    resposta = model.query_3(cliente);
                    view.show_query_3(resposta);
                    break;

                }
                case 4:{
                    view.printMensagem("Insira o código do produto:");
                    ler = new Scanner(System.in);
                    String prod = ler.nextLine();
                    IProduto produto = new Produto();
                    produto.setCodigoP(prod);
                    while(!(model.getCatPd().existsProduct(produto))){
                        view.printMensagem("\033[1;31mCódigo de Produto inválido!!" + "\033[0m");
                        ler = new Scanner(System.in);
                        prod = ler.nextLine();
                        produto = new Produto();
                        produto.setCodigoP(prod);
                    }
                    Map<Integer,List<Double>> resposta = model.query_4(prod);
                    view.show_query4(resposta,prod);
                    break;
                }

                case 5: {
                    view.printMensagem("Insira o código do cliente:");
                    ler = new Scanner(System.in);
                    String cliente = ler.nextLine();
                    ICliente c = new Cliente();
                    c.setCodigoC(cliente);
                    while(!(model.getCatCl().existsClient(c))){
                        view.printMensagem("\033[1;31mCódigo de Cliente inválido!!" + "\033[0m");
                        ler = new Scanner(System.in);
                        cliente = ler.nextLine();
                        c = new Cliente();
                        c.setCodigoC(cliente);
                    }
                    Map<String,Integer> res = model.query_5(cliente);

                    List<String> list = new ArrayList<String>(res.keySet());
                    List<Integer> values = new ArrayList<Integer>(res.values());
                    navegar(list,values,"Lista dos produtos mais comprados",4);
                    break;
                }

                case 6: {
                    view.printMensagem("Insira o número de produtos:");
                    ler = new Scanner(System.in);
                    int N = ler.nextInt();
                    crono.start();
                    Map<Integer,List<String>> res = model.query_6(N);
                    view.showQuery6(res, N);
                    break;
                }
                case 7:{
                    crono.start();
                    List<String> res = model.query_7();
                    view.show_query7(res);
                    break;
                }
                case 8: {
                    view.printMensagem("Insira o nº de clientes");
                    ler = new Scanner(System.in);
                    int N = ler.nextInt();
                    crono.start();
                    Map<String,Integer> res = model.query_8(N);
                    if(N<20) view.show_query8(res);
                    else {
                        List<String> list = new ArrayList<String>(res.keySet());
                        List<Integer> values = new ArrayList<Integer>(res.values());
                        navegar(list,values,"Lista do top clientes que compraram + produtos distintos",4);}
                    break;
                }

                case 9:{
                    view.printMensagem("Insira o código do produto:");
                    ler = new Scanner(System.in);
                    String cod = ler.nextLine();
                    IProduto produto = new Produto();
                    produto.setCodigoP(cod);
                    while(!(model.getCatPd().existsProduct(produto))){
                        view.printMensagem("\033[1;31mCódigo de Produto inválido!!" + "\033[0m");
                        ler = new Scanner(System.in);
                        cod = ler.nextLine();
                        produto = new Produto();
                        produto.setCodigoP(cod);
                    }
                    view.printMensagem("Insira o nº limite:");
                    ler = new Scanner(System.in);
                    int N = ler.nextInt();
                    crono.start();
                    Map<Integer,List<String>> map = model.query_9(cod,N);
                    view.show_query9(map,N);
                    break;
                }
                case 10: {
                    crono.start();
                    Map<String, List<Double>> res = model.query_10();
                    List<String> prods = new ArrayList<String>(res.keySet());
                    int i=0;
                    String x;
                    List<Double> valor = res.get(prods.get(i));
                    view.getTabela().query10();
                    view.getTabela().navegadorTable(prods.get(i), valor, i,prods.size());
                    do {
                        ler = new Scanner(System.in);
                        x = ler.nextLine();
                        switch (x) {
                            case "P":
                                view.getTabela().query10();
                                i++;
                                view.getTabela().navegadorTable(prods.get(i), valor, i,prods.size());
                                break;
                            case "A":
                                view.getTabela().query10();
                                i--;
                                view.getTabela().navegadorTable(prods.get(i), valor, i,prods.size());
                                break;
                            case "N":
                                view.printMensagem("Insira o nº da Página:");
                                ler = new Scanner(System.in);
                                i = ler.nextInt();
                                while ((i<0) || (i>=prods.size())){
                                    view.printMensagem("\033[1;31mPor favor insira uma página válida!" + "\033[0m");
                                    ler = new Scanner(System.in);
                                    i = ler.nextInt();
                                }
                                view.getTabela().query10();
                                view.getTabela().navegadorTable(prods.get(i), valor, i,prods.size());
                                break;
                            case "T":
                                view.printMensagem(String.valueOf(prods.size()));
                                break;
                            default:
                                if(!(x.equals("M"))) view.printMensagem("\033[1;31mPor favor insira uma opção válida!" + "\033[0m");
                                break;
                        }
                    }while(!(x.equals("M")) && (i<prods.size()));
                    break;
                }
                case 11:{
                    crono.start();
                    view.estatistica(model.getDataF(),fVd);
                    break;
                }
                case 12:{
                    crono.start();
                    model.estadoData();
                    view.estatistica2(model.getDataE());
                    break;
                }
                case 13: {
                    ler = new Scanner(System.in);
                    view.printMensagem("Gerar um ficheiro gestVendas.dat?");
                    view.yesOrNo();
                    String op = ler.nextLine();
                    if(op.equals("N")){
                        view.printMensagem("Dê um nome ao ficheiro:");
                        String f = ler.nextLine();
                        rw.setFileOut(f);
                    }
                    crono.start();
                    try {
                        view.printMensagem("Saving ...");
                        rw.saveData(this.model);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 14: {
                    ler = new Scanner(System.in);
                    view.printMensagem("Ler um ficheiro gestVendas.dat?");
                    view.yesOrNo();
                    String op = ler.nextLine();
                    if(op.equals("N")){
                        view.printMensagem("Indique o nome do ficheiro:");
                        String f = ler.nextLine();
                        rw.setFileIn(f);
                    }
                    crono.start();
                    try {
                        view.printMensagem("Loading ...");
                        setModel(rw.loadData());
                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 15: break;
                default: view.printMensagem("Insira novamente a opção!");
            }
            System.out.println(crono.getTImeString());
        }while(opcao!=15);
    }
}
