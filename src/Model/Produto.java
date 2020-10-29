package Model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe que define um produto.
 * O produto é constituído pelo seu código.
 */
public class Produto implements Serializable,IProduto {
    private String codigo;

    /**
     *Construtor vazio
     */
    public Produto(){
        this.codigo = "";
    }

    /**
     *   Construtor parameterizado
     */
    public Produto(String codigo){
        this.codigo = codigo;
    }

    /**
     *Construtor de cópia
     */
    public Produto(Produto p){
        this.codigo = p.getCodigoP();
    }

    /**
     * Getters e Setters
     */
    public String getCodigoP() {
        return this.codigo;
    }

    public void setCodigoP(String codigo){
        this.codigo = codigo;
    }

    /**
     *Equals
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return this.codigo.equals(produto.getCodigoP());
    }

    /**
     *HashCode
     */
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return codigo ;
    }
}


