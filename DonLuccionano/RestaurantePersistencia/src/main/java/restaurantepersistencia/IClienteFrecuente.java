
package restaurantepersistencia;

import java.util.List;
import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;

/**
 *
 * @author Alex García Trejo
 */
public interface IClienteFrecuente {
    
    //Contrato 1: crear (insertar en sistema) un cliente frecuente
    public abstract ClienteFrecuente crearCliente(ClienteFrecuenteDTO clienteFrecuente) throws PersistenciaException;
    
    //Contrato 2: buscar un cliente frecuente por nombre
    public abstract List<ClienteFrecuente> buscarNombre(String nombreCliente) throws PersistenciaException;
    
    //Contrato 3: buscar cliente por numero telefonico
    public abstract List<ClienteFrecuente> numeroCliente(String numeroCliente) throws PersistenciaException;
    
    //Contrato 4: buscar cliente por correo electronico
    public abstract List<ClienteFrecuente> emailCliente(String emailCliente) throws PersistenciaException;
}
