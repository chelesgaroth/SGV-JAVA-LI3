package Model;


import java.io.Serializable;

/**
 * Classe que implementa as variáveis de cada IInfoFatur.
 */
public class InfoFatur implements IInfoFatur, Serializable {
    private String produto;
    private int mes;
    private int filial;
    private int nr_vendasN;  /**número de vendas deste produto em N*/
    private int nr_vendasP;  /**número de vendas deste produto em P*/
    private double fatP;     /**total da faturacao em modo P*/
    private double fatN;     /**total da faturacao em modo N*/
    private int unidT;       /**número Total vendas feitas deste produto*/

    public InfoFatur(){
        this.produto = "";
        this.mes = 0;
        this.filial = 0;
        this.nr_vendasN = 0;
        this.nr_vendasP = 0;
        this.fatP = 0.0;
        this.fatN = 0.0;
        this.unidT = 0;
    }

    /**
     * Responsável por adicionar as informações de uma venda a uma InfoFatur
     */
    public void setInfoFatur(String venda){
        String[] vendaSplit = venda.split(" ");
        this.produto = vendaSplit[0];
        this.mes = Integer.parseInt(vendaSplit[5]);
        this.filial = Integer.parseInt(vendaSplit[6]);
        if((vendaSplit[3].charAt(0))=='N'){
            this.nr_vendasN++;
            this.fatN += ((Double.parseDouble(vendaSplit[1]))*(Integer.parseInt(vendaSplit[2])));
        }
        else {
            this.nr_vendasP++;
            this.fatP += ((Double.parseDouble(vendaSplit[1]))*(Integer.parseInt(vendaSplit[2])));
        }
        this.unidT += Integer.parseInt(vendaSplit[2]);
    }

    /**
     * Fazer set de uma infoFatur com as informações de outra
     */
    public void newInfo(IInfoFatur fat){
        this.produto = fat.get_produto();
        this.mes = fat.get_mes();
        this.filial = fat.get_fil();
        this.nr_vendasN = fat.get_nr_vendasN();
        this.nr_vendasP = fat.get_nr_vendasP();
        this.fatN = fat.get_FatN();
        this.fatP = fat.get_FatP();
        this.unidT = fat.get_unidT();
    }

    /**
     * Método que compara duas InfoFatur para ver se ambas têm a mesma filial ou mês.
     */
    public boolean compareInfos(IInfoFatur aux){
        if(this.produto.equals(aux.get_produto())){
            if(this.mes == (aux.get_mes())){
                if(this.filial == (aux.get_fil()))
                    return true;
            }
        }
        return false;
    }

    /**
     * Adicionar as Informações de uma infoFatur a outra já existente
     */
    public void addInfos(IInfoFatur soma){
        this.nr_vendasN += soma.get_nr_vendasN();
        this.nr_vendasP += soma.get_nr_vendasP();
        this.fatN += soma.get_FatN();
        this.fatP += soma.get_FatP();
        this.unidT += soma.get_unidT();
    }

    /**
     * Getters e Setters
     */
    public String get_produto(){
        return this.produto;
    }

    public int get_mes(){
        return this.mes;
    }

    public int get_fil(){
        return this.filial;
    }

    public  int get_nr_vendasN(){
        return this.nr_vendasN;
    }

    public int get_nr_vendasP(){
        return this.nr_vendasP;
    }

    public double get_FatN(){
        return this.fatN;
    }

    public double get_FatP(){
        return this.fatP;
    }

    public int get_unidT(){
        return this.unidT;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public void setFilial(int filial) {
        this.filial = filial;
    }

    public void setFatN(double fatN) {
        this.fatN = fatN;
    }

    public void setFatP(double fatP) {
        this.fatP = fatP;
    }

    public void setNr_vendasN(int nr_vendasN) {
        this.nr_vendasN = nr_vendasN;
    }

    public void setNr_vendasP(int nr_vendasP) {
        this.nr_vendasP = nr_vendasP;
    }

    public void setUnidT(int unidT) {
        this.unidT = unidT;
    }

    /**
     * Método toString()
     */
    public String toString() {
        return "\nInfoFatur{" +
                "\n" + produto + '\'' +
                "\n" + mes +
                "\n" + filial +
                "\n" + nr_vendasN +
                "\n" + nr_vendasP +
                "\n" + fatP +
                "\n" + fatN +
                "\n" + unidT + "\n" + '}' +
                "\n\n";
    }
}
