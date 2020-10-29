package Model;

/**
 * Classe responsável pela validação das vendas
 */
public class VendasValidas implements IVendasValidas {

/**
 * Função que pega na linha da venda e divide a mesma para validar os campos, através do métodos definidos para cada campo.
 * Se o int total for 0 então a linha de venda é válida.
 */
    public int divideLinha(String token, ICatalogoClientes catCl, ICatalogoProdutos catPd) {
        int res1=0, res2=0, res3=0, res4=0, res5=0, res6=0, res7=0, total =0;
        int n = 1;
        for (String val : token.split(" ")){
            if (n==1) res1 =validaProduto(val,catPd);
            if (n==2) res2 =validaPreco(val);
            if (n==3) res3= validaUnidades(val);
            if (n==4) res4= validaTipo(val);
            if (n==5) res5= validaCliente(val,catCl);
            if (n==6) res6=validaMes (val);
            if (n==7) res7=validaFilial (val);
            n++;
        }
        total = res1 + res2 + res3 + res4 + res5 + res6+ res7;
        return total;
    }

    /** Função que verifica se o produto existe no catálogo de produtos.
     * 0- válido
     * 1- inválido
     */
    public static int validaProduto(String token,ICatalogoProdutos catPd){
        Produto produto = new Produto();
        produto.setCodigoP(token);
        if(catPd.existsProduct(produto)){
            return 0;
        }
        return 1;
    }

    /** Função que valida se o produto existe no catálogo de clientes
     *  0- válido
     *  1- inválido
     */
    public static int validaCliente(String token, ICatalogoClientes catCl){
        Cliente cliente = new Cliente();
        cliente.setCodigoC(token);
        if(catCl.existsClient(cliente)){
            return  0;
        }
        return 1;
    }

    /** Função que verifica se o preço está entre 0 e 999.99
     *  0- válido
     *  1- inválido
     */
    public static int validaPreco(String preco){

        double p = Double.parseDouble(preco);
        if ((p>=0.0) && (p<=999.99)){

            return 0;
        }
        return 1;
    }

    /** Função que valida as unidades, são validas se estiverem entre 1 e 200
     *  0- válido
     *  1- inválidp
     */
    public static int validaUnidades(String unid){
        int u = Integer.parseInt(unid);
        if ((u>=1) && (u<=200)) {
            return 0;
        }
        else {
            System.out.println("UNID NAO VALIDO "+ u);
        }
        return 1;
    }

    /**
     * Função que verifica se o tipo é válido, ou seja se é N ou P
     * 0- válido
     * 1 - inválido
     */
    public static int validaTipo(String tipo){
        String normal = "N";
        String promo = "P";
        if ((tipo.equals(normal.intern())) || (tipo.equals(promo.intern()))){
            return 0;
        }
        return 1;
    }

    /**
     *  Função que valida o mês, ou seja se está entre 1 e 12
     *  0- válido
     *  1-inválido
     */
    public static int validaMes(String mes){
        int m = Integer.parseInt(mes);
        if ((m>=1) && (m<=12)){
            return 0;
        }
        return 1;
    }

    /**
     * Função que verifica se a filial é válida, ou seja se é 1, 2 ou 3
     * 0 - válido
     * 1- inválido
     */
    public static int validaFilial(String fil){
        int f = Integer.parseInt(fil);
        if((f==1) ||(f==2)|| (f==3)) {
            return 0;
        }
        return 1;
    }
}
