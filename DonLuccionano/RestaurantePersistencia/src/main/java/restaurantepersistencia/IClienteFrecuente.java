
package restaurantepersistencia;

import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;

/**
 *
 * @author Alex García Trejo
 */
public interface IClienteFrecuente {
    
    public abstract ClienteFrecuente crearCliente(ClienteFrecuenteDTO clienteFrecuente) throws PersistenciaException;
}
