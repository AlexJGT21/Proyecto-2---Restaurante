
package restaurantenegocio;

import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;

/**
 *
 * @author JAR
 */
public interface IClienteFrecuenteBO  {
    
    ClienteFrecuente crearCliente(ClienteFrecuenteDTO clienteDTO) throws NegocioException;
    
    public abstract ClienteFrecuente buscarCliente(String nombre, String apellidoP, String apellidoM) throws NegocioException;
    
    public abstract ClienteFrecuente buscarPorTelefono(String telefono) throws NegocioException;
    
    public abstract ClienteFrecuente buscarPorCorreo(String correo) throws NegocioException;
}
