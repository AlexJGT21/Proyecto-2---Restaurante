
package Interfaces;

import java.util.List;
import restaurantedominio.Ingrediente;
import EnumeradoresDominio.TipoUnidad;
import restaurantedtos.IngredienteActualizadoDTO;
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
    //Este metodo se usa para verificar si existen duplicados de un mismo ingrediente    
    public abstract Ingrediente buscarPorNombreYUnidad(String nombre, TipoUnidad unidad) throws PersistenciaException;
    
    //Contrato 3: Este metodo sirve para llenar la tabla de ingredientes (y de ingredientes para productos en su momento)
    //De manera automatica
    public abstract List<Ingrediente> llenarTabla() throws PersistenciaException;
    
    //Contrato 4: Este metodo es para buscar por nombre y unidad pero regresando una lista
    //Este es diferente al "Contrato 2", ya que ese es para buscar uno y verificar
    //Este se usa para busquedar y traer todos los resultados posibles
    public abstract List<Ingrediente> buscarPorNombreUnidad(String nombreIngrediente, TipoUnidad unidadIngrediente) throws PersistenciaException;
    
    //Contrato 5: Permite actualizar la cantidad de un ingrediente
    public abstract Ingrediente inventariarIngrediente(IngredienteActualizadoDTO ingredienteInventario) throws PersistenciaException;
    
    //Actualiza el ingrediente cuando se comanda la orden
    public abstract void actualizarIngrediente (Ingrediente ingrediente) throws PersistenciaException;
    
}
