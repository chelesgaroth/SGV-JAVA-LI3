package Model;

import java.util.Set;

public interface ICatalogoClientes {

    ICatalogoClientes addClient(ICliente c);
    boolean existsClient(ICliente c);
    Set<ICliente> getClientes();

}
