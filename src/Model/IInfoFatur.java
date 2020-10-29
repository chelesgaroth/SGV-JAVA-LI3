package Model;

public interface IInfoFatur {
    public void setInfoFatur(String venda);
    public void newInfo(IInfoFatur fat);
    public boolean compareInfos(IInfoFatur aux);
    public void addInfos(IInfoFatur soma);

    public String get_produto();
    public int get_mes();
    public int get_fil();
    public  int get_nr_vendasN();
    public int get_nr_vendasP();
    public double get_FatN();
    public double get_FatP();
    public int get_unidT();
    public void setProduto(String produto);
    public void setMes(int mes);
    public void setFilial(int filial);
    public void setFatN(double fatN);
    public void setFatP(double fatP);
    public void setNr_vendasN(int nr_vendasN);
    public void setNr_vendasP(int nr_vendasP);
    public void setUnidT(int unidT);
    public String toString();


}
