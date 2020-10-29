package TestesPerformance;

import Model.*;

import java.io.Serializable;
import java.util.*;

/**
 * Classe de Teste:
 * Implementa a mesma interface que a classe original (Filial.java). O que faz com que não seja preciso alterar qualquer
 * parâmetro de entrada ou de saída (return da função).
 * Nesta classe, em vez de usarmos HashMap optamos por TreeMap e em vez de TreeSet usamos HashSet.
 * Para funções relacionadas com a resolução de queries e que usavam a coleção List , optamos agora por usar Vector,
 * onde antes tinhamos usado ArrayList.
 */
public class FilialTeste implements IFilial, Serializable {
    private Map<ICliente, Set<IInfoVenda>> filial;

    /**Comparator para o TreeMap, organiza por ordem alfabética os clientes*/
    class The_Comparator implements Comparator<ICliente> {
        public int compare(ICliente c1, ICliente c2)
        {
            String first_Str;
            String second_Str;
            first_Str = c1.getCodigoC();
            second_Str = c2.getCodigoC();
            return second_Str.compareTo(first_Str);
        }
    }

    /**Construtores*/
    public FilialTeste() {
        this.filial = new TreeMap<>(new The_Comparator());
    }

    public FilialTeste(Map<ICliente, Set<IInfoVenda>> f) {
        this.filial = f;
    }

    /**Get e Set*/
    public Map<ICliente, Set<IInfoVenda>> getFilial() {
        return filial;
    }

    public void setFilial(Map<ICliente, Set<IInfoVenda>> filial) {
        this.filial = filial;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilialTeste filial1 = (FilialTeste) o;
        return Objects.equals(filial, filial1.filial);
    }

    public int hashCode() {
        return Objects.hash(filial);
    }


    public String toString() {
        return "Filial:" + "\n" +
                filial;
    }

    /**Adiciona uma venda à estrtura da Filial*/
    public void addVenda(ICliente keycl, IInfoVenda v) {

        Set<IInfoVenda> vendas = new HashSet<>();
        if (keycl != null) {
            vendas = filial.get(keycl);
        }
        if (vendas == null) {
            vendas = new HashSet<>();
        }
        vendas.add(v);
        filial.put(keycl, vendas);
    }

    /**Função que procura elemento*/
    public void getItem(String keyCl) {
        Set<IInfoVenda> vendas;
        vendas = filial.get(keyCl);

        for (IInfoVenda v : vendas) {
            System.out.println("Mes: " + v.getMes());

            if (v.getMes() == 3) {
                System.out.println("Encontrei o mes 3");
                System.out.println(v.getProduto());
            }
        }
    }

    /**Função que vê se os clientes do catalogo compraram na filial em questao*/
    public Set<String> clientesFilial (ICatalogoClientes cat, int x, int mes){
        Set<String> res = new TreeSet<>(); /**clientes que compraram na filial*/
        Set<String> res0 = new TreeSet<>(); /**clientes que não compraram na filial*/
        Set<String> res2 = new TreeSet<>(); /**clientes que compraram na filial de acordo com o mês*/
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

    /**Função que devolve  o número de vendas realizadas num mês e o número de clientes distintos que realizaram essas compras*/
    public int[] produtoMes(int mes, Set<IInfoVenda> tree) {
        Set<String> clientes= new TreeSet<>();
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


    /**Vai buscar o Value de uma dada key*/
    public Set<IInfoVenda> getTreeFil (ICliente key) {
        return this.filial.get(key);
    }

    /** Dado um cliente e um mês, obtemos o número de produtos, o número de compras e o total gasto pelo cliente nesse mesmi mês*/
    public double[] clienteCompras (String key, int mes){
        double ncompras= 0;
        double npreco = 0;
        double [] res = new double[3];
        ICliente cliente = new Cliente();
        cliente.setCodigoC(key);
        Set<IInfoVenda> tree = getTreeFil(cliente);
        Set<String> produtos= new TreeSet<>();
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

    /**Devolve os meses onde o cliente efetuou compras*/
    public Set<Integer> getMesCliente (String key) {
        Set<Integer> res = new TreeSet<>();ICliente cliente = new Cliente();
        cliente.setCodigoC(key);
        Set<IInfoVenda> tree = getTreeFil(cliente);
        for (IInfoVenda venda: tree) {
            res.add(venda.getMes());
        }
        return res;
    }

    /**Devolve o número de compras feitas num mês ,e o número de clientes distintos que fizeram essas mesmas.
     * Caso fil==0, vêmos quais foram as compras feitas tendo em conta o Produto prod.
     * Caso contrário, vemos a totalidade das compras nesse mês numa dada filial.
     */
    public List<Integer> prodMesNrClientes_Compras(String prod,int mes,int fil){
        Set<String> clientes = new HashSet<>();
        List<Integer> res = new Vector<>();
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

    /**função que para dado cliente devolve um Map de codProd comprado por ele e quantidade comprada*/
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

    /**lista de clientes que compraram um produto*/
    public Set<String> nrClientesProd (String prod){
        Set<String> clientes = new TreeSet<>();
        for (Set<IInfoVenda> tree : filial.values()) {
            for (IInfoVenda venda : tree) {
                if(venda.getProduto().equals(prod)) clientes.add(venda.getCliente());
            }
        }
        return clientes;
    }

    /**Devolve os top 3 compradores na Filial*/
    public List<String> top3Clientes (){
        String clienteMax=null;
        double faturacaoMax =0.0;
        double faturacaoAtual =0.0;
        String clienteAtual=null;
        double maxAnterior =0.0;
        int numero =0;
        int i=0;
        List<String> resultado = new Vector<>();
        Set<String> prods = new TreeSet<>();
        while(numero<3){
            Set<ICliente> keys = this.listaClientes();
            for (ICliente s:keys){
                Set<IInfoVenda> tree = getTreeFil(s);
                faturacaoAtual=0.0;
                for (IInfoVenda venda : tree) {
                    faturacaoAtual += venda.getQuantidade() * venda.getPreco();
                }
                if (((faturacaoAtual > faturacaoMax) && (faturacaoAtual<maxAnterior)) || ((faturacaoAtual>faturacaoMax) && (maxAnterior==0))) {
                    faturacaoMax = faturacaoAtual;
                    clienteMax = s.toString();
                }
            }
            resultado.add(clienteMax);
            int j=i+1;
            resultado.add(String.valueOf(faturacaoMax));
            maxAnterior = faturacaoMax;
            faturacaoMax=0.0;
            numero++;
        }
        return resultado;
    }

    /**Devolve a lista total de Clientes*/
    public Set<ICliente> listaClientes() {
        return filial.keySet();
    }

    /**função que devolve o set dos produtos comprados do cliente*/
    public Set<String> produtosDoCliente(ICliente cliente) {
        Set<String> produtos = new TreeSet<>();
        Set<IInfoVenda> tree = getTreeFil(cliente);
        for (IInfoVenda venda: tree) {
            produtos.add(venda.getProduto());
        }
        return produtos;
    }

    /**Devolve a quantidade que um cliente comprou um produto*/
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
