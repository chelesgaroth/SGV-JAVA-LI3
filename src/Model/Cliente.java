package Model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe que define um Cliente
 * O cliente contém o seu código.
 */
public class Cliente implements Serializable,ICliente {
    private String codigo;

    /**
     *Construtor vazio
     */
    public Cliente(){
        this.codigo = "";
    }

    /**
     *  Construtor parameterizado
     */
    public Cliente(String codigo){
        this.codigo = codigo;
    }

    /**
     * Construtor de cópia
     */
    public Cliente(Cliente c){
        this.codigo = c.getCodigoC();
    }

    /**
     *  Getters e Setters
     */
   public String getCodigoC() {
        return this.codigo;
    }

    public void setCodigoC(String codigo){
        this.codigo = codigo;
    }

    //Equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return this.codigo.equals(cliente.getCodigoC());
    }

    //HashCode
    public int hashCode() {
        return Objects.hash(codigo);
    }


    public String toString() {
        return codigo;
    }
}
