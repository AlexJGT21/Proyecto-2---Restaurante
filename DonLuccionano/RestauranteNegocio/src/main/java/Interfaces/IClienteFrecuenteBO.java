
package Interfaces;

import java.util.List;
import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;
import restaurantedtos.ClienteFrecuenteReporteDTO;
import restaurantenegocio.NegocioException;

/**
 *
 * @author JAR
 */
public interface IClienteFrecuenteBO  {
    
    //Contrato 1: Permite crear un nuevo cliente frecuente
    public abstract ClienteFrecuente crearCliente(ClienteFrecuenteDTO clienteDTO) throws NegocioException;
    
    //Contrato 2: Busca buscar un cliente por nombre
    public abstract List<ClienteFrecuente> buscarClienteLista(String nombre, String apellidoP, String apellidoM) throws NegocioException;
    
    //Contrato 3: Busca un cliente por su telefono
    public abstract ClienteFrecuente buscarPorTelefono(String telefono) throws NegocioException;
    
    //Contrato 4: Busca un cliente por su correo electronico
    public abstract ClienteFrecuente buscarPorCorreo(String correo) throws NegocioException;
    
    //Contrato 5: Permite crear un listado de clientes frecuentes
    public abstract List<ClienteFrecuenteDTO> listaClientesF() throws NegocioException;
    
    //Contrato 6: Logica de actualizacion para puntos, visitas y total gastado de un cliente frecuente
    public abstract ClienteFrecuente actualizarVisita(Long idCliente, Double totalVenta) throws NegocioException;
    
    //Contrato 7: Busca clientes frecuentes por nombre o numero de visitas
    public abstract List<ClienteFrecuenteReporteDTO> filtrarClientes(String nombre, Integer visitas) throws NegocioException;

    //Contrato 8: Generacion del reporte
    public abstract boolean generarReporteClientesFrecuentes(String nombre, Integer visitas) throws NegocioException;
    
}