
package restaurantepersistencia;

import restaurantedominio.Ingrediente;
import restaurantedtos.IngredienteDTO;

/**
 *
 * @author Alex García Trejo
 */
public interface IIngredienteDAO {
    
    //Contrato 1: Permite crear un nuevo ingrediente - NOTA: BUSCAR SI SE PUEDE VERIFICAR EXISTENCIA AL CREAR O CREAR OTRO METODO
    public abstract Ingrediente nuevoIngrediente(IngredienteDTO nuevoIngrediente) throws PersistenciaException;
}
