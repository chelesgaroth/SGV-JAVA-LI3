package TestesPerformance;

import Model.ICatalogoClientes;
import Model.ICliente;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe de teste:
 * Tem a mesma interface que a CatalogoClientes, de modo a permitir que possamos preencher na mesma a GestaoVendas apesar
 * de usarmos outro tipo de classe na Coleção usada para construir a estrutura, neste caso em vez de HashSet, usamos
 * TreeSet.
 */

public class CatalogoClientesTeste implements ICatalogoClientes, Serializable {

    private Set<ICliente> clientes;

    /**Comparator para a TreeSet de modo a compararmos os códigos de cliente (String)*/
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

    /**Construtor vazio*/
    public CatalogoClientesTeste(){
        this.clientes = new TreeSet<>(new The_Comparator());
    }

    /**Adiciona cliente ao catálogo*/
    public ICatalogoClientes addClient(ICliente c){
        this.clientes.add(c);
        return this;
    }

    /**Verifica se existe o cliente ou não no catálogo*/
    public boolean existsClient(ICliente c){
        return this.clientes.contains(c);
    }

    /**Devolve o catálogo de clientes*/
    public Set<ICliente> getClientes() {
        return clientes;
    }
}


