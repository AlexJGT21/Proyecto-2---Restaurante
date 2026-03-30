
package Interfaces;

import restaurantedominio.Ingrediente;
import restaurantedtos.IngredienteDTO;
import restaurantedominio.TipoUnidad;
import restaurantenegocio.NegocioException;

/**
 *
 * @author Alex García Trejo
 */
public interface IIngredienteBO {
    
    //Contrato 1: Permite crear un nuevo ingrediente - NOTA: BUSCAR SI SE PUEDE VERIFICAR EXISTENCIA AL CREAR O CREAR OTRO METODO
    public abstract Ingrediente nuevoIngrediente(IngredienteDTO nuevoIngredienteDTO) throws NegocioException;
    
}
