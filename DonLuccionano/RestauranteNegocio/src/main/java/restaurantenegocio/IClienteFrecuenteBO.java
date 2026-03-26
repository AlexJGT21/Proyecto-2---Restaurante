
package restaurantenegocio;

import java.util.List;
import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;

/**
 *
 * @author JAR
 */
public interface IClienteFrecuenteBO  {
    
    public abstract ClienteFrecuente crearCliente(ClienteFrecuenteDTO clienteDTO) throws NegocioException;
    
    public abstract ClienteFrecuente buscarCliente(String nombre, String apellidoP, String apellidoM) throws NegocioException;
    
    public abstract List<ClienteFrecuente> numeroCliente(String numeroCliente) throws NegocioException;
    
    public abstract List<ClienteFrecuente> emailCliente(String emailCliente) throws NegocioException;
    
    public abstract List<ClienteFrecuente> buscarNombre(String nombreCliente) throws NegocioException;
    
    public abstract ClienteFrecuente buscarPorTelefono(String telefono) throws NegocioException;
    
    public abstract ClienteFrecuente buscarPorCorreo(String correo) throws NegocioException;
    

    

    
  
}
