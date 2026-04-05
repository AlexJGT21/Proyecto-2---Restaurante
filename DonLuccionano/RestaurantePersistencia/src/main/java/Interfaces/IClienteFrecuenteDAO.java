    
package Interfaces;

import java.util.List;
import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;
import restaurantepersistencia.PersistenciaException;

/**
 *
 * @author Alex García Trejo
 */
public interface IClienteFrecuenteDAO {
    
    //Contrato 1: crear (insertar en sistema) un cliente frecuente
    public abstract ClienteFrecuente crearCliente(ClienteFrecuenteDTO clienteFrecuente) throws PersistenciaException;
    
    //Contrato 2: buscar un cliente frecuente por nombre
    public abstract List<ClienteFrecuente> buscarClienteLista(String nombreCliente, String apellidoPaterno, String apellidoMaterno) throws PersistenciaException;
    
    //Contato 3: busca cliente frecuente por numero telefonico
    public ClienteFrecuente buscarPorTelefono(String telefono) throws PersistenciaException;
    
    //Contrato 4: busca cliente frecuente por correo electronico
    public ClienteFrecuente buscarPorCorreo(String correo) throws PersistenciaException;
    
    //Contrato 5: Lista todos los clientes frecuentes
    public abstract List<ClienteFrecuenteDTO> listaClientesF() throws PersistenciaException;

    public abstract ClienteFrecuente buscarPorId(long id) throws PersistenciaException;
}
