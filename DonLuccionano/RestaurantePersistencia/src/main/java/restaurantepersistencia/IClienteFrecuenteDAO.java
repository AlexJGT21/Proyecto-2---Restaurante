    
package restaurantepersistencia;

import java.util.List;
import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;

/**
 *
 * @author Alex García Trejo
 */
public interface IClienteFrecuenteDAO {
    
    //Contrato 1: crear (insertar en sistema) un cliente frecuente
    public abstract ClienteFrecuente crearCliente(ClienteFrecuenteDTO clienteFrecuente) throws PersistenciaException;
    
    //Contrato 2: buscar un cliente frecuente por nombre
    public abstract List<ClienteFrecuente> buscarClienteLista(String nombreCliente, String apellidoPaterno, String apellidoMaterno) throws PersistenciaException;
    
    //Contrato 3: buscar cliente por numero telefonico
    public abstract List<ClienteFrecuente> numeroCliente(String numeroCliente) throws PersistenciaException;
    
    //Contrato 4: buscar cliente por correo electronico
    public abstract List<ClienteFrecuente> emailCliente(String emailCliente) throws PersistenciaException;
    
    //Contrato 5; busca cliente frecuente por nombre completo
    public abstract ClienteFrecuente buscarCliente(String nombre, String apellidoP, String apellidoM) throws PersistenciaException;
    
    //contato 6; busca cliente frecuente por numero telefonico
    public ClienteFrecuente buscarPorTelefono(String telefono) throws PersistenciaException;
    
    //contrato 7; busca cliente frecuente por correo electronico
    public ClienteFrecuente buscarPorCorreo(String correo) throws PersistenciaException;
}
