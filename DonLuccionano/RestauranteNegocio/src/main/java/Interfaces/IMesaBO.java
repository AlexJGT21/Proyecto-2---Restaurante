
package Interfaces;

import EnumeradoresDominio.Disponibilidad;
import java.util.List;
import restaurantedominio.Mesa;
import restaurantedtos.MesaDTO;
import restaurantenegocio.NegocioException;

/**
 *
 * @author Alex García Trejo
 */
public interface IMesaBO {
    
    //Contrato 1: Validaciones sobre la creacion de una mesa.
    public abstract Mesa registrarMesa(MesaDTO nuevaMesa) throws NegocioException;
    
    //Contrato 2: Consulta todas las mesas
    public abstract List<Mesa> listarMesas() throws NegocioException;

    //Contrato 3: Permite cambiar el estado de una mesa
    public abstract Mesa cambiarDisponibilidad(Long id, Disponibilidad disponibilidad) throws NegocioException;
}
