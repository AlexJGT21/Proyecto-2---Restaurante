
package restaurantenegocio;

import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;

/**
 *
 * @author JAR
 */
public interface IClienteFrecuenteBO  {
    
    ClienteFrecuente crearCliente(ClienteFrecuenteDTO clienteDTO) throws NegocioException;
}
