
package Interfaces;

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

}
