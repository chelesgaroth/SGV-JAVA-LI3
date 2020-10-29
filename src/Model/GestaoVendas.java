package Model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  Classe que agrega todas as estruturas do Model.
 */
public class GestaoVendas implements IGestaoVendas, Serializable {
    private ICatalogoClientes catCl;
    private ICatalogoProdutos catPd;
    private IFilial f1,f2,f3;
    private IFaturacao ft;
    private IDadosFicheiro dataF;
    private IDadosEstado dataE;

    /**
     *Construtor vazio
     */
    public GestaoVendas(){
        this.catCl = new CatalogoClientes();
        this.catPd = new CatalogoProdutos();
        this.f1 = new Filial();
        this.f2 = new Filial();
        this.f3 = new Filial();
        this.ft = new Faturacao();
        this.dataF = new DadosFicheiro();
        this.dataE = new DadosEstado();

    }

    /**
     *Construtor parameterizado
     */
    public GestaoVendas(CatalogoClientes cl, CatalogoProdutos pd, Filial f1, Filial f2, Filial f3 ,Faturacao ft, DadosFicheiro dataF,DadosEstado dataE){
        this.catCl = cl;
        this.catPd = pd;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.ft = ft;
        this.dataF = dataF;
        this.dataE = dataE;
    }

    /**
     *Construtor de cópia
     */
    public GestaoVendas(GestaoVendas sgv){
        this.catCl = sgv.getCatCl();
        this.catPd = sgv.getCatPd();
        this.f1 = sgv.getF1();
        this.f2 = sgv.getF2();
        this.f3 = sgv.getF3();
        this.ft = sgv.getFt();
        this.dataF = sgv.getDataF();
    }

    /**
     *Getters e Setters
     */
    public ICatalogoClientes getCatCl(){
        return this.catCl;
    }
    public ICatalogoProdutos getCatPd(){
        return this.catPd;
    }
    public IFilial getF1() { return f1; }
    public IFilial getF2() { return f2; }
    public IFilial getF3() { return f3; }
    public IFaturacao getFt() {
        return ft;
    }
    public IDadosFicheiro getDataF() { return this.dataF;}

    public IDadosEstado getDataE(){return this.dataE;}

    public void setF1(IFilial f1) { this.f1 = f1; }
    public void setF2(IFilial f2) { this.f2 = f2; }
    public void setF3(IFilial f3) { this.f3 = f3; }
    public void setCatCl(ICatalogoClientes cl){
        this.catCl = cl;
    }
    public void setCatPd(ICatalogoProdutos pd){
        this.catPd = pd;
    }
    public void setFt(IFaturacao fat){ this.ft = fat; }
    public void setDataF(IDadosFicheiro dataF){ this.dataF = dataF;}

    /**
     * Métodos de inseração de clientes e de produtos
     */
    public void insereCliente (ICliente cliente){
        this.catCl.addClient(cliente);
    }

    public void insereProduto (IProduto prod){
        this.catPd.addProduct(prod);
    }

    /**Resolução das queries estatísticas referentes aos ficheiros lidos*/
    public void filesData(){
        this.dataF.setProdsNComprados(query_1().size());
        this.dataF.setProdsComprados(ft.get_naoComprados(catPd,1).size());
        this.dataF.setFatTotal(ft.faturacaoGlobal(0,0));
        HashSet<String> compra = new HashSet<>();
        compra.addAll(f1.clientesFilial(this.catCl,1,0));
        compra.addAll(f2.clientesFilial(this.catCl,1,0));
        compra.addAll(f3.clientesFilial(this.catCl,1,0));
        this.dataF.setClientesNCompraram(compra.size());
        HashSet<String> notCompra = new HashSet<>();
        notCompra.addAll(f1.clientesFilial(this.catCl,0,0));
        notCompra.addAll(f2.clientesFilial(this.catCl,0,0));
        notCompra.addAll(f3.clientesFilial(this.catCl,0,0));
        this.dataF.setClientesCompraram(notCompra.size());
    }

    /**Resolução das queries estatísticas referentes aos estados lidos*/
    public void estadoData(){
        HashMap<Integer,Integer> mapa = new HashMap<>();
        HashMap<Integer,Double> mapa2 = new HashMap<>();
        HashMap<Integer,Double> fil1 = new HashMap<>();
        HashMap<Integer,Double> fil2 = new HashMap<>();
        HashMap<Integer,Double> fil3 = new HashMap<>();
        HashMap<Integer,List<Integer>> cls = new HashMap<>();
        int res;
        double res1;
        double resf1,resf2,resf3;
        int cl1, cl2, cl3;
        for(int i=1; i<13; i++){
            List<Integer> listaRes = new ArrayList<>();
            res = this.ft.comprasMes(i);
            res1 = ft.faturacaoGlobal(i,0);
            resf1 = ft.faturacaoGlobal(i,1);
            resf2 = ft.faturacaoGlobal(i,2);
            resf3 = ft.faturacaoGlobal(i,3);
            List<Integer> lista = f1.prodMesNrClientes_Compras(" ",i,1);
            cl1 = lista.get(0);
            List<Integer> lista1 = f2.prodMesNrClientes_Compras(" ",i,2);
            cl2 = lista1.get(0);
            List<Integer> lista2 = f3.prodMesNrClientes_Compras(" ",i,3);
            cl3 = lista2.get(0);

            mapa.put(i,res);
            mapa2.put(i,res1);
            fil1.put(i,resf1);
            fil2.put(i,resf2);
            fil3.put(i,resf3);

            listaRes.add(cl1);
            listaRes.add(cl2);
            listaRes.add(cl3);
            cls.put(i,listaRes);
        }
        this.dataE.setComprasTMes(mapa);
        this.dataE.setFatTotalG(mapa2);
        this.dataE.setFatFilial1(fil1);
        this.dataE.setFatFilial2(fil2);
        this.dataE.setFatFilial3(fil3);
        this.dataE.setClientMes(cls);
    }

    /** Lista dos produtos não comprados
     * Utiliza como auxiliar o método da classe faturação get_naoComprados.
     * No final faz sort da list para a ordenar.
     * @return List<String>
     */
    public List<String> query_1 (){
        Set<String> set = ft.get_naoComprados(catPd,0);
        List<String> lista = new ArrayList<String>(set);
        Collections.sort(lista);
        return lista;
    }

    /**
     * Para a query 2 recebe-se como input o mês pretendido.
     * Inicializa-se um array res1 para a filial 1, um array res2 para a filial 2 e um array res3 para a filial 3.
     * Percorre-se as árvores da filial 1 e soma-se os índices de 0 em cada iteração (nº de vendas) e os índices 1 (nº de clientes)
     * Os arrays res são preenchidos na auxiliar produtoMes da classe filial, para cada venda (tree) em análise.
     * No final, retorna-se no índice 0 e 1 os resultados da filial 1, no 2 e 3 ps da filial 2 e no 4 e 5 da filial 3.
     */
    public int[] query_2(int mes) {
        int [] res1 = new int[2];
        int nrvendas1 = 0,nrclientes1 =0;
        int [] res2 = new int[2];
        int nrvendas2 = 0,nrclientes2=0;
        int [] res3 = new int[2];
        int nrvendas3=0,nrclientes3=0;
        int  [] resposta = new int [6];
        for (Set<IInfoVenda> vendas : f1.getFilial().values()) {
            res1 = (f1.produtoMes(mes,vendas));
            nrvendas1+=res1[0];
            nrclientes1+=res1[1];
        }
        for (Set<IInfoVenda> vendas : f2.getFilial().values()) {
            res2=(f2.produtoMes(mes,vendas));
            nrvendas2+=res2[0];
            nrclientes2+=res2[1];

        }
        for (Set<IInfoVenda> vendas : f3.getFilial().values()) {
            res3 = (f3.produtoMes(mes,vendas));
            nrvendas3+=res3[0];
            nrclientes3+=res3[1];
        }

        resposta[0] = nrvendas1;
        resposta[1] = nrclientes1;
        resposta[2] = nrvendas2;
        resposta[3] = nrclientes2;
        resposta[4] = nrvendas3;
        resposta[5] = nrclientes3;


        return resposta;


    }


    /** Na query 3 recebe-se como input o cliente pretendido
     * Aloca-se no set meses os meses das filiais 1, 2 e 3, nos quais o cliente efetuou compras. Auxiliar getMesCLiente da classe Filial.
     * Percorre-se todos os meses que estão no set meses para ir buscar o nº de compras, o nº de produtos que comprou e o gasto que teve.
     * Esses campos são preenchidos nos arrays res1, res2 e res3 no método clienteCompras da classe Fiial.
     * O nº de compras é o nº de compras da filial 1 + o nº de vendas da filial 2 e da 3, assim como o nº de produtos e o gasto.
     * Adiciona-se no final da análise de um mês os campos list de Double para devolver a list no final de todos os meses como return.
     *
     */
    public List<Double> query_3(String key) {
        Set<Integer> meses = new HashSet<>();
        double [] res1 = new double [3];
        double [] res2 = new double [3];
        double [] res3 = new double [3];
        List<Double> resposta = new ArrayList<>();
        int ncompras=0, nprodutos=0;
        double gastos =0;
        meses =  f1.getMesCliente(key);
        Set<Integer> meses2 = f2.getMesCliente(key);
        Set<Integer> meses3 = f3.getMesCliente(key);
        meses.addAll(meses2);
        meses.addAll(meses3);
        int i =0;
        for (int mes: meses) {
            res1 = f1.clienteCompras(key,mes);
            res2 = f2.clienteCompras(key,mes);
            res3 = f3.clienteCompras(key,mes);
            ncompras = (int) res1[0] + (int) res2[0] + (int)res3 [0];
            nprodutos = (int) res1[1] + (int) res2[1] + (int)res3 [1];
            gastos = res1[2] + res2[2] + res3[2];
            resposta.add((double)mes);
            resposta.add((double)ncompras);
            resposta.add((double)nprodutos);
            resposta.add(gastos);

        }

        return resposta;
    }

    /**
     * Dado o código de um produto existente, determinar, mês a mês, quantas vezes foi
     * comprado, por quantos clientes diferentes e o total faturado.
     * Utiliza como auxiliar o método getFatMes da classe Faturação que devolve a faturação do produto no mês em análise (irá
     * percorrer os 12) e adiciona-se esse valor ao resLista.
     * De seguida, usa como auxiliar a função prodMesNrClientes_Compras para as 3 filiais, onde é alocado no índice 0
     * o nº de clientes que compraram o prod e no índice 1 o nº de vezes que o produto foi comprado.
     * Somando-se estes valores para cada filial obtém-se o total para o mês, ficando alocado em resLista (onde já estava alocado
     *  o total faturado ) o que foi retornado no índice 0 e de seguida o indice 1.
     *  Por fim, para cadda iteração dos 12  meses coloca-se no map res ( o que é retornado), o mês em análise como key e os
     *  valores referidos anteriormente como value.
     */
    public Map<Integer,List<Double>> query_4 (String keyProd){
        IProduto prod = new Produto();
        prod.setCodigoP(keyProd);
        Map<Integer,List<Double>> res = new HashMap<>();
        double faturacao = 0.0;
        for(int mes = 1;mes<=12; mes++){
            List<Double> resLista = new ArrayList<>();
            faturacao = ft.getFatMes(prod,mes,0);
            resLista.add(faturacao);
            List<Integer> resfil1 = new ArrayList<>();
            List<Integer> resfil2 = new ArrayList<>();
            List<Integer> resfil3 = new ArrayList<>();
            resfil1 = f1.prodMesNrClientes_Compras(prod.toString(),mes,0);
            resfil2 = f2.prodMesNrClientes_Compras(prod.toString(),mes,0);
            resfil3 = f3.prodMesNrClientes_Compras(prod.toString(),mes,0);
            resLista.add((double) (resfil1.get(0)+resfil2.get(0)+resfil3.get(0)));
            resLista.add((double) (resfil1.get(1)+resfil2.get(1)+resfil3.get(1)));
            res.put(mes,resLista);
        }
        return res;
    }


    /**
     * No map result fica alocado um Map com as compras todas do Cliente, da filial 1, 2 e 3.
     * Ou seja, fica nas keys o produto ( o código ) e no value a quantidade comprada pelo cliente
     *  Primeiro ordena-se este map pelas keys, para ficar ordenado alfabeticamente e, de seguida, pelo value, de forma
     *  decrescente, assim quando as quantidades são iguais, fica ordenado pela key.
     */
    public Map<String,Integer> query_5(String cliente) {

        List<String> res = new ArrayList<>();
        List<Integer> res2 = new ArrayList<>();
        Map<String,Integer> result = f1.comprasCliente(cliente);
        result.putAll(f2.comprasCliente(cliente));
        result.putAll(f3.comprasCliente(cliente));

        //Ordenar pela key

        Map<String, Integer> resultado = result.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        Map<String, Integer> resultado2 = resultado.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return resultado2;
    }


    /**
     * Dado um limite N, devolve-se o top N produtos mais vendidos.
     * Após recebermos no tempArray, um array já com os produtos organizados conforme a sua quantidade vendida de forma
     * descrescente. Fazemos um ciclo para ir buscar nesse arrays os produtos até ao indice N-1. Após isso com o código
     * do produto vamos buscar, a quantidade total adquirida desse produto e o nº de clientes distintos que compraram
     * esse produto
     * No return:
     * Key : Integer -> corresponde a um número de 0 a N-1, de forma a manter a ordenação, uma vez que os hashMaps não
     * mantêm a sua ordenação. E, sendo assim, podemos ir buscar de forma ordenada os top N produtos. Sendo que por exemplo,
     * na key 0 temos o produto mais comprado.
     */
    public Map<Integer,List<String>> query_6(int N){
        Map<Integer,List<String>> res = new HashMap<>();
        IProduto[] tempArray = this.ft.topNVendidos();
        for (int i=0; i<N; i++){
            int quantidade = ft.nrUnidVendidas(tempArray[i]);
            HashSet<String> clientes = new HashSet<>();
            ArrayList<String> value = new ArrayList<>();
            clientes.addAll(f1.nrClientesProd(tempArray[i].toString()));
            clientes.addAll(f2.nrClientesProd(tempArray[i].toString()));
            clientes.addAll(f3.nrClientesProd(tempArray[i].toString()));
            value.add(tempArray[i].toString()); //na posicao 0 temos o produto
            value.add(String.valueOf(quantidade)); // na posicao 1 temos a quantidade adquirida
            value.add(String.valueOf(clientes.size())); // na posicao 2 temos o nr de clientes
            res.put(i,value);
        }
        return res;
    }

    /**
     * Utiliza a função top3Clientes para as 3 filiais e concatena as 3 lists numa só, assim fica com os top 3 de cada filial
     * numa só List
     *
     */
    public List<String> query_7() {
        List<String> resultado = new ArrayList<>();
        resultado= f1.top3Clientes();
        List<String> resultadofinal = new ArrayList<>();
        resultadofinal.addAll(resultado);
        resultado = f2.top3Clientes();
        resultadofinal.addAll(resultado);
        resultado = f3.top3Clientes();
        resultadofinal.addAll(resultado);



        return resultadofinal;
    }


    /**
     * Vai buscar o set total dos clientes, ou seja os clientes que compraram na filial 1, 2 e 3.
     * Para cada cliente, determina a quantidade de produtos através do size resultante do set do método produtosDoCliente da key das
     * 3 filiais.
     * Coloca-se no map a string do código da key e quantidade que é a soma do size da filial 1, 2 e 3.
     * Ordena-se o map para se extrair o top N e aloca-se num novo map
     * Este map que que é dado como resultado , que apenas contém o top N é novamente ordenado pelas quantidade de produtos (value).
     *
     */
    public Map<String,Integer> query_8(int nrclientes){

        Set<ICliente> filial1 = f1.listaClientes();
        Set<ICliente> filial2 = f2.listaClientes();
        Set<ICliente> filial3 = f3.listaClientes();
        Set<ICliente> finalClientes = new HashSet<>();
        finalClientes.addAll(filial1);
        finalClientes.addAll(filial2);
        finalClientes.addAll(filial3);



        HashMap<String,Integer> resposta = new HashMap<>();
        for (ICliente s: finalClientes) {
            int quantidadeCliente = 0;
            quantidadeCliente+= f1.produtosDoCliente(s).size();
            quantidadeCliente+= f2.produtosDoCliente(s).size();
            quantidadeCliente+=f3.produtosDoCliente(s).size();
            resposta.put(s.toString(),quantidadeCliente);
        }

        Map<String, Integer> resultado2 = resposta.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        int i=0;
        /**
         * Extrai os primeiros N elementos do map
         */
        HashMap<String,Integer> respostaFinal = new HashMap<>();
        for (String s : resultado2.keySet()){
            if (i<nrclientes) {
                respostaFinal.put(s,resultado2.get(s));
            }
            else if (i>nrclientes) break;
            i++;
        }
        Map<String, Integer> resultado3 = respostaFinal.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return resultado3;
    }


    /**
     * No finalClientes fica os clientes todos que compraram o produto prod, da filial 1, 2 e 3.
     * 1º ordena alfabeticamente o Array do set (toArray)
     * 2o ordena pelas quantidades compradas do produto
     *Assim , conseguimos com que no caso em que as quantidades sejam iguais, os produtos estejam organizados por ordem
     * alfabética tal como pedido no enunciado.
     */
    public Map<Integer,List<String>> query_9(String prod, int N) {

        Set<String> finalClientes = new HashSet<>();
        finalClientes.addAll(f1.nrClientesProd(prod));
        finalClientes.addAll(f2.nrClientesProd(prod));
        finalClientes.addAll(f3.nrClientesProd(prod));

        HashMap<Integer, List<String>> map = new HashMap<>();
        String[] array = finalClientes.toArray(new String[finalClientes.size()]);
        Arrays.sort(array); //ordenar alfabeticamente 1º
        Arrays.sort(array, new Comparator<String>(){    //ordenar conforme as quantidades compradas do produto

            public int compare(String c1, String c2) {
                double resc1;
                double resc2;
                ICliente cliente = new Cliente();
                cliente.setCodigoC(c1);
                ICliente cliente2 = new Cliente();
                cliente2.setCodigoC(c2);
                double[] res1 = f1.quantidadeCompradaDocliente(cliente, prod);  //função que devolve a quantidade e o dinheiro gasto que o cliente cl
                double[] res2 = f2.quantidadeCompradaDocliente(cliente, prod);  // gastou no prod
                double[] res3 = f3.quantidadeCompradaDocliente(cliente, prod);
                double[] res4 = f1.quantidadeCompradaDocliente(cliente2, prod);  //função que devolve a quantidade e o dinheiro gasto que o cliente cl
                double[] res5 = f2.quantidadeCompradaDocliente(cliente2, prod);  // gastou no prod
                double[] res6 = f3.quantidadeCompradaDocliente(cliente2, prod);
                resc1 = res1[0] + res2[0] + res3[0];
                resc2 = res4[0] + res5[0] + res6[0];
                if(resc1>resc2) return -1;
                if(res1==res2) return 0;
                else return 1;
            }
        });

        for (int i = 0; i < N; i++) {
            ArrayList<String> list = new ArrayList<>();
            double[] res = new double[2];
            ICliente c = new Cliente();
            c.setCodigoC(array[i]);
            double[] res1 = f1.quantidadeCompradaDocliente(c, prod);  //função que devolve a quantidade e o dinheiro gasto que o cliente cl
            double[] res2 = f2.quantidadeCompradaDocliente(c, prod);  // gastou no prod
            double[] res3 = f3.quantidadeCompradaDocliente(c, prod);
            res[0] = res1[0] + res2[0] + res3[0];
            res[1] = res1[1] + res2[1] + res3[1];
            list.add(array[i]);
            list.add(String.valueOf(res[0]));
            list.add(String.valueOf(res[1]));
            map.put(i, list);
        }
        return map;
    }

    /**
     * Nesta query, para cada produto do catálogo vemos a faturação feita nos 12 meses com esse mesmo produto nas
     * 3 filiais.
     * @return:
     * Key: Código do Produto
     * Value: Uma lista que guarda as faturações de cada mês em cada Filial
     * Nas posições de [0,11] da lista, correspondem às faturações desse produto em cada mês na filial 1.
     * [12,23] , faturações de cada mês na filial 2.
     * [24,35] , faturações de cada mês na filial 3.
     */
    public Map<String,List<Double>> query_10(){
        Map<String,List<Double>> mapa = new HashMap<>();
        for(IProduto prod : this.catPd.getProdutos()) {
            List<Double> lista = new ArrayList<>();
            for (int i = 1; i < 13; i++) {           // lista.get[0,11] posicoes são para a filial 1
                lista.add(ft.getFatMes(prod,i,1));
            }
            for (int i = 1; i < 13; i++) {           // lista.get[12,23] posicoes são para a filial 2
                lista.add(ft.getFatMes(prod,i,2));
            }
            for (int i = 1; i < 13; i++) {           // lista.get[24,35] posicoes são para a filial 3
                lista.add(ft.getFatMes(prod,i,2));
            }
            mapa.put(prod.toString(),lista);
        }
        return mapa;
    }
}
