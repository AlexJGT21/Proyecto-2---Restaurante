
package Interfaces;

import java.util.List;
import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;
import restaurantenegocio.NegocioException;

/**
 *
 * @author JAR
 */
public interface IClienteFrecuenteBO  {
    
    public abstract ClienteFrecuente crearCliente(ClienteFrecuenteDTO clienteDTO) throws NegocioException;
    public abstract List<ClienteFrecuente> buscarClienteLista(String nombre, String apellidoP, String apellidoM) throws NegocioException;
    public abstract ClienteFrecuente buscarPorTelefono(String telefono) throws NegocioException;
    public abstract ClienteFrecuente buscarPorCorreo(String correo) throws NegocioException;
}