package Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Esta classe é responsável por definir a estrutura que agrega todos os Clientes.
 * Definimos o catálogo de clientes como um Set<ICliente>
 */
public class CatalogoClientes implements ICatalogoClientes, Serializable {

    private Set<ICliente> clientes;

    /**
     *Construtor vazio
     */
    public CatalogoClientes (){
        this.clientes = new HashSet<>();
    }

    /**
     *Adiciona cliente ao catálogo
     */
    public ICatalogoClientes addClient(ICliente c){
        this.clientes.add(c);
        return this;
    }

    /**
      *Verifica se existe o cliente ou não no catálogo
     */
    public boolean existsClient(ICliente c){
        return this.clientes.contains(c);
    }

    /**
     * Devolve o catálogo de clientes
     */
    public Set<ICliente> getClientes() {
        return clientes;
    }

}


