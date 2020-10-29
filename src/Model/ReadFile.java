package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Classe responsável pela leitura dos ficheiros ( Clientes, Produtos e Vendas)
 */
public class ReadFile implements IReadFile {
    private IVendasValidas validar;

    public ReadFile()  {
        this.validar = new VendasValidas();
    }

    /**
     * Realizam-se 3 ciclos para ser possível percorrer os 3 ficheiros em questão, o primeiro try catch destina-se
     * à leitura do ficheiro cliente e para cada linha que o se lê do FileReader, aloca-se no model o cliente, insereCliente,
     * assim, como se faz set previamente ao código do mesmo.
     * Aloca-se também no IDadosFicheiro o nº de clientes lidos.
     * Caso tenha ocorrido uma IOException a função dá return -1.
     * Para o 2º ciclo a dinámica é em tudo semelhante, apenas estamos a ler do ficheiro de produtos e aloca-se o produto na faturação do
     * model e no final o nº de produtos no data.
     * Para as vendas já se usa outro mecanismo, lê- se na mesma linha de venda a linha de venda, mas desta vez chama-se a classe VendasValidas,
     * pois é no método divide linha que se separam os campos para verificar se todos eles são válidos. Caso o return do método divideLinha
     * seja 0, então significa que todos os campos são válidos. Desta maneira, então já podemos alocar nas estrutras as vendas, ou seja
     * na InfoVenda e na InfoFatur, assim como na faturação e na filial, conforme o campo da filial seja igual a 1, 2 e 3. No final, aloca-se
     * os dados que se obtém da leitura do ficheiro de vendas.
     * Por fim, caso a leitura tenha sido efetuada com sucesso retorna-se 0.
     *
     */

    public int readFile (String fCl, String fPd, String fVd , IGestaoVendas sgv) {

        IDadosFicheiro data = sgv.getDataF();

        int validas;
        int nrProds = 0;
        int nrClientes = 0;

        // Para os clientes
        try {
            FileReader buf = new FileReader(fCl);
            BufferedReader lerBuf = new BufferedReader(buf);
            String linha = lerBuf.readLine();
            while (linha != null) {
                ICliente cliente = new Cliente();
                cliente.setCodigoC(linha);
                sgv.insereCliente(cliente);
                linha = lerBuf.readLine();
                nrClientes++;
            }
            buf.close();
            data.setNumClientes(nrClientes);
        } catch (IOException e) {
            return -1;
        }


        //Para os produtos
        try {
            FileReader buf = new FileReader(fPd);
            BufferedReader lerBuf = new BufferedReader(buf);
            String linha = lerBuf.readLine();
            while (linha != null) {
                IProduto produto = new Produto();
                produto.setCodigoP(linha);
                sgv.insereProduto(produto);
                sgv.getFt().addProduto(linha); //FATURACAO!!!
                linha = lerBuf.readLine();
                nrProds++;
            }
            buf.close();
            data.setTotalProds(nrProds);
        } catch (IOException e) {
            return -1;
        }


        //Para as vendas
        int preco = 0;
        int errados=0;
        int valida = 1;
        int res = 0;
        int filial;
        int lida=0;
        try {
            validas=0;
            FileReader buf = new FileReader(fVd);
            BufferedReader lerBuf = new BufferedReader(buf);
            String linha = lerBuf.readLine();
            while (linha != null) {
                lida++;
                valida = validar.divideLinha(linha,sgv.getCatCl(),sgv.getCatPd());
                if(valida==0){
                    InfoVenda vd = new InfoVenda();
                    InfoFatur fat = new InfoFatur();
                    vd = vd.setInfoVenda(linha);
                    filial = vd.getFilial();

                    fat.setInfoFatur(linha);
                    res += sgv.getFt().addInfos(fat);

                    if(vd.getPreco()==0.0) preco++;

                    String keyCl = vd.getCliente();
                    ICliente cliente = new Cliente();
                    cliente.setCodigoC(keyCl);
                    if(filial == 1) {
                        sgv.getF1().addVenda(cliente, vd);
                    }
                    if(filial == 2) {
                        sgv.getF2().addVenda(cliente, vd);
                    }
                    if(filial == 3) {
                        sgv.getF3().addVenda(cliente, vd);
                    }
                    validas++;
                }
                else errados++;
                linha = lerBuf.readLine();
            }
            data.setComprasGratis(preco);
            data.setNumVendasErradas(errados);
            lerBuf.close();
        } catch (IOException e) {
            return -1;
        }
        return 0;
    }
}
