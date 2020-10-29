package Model;

public interface ICliente {
   /** Get e Set */
    String getCodigoC();
    void setCodigoC(String codigo);

    /** Equals e hashCode */
    boolean equals(Object o);
    int hashCode();

}
