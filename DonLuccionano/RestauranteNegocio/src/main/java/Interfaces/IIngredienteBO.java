
package Interfaces;

import java.util.List;
import restaurantedominio.Ingrediente;
import restaurantedtos.IngredienteDTO;
import restaurantenegocio.NegocioException;

/**
 *
 * @author Alex García Trejo
 */
public interface IIngredienteBO {
    
    //Contrato 1: Permite crear un nuevo ingrediente - NOTA: BUSCAR SI SE PUEDE VERIFICAR EXISTENCIA AL CREAR O CREAR OTRO METODO
    public abstract Ingrediente nuevoIngrediente(IngredienteDTO nuevoIngredienteDTO) throws NegocioException;
    
    //Contrato 2: Este metodo sirve para llenar la tabla de ingredientes (y de ingredientes para productos en su momento)
    //De manera automatica
    public abstract List<Ingrediente> llenarTabla() throws NegocioException;    
}
