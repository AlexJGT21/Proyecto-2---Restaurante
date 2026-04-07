    
package Interfaces;

import java.util.List;
import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;
import restaurantedtos.ClienteFrecuenteReporteDTO;
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

    //Contrato 6: Busca un cliente frecuente por si id
    public abstract ClienteFrecuente buscarPorId(long id) throws PersistenciaException;
    
    //Constrato 7: Permite actualizar el total gastado, puntos y total de visitas de un cliente frecuente
    public abstract ClienteFrecuente actualizarVisita(ClienteFrecuente clienteFrecuenteVisita) throws PersistenciaException;
    
    //Contrato 8: Permite filtrar clientes frecuentes por nombre o numero de visitas
    public abstract List<ClienteFrecuenteReporteDTO> filtrarClientes(String nombre, Integer visitas) throws PersistenciaException;
    
}
