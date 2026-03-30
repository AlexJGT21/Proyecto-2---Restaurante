
package Interfaces;

import restaurantedominio.Ingrediente;
import restaurantedominio.TipoUnidad;
import restaurantedtos.IngredienteDTO;
import restaurantepersistencia.PersistenciaException;

/**
 *
 * @author Alex García Trejo
 */
public interface IIngredienteDAO {
    
    //Contrato 1: Permite crear un nuevo ingrediente - NOTA: BUSCAR SI SE PUEDE VERIFICAR EXISTENCIA AL CREAR O CREAR OTRO METODO
    public abstract Ingrediente nuevoIngrediente(IngredienteDTO nuevoIngrediente) throws PersistenciaException;
    
    //Contrato 2: Sirve para buscar un ingrediente por nombre y unidad
    //Este metodo se usa para verificar si existen duplicados de un mosmo ingrediente    
    public abstract Ingrediente buscarPorNombreYUnidad(String nombre, TipoUnidad unidad) throws PersistenciaException;
    
}
