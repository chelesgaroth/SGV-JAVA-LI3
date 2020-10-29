package Model;

import java.io.Serializable;
import java.util.*;

/**
 * Classe que organiza as compras num map<Cliente, Set<InfoVenda>> de forma a associar um  cliente às suas vendas.
 */
public class Filial implements IFilial, Serializable {
    private Map<ICliente, Set<IInfoVenda>> filial;

    /**
     *Construtores
     */
    public Filial() {
        this.filial = new HashMap();
    }

    public Filial(Map<ICliente, Set<IInfoVenda>> f) {
        this.filial = f;
    }

    /**
     *Get e Set
     */
    public Map<ICliente, Set<IInfoVenda>> getFilial() {
        return filial;
    }

    public void setFilial(Map<ICliente, Set<IInfoVenda>> filial) {
        this.filial = filial;
    }

    /**
     * Equals
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filial filial1 = (Filial) o;
        return Objects.equals(filial, filial1.filial);
    }

    /**
     * HashCode
     */
    public int hashCode() {
        return Objects.hash(filial);
    }


    /**
     * toString
     */
    public String toString() {
        return "Filial:" + "\n" +
                filial;
    }

    /**
     * Função que adiciona a venda, no HashMap.
     * Inicializa o treeSet, se a key não for null, vai buscar a tree dessa key, adiciona a infoVenda e aloca-se no Map.
     */
    public void addVenda(ICliente cliente, IInfoVenda v) {
        Set<IInfoVenda> vendas = new TreeSet<>(new Comparable());

        if (cliente != null) {
            vendas = filial.get(cliente);
        }
        if (vendas == null) {
            vendas = new TreeSet<>(new Comparable());
        }
        vendas.add(v);
        filial.put(cliente, vendas);
    }

    /**
     *Função que vê se os clientes do catalogo compraram na filial em questao
     * Percorre cada cliente do catálogo e vai buscar a árvore correspondente a esse cliente.
     * Se o cliente tiver uma árvore associada, então adiciona o cliente ao set res, senão ao set res0.
     * Caso o cliente tiver comprado no mês do input, então adiciona o mesmo ao set res2.
     * Conforme o int x, devolve-se um dos sets.
     */
    public Set<String> clientesFilial (ICatalogoClientes cat, int x, int mes){
        Set<String> res = new HashSet<>(); //clientes que compraram na filial
        Set<String> res0 = new HashSet<>(); //clientes que não compraram na filial
        Set<String> res2 = new HashSet<>(); //clientes que compraram na filial de acordo com o mês
        for(ICliente cliente : cat.getClientes()){
            Set<IInfoVenda> tree;
            tree = filial.get(cliente);
            if(tree!=null) {
                for (IInfoVenda venda : tree) {
                    res.add(cliente.toString());
                    if(venda.getMes()==mes) res2.add(cliente.toString());
                }
            }
            else res0.add(cliente.toString());
        }
        if(x==0) return res0;
        else if (x==1) return res;
        else return res2;
    }

    /** Função axiliar da query 2, se o mês da infovenda em analise for igual ao mês pedido
     * adiciona-se ao set de códigos de clientes o cliente dessa InfoVenda e aumenta-se o número de vendas
     * Vemos o tamanho do set clientes para saber o número de clientes distintos.
     *
     * Devolve-se no indice 0 o número de vendas e no índice 1 o nº de clientes distintos.
     */
    public int[] produtoMes(int mes, Set<IInfoVenda> tree) {
        Set<String> clientes= new HashSet<>();
        int [] res = new int [2];
        int contador = 0;

        for (IInfoVenda venda :  tree) {
            if (venda.getMes() == mes) {
                clientes.add(venda.getCliente());
                contador++;
            }
            else if (venda.getMes()>mes) break;
        }
        res[0] = contador;
        res[1] = clientes.size();


        return res;
    }

    /**
     * Devolve o set (value) correspondente à key
     */
    public Set<IInfoVenda> getTreeFil (ICliente key) {
        return this.filial.get(key);
    }


    /**
     * Função usada na query 3, dada a key, percorre-se a Tree dessa key, se o mês da InfoVenda em análise for igual ao mês
     * do input adiciona-se o produto ao set (para no final percorrer o mesmo e ser possível saber o nº de produtos diferentes comprados)
     * aumenta-se o número de compras e o gasto aumenta-se para o a quantidade comprada multiplicada pelo preço do artigo.
     * Como a tree está organizada pelos meses, caso o mès da info venda seja maior que o mês de análise termina-se o ciclo.
     * No final do ciclo , devolve um array de doubles, no qual o índice 0 é o nº de compras, o 1 é o nº de produtos e o 2 é o total gasto.
     */
    public double[] clienteCompras (String key, int mes){
        double ncompras= 0;
        double npreco = 0;
        double [] res = new double[3];
        ICliente cliente = new Cliente();
        cliente.setCodigoC(key);
        Set<IInfoVenda> tree = getTreeFil(cliente);
        Set<String> produtos= new HashSet<>();
        for (IInfoVenda venda: tree) {
            if(venda.getMes()==mes) {
                produtos.add(venda.getProduto());
                ncompras++;
                npreco+=venda.getQuantidade() * venda.getPreco();
            } else if (venda.getMes()>mes) break;
        }
        res[0] = (double) ncompras;
        res[1] = (double) produtos.size();
        res[2] = npreco;
        return res;
    }

    /**
     * Método que devolve os meses onde o cliente efetou compras.
     * Para isso, vai buscar a árvore correspondente a esse cliente (getTreeFil).
     * De seguida, percorre cada IInfoVenda dessa mesma árvore e adiciona ao set de meses (res) o mesmo da IInfoVenda.
     */
    public Set<Integer> getMesCliente (String key) {
        Set<Integer> res = new HashSet<>();
        ICliente cliente = new Cliente();
        cliente.setCodigoC(key);
        Set<IInfoVenda> tree = getTreeFil(cliente);
        for (IInfoVenda venda: tree) {
            res.add(venda.getMes());
        }
        return res;
    }

    /**
     *  Método que vai buscar os clientes que compraram um produto num dado mês.
     *  Como o set está organizado de forma crescente de meses, caso o mês em análise seja superior ao dado como input
     *  faz-se break do ciclo.
     *  Se no input fil estiver um valor diferente de 0, então apenas queremos devolver o número total de compras feitas e o nº
     *  de clientes distintos que fizeram essas mesmas compras, independentemente do produto (Usado para resolver as queries de estatstica).
     *  No res, que é o return da função fica, portanto, no indíce 0 o nº de clientes que foram alocados, e no 1 o nº de vezes que
     *  o produto foi comprado.
     */
    public List<Integer> prodMesNrClientes_Compras(String prod,int mes,int fil){
        Set<String> clientes = new HashSet<>();
        List<Integer> res = new ArrayList<>();
        int num=0;
        for (Set<IInfoVenda> tree : filial.values()) {
            for (IInfoVenda venda : tree) {
                if(fil==0) {
                    if (venda.getMes() == mes) {
                        if (venda.getProduto().equals(prod)) {
                            clientes.add(venda.getCliente());
                            num++;
                        }
                    } else if (venda.getMes() > mes) break;
                }
                else{
                    if ((venda.getMes() == mes)&&(venda.getFilial()!=0)){
                        clientes.add(venda.getCliente());
                        num++;
                    }
                }
            }
        }
        res.add(clientes.size()); //Na posicao 0 do array leva o nr de clientes diferentes que compraram o produto
        res.add(num); //Na posicao 1 leva o numero de vezes que o produto foi comprado
        return res;
    }

    /**
     * Função que para um dado cliente, devolve um Map de codígos de produtos (key) por ele comprados, associada à
     * quantidade adiquirida (value)
     */
    public Map<String,Integer> comprasCliente (String cliente) {
        Map <String,Integer> res = new HashMap<>();
        ICliente cliente2 = new Cliente();
        cliente2.setCodigoC(cliente);
        Set<IInfoVenda> tree = getTreeFil(cliente2);
        for (IInfoVenda venda: tree) {
            res.put(venda.getProduto(),venda.getQuantidade());
        }
        return res;
    }

    /**
     * Função que devolve a lista de clientes que compraram um produto. Para isso, verifica para todos os clientes, todas as IInfoVendas
     * do mesmo e compara o produto de cada IInfoVenda com o dado como input.
     */
    public Set<String> nrClientesProd (String prod){
        Set<String> clientes = new HashSet<>();
        for (Set<IInfoVenda> tree : filial.values()) {
            for (IInfoVenda venda : tree) {
                if(venda.getProduto().equals(prod)) clientes.add(venda.getCliente());
            }
        }
        return clientes;
    }

    /**
     Para cada cliente calcula a faturaação do mesmo, percorrendo todas as suas infovendas e somando.
     No final de percorrer todos os clientes, fica alocado o maximo dessa iteração, neste caso até conter o top 3 clientes, portanto irá
     fazer 3 vezes o ciclo para percorrer todos os clientes.
     */
    public List<String> top3Clientes (){
        String clienteMax=null;
        double faturacaoMax =0.0;
        double faturacaoAtual =0.0;
        String clienteAtual=null;
        double maxAnterior =0.0;
        int numero =0;
        int i=0;
        List<String> resultado = new ArrayList<>();
        Set<String> prods = new HashSet<>();
        while(numero<3){
            Set<ICliente> keys = this.listaClientes();
            for (ICliente s:keys){
                Set<IInfoVenda> tree = getTreeFil(s);
                faturacaoAtual=0.0;
                /**
                 *Percorre as vendas do cliente e calcula o total faturado
                 */
                for (IInfoVenda venda : tree) {
                    faturacaoAtual += venda.getQuantidade() * venda.getPreco();

                }
                /**
                 * verificaa se a faturação desse cliente é o maior de todos os clientes e menor que o anterior calculado.
                 */
                if (((faturacaoAtual > faturacaoMax) && (faturacaoAtual<maxAnterior)) || ((faturacaoAtual>faturacaoMax) && (maxAnterior==0))) {
                    faturacaoMax = faturacaoAtual;
                    clienteMax = s.getCodigoC();
                }
                /**
                 * termina o ciclo e aloca o maior cliente dessa iteração
                 */
            }

            resultado.add(clienteMax);
            int j=i+1;
            resultado.add(String.valueOf(faturacaoMax));

            /**
             *aloca no maxAnterior a faturação máxima
             */
            maxAnterior = faturacaoMax;
            /**
             * coloca na faturacaoMax 0 que é para voltar a procurar o máximo
             */
            faturacaoMax=0.0;
            numero++;
        }
        return resultado;
    }

    /**
     * Função que devolve a lista de clientes, ou seja as keys do map filial
     */
    public Set<ICliente> listaClientes() {
        return filial.keySet();
    }

    /**
     *função que devolve o set dos produtos comprados do cliente
     */
    public Set<String> produtosDoCliente(ICliente cliente) {
        Set<String> produtos = new HashSet<>();
        Set<IInfoVenda> tree = getTreeFil(cliente);

        for (IInfoVenda venda: tree) {
            produtos.add(venda.getProduto());
        }
        return produtos;
    }

    /**
     Devolve a quantidade que um cliente comprou um produto e o valor gasto num array de doubles.
     Índice 0 - quantidade , 1- gastos.
     */
    public double[] quantidadeCompradaDocliente (ICliente cliente, String prod) {
        Set<IInfoVenda> tree = getTreeFil(cliente);
        double quantidade =0.0;
        double valorGasto = 0.0;
        double [] res = new double [2];
        for (IInfoVenda venda: tree) {
            if(venda.getProduto().equals(prod)) {
                quantidade+=venda.getQuantidade();
                valorGasto+= venda.getQuantidade()* venda.getPreco();
            }
        }
        res[0] = quantidade;
        res[1] = valorGasto;
        return res;
    }
}
