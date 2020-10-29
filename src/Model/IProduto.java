package Model;

public interface IProduto {
    /** Get e Set */
    String getCodigoP();
    void setCodigoP(String codigo);

    /** Equals e HashCode */
    boolean equals(Object o);
    int hashCode();
}
