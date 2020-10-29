package Model;

import java.io.Serializable;

/**
 * Classe responsável por definir as variáveis que cada InfoVenda vai alocar.
 */
public class InfoVenda implements IInfoVenda, Serializable {
    private String cliente;
    private String produto;
    private double preco;
    private int quantidade;
    private char tipo;
    private int mes;
    private int filial;

    /**
     * Construtor vazio
     */
    public InfoVenda(){
        this.cliente = null;
        this.produto = null;
        this.preco = 0.0;
        this.quantidade = 0;
        this.tipo = ' ';
        this.mes = 0;
        this.filial = 0;
    }

    /**
     * Construtor parametrizado.
     */
    public InfoVenda ( String cliente, String produto, double preco, int quantidade, char tipo, int mes, int filial){
        this.cliente= cliente;
        this.produto = produto;
        this.preco = preco;
        this.quantidade = quantidade;
        this.tipo=tipo;
        this.mes = mes;
        this.filial=filial;
    }

    /**
     * Set de uma InfoVenda através de uma linha de compras.
     * Divide-se a linha por espaços e aloca-se nas variáveis respetivas a cada campo.
     */
     public InfoVenda setInfoVenda(String venda) {
        InfoVenda v = new InfoVenda();
        String[] vendaSplit = venda.split(" ");
        this.cliente = vendaSplit[4];
        this.produto = vendaSplit[0];
        this.preco = Double.parseDouble(vendaSplit[1]);
        this.quantidade = Integer.parseInt(vendaSplit[2]);
        this.tipo = vendaSplit[3].charAt(0);
        this.mes = Integer.parseInt(vendaSplit[5]);
        this.filial = Integer.parseInt(vendaSplit[6]);
        return this;
    }

    /** Getters */
    public String getCliente() {
        return cliente;
    }

    public String getProduto() {
        return produto;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getLucro(){ return quantidade*preco;}

    public char getTipo() {
        return tipo;
    }

    public int getMes() {
        return mes;
    }

    public int getFilial() {
        return filial;
    }

    /**
     * toString
     */
    public String toString() {
        return "\nInfoVenda{" +
                "\ncliente='" + cliente + '\'' +
                "\nproduto='" + produto + '\'' +
                "\npreco=" + preco +
                "\nquantidade=" + quantidade +
                "\ntipo=" + tipo +
                "\nmes=" + mes +
                "\nfilial=" + filial + "\n" +'}'+
                "\n\n";
    }
}