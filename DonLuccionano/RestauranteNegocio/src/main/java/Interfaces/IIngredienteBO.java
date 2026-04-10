
package Interfaces;

import java.util.List;
import restaurantedominio.Ingrediente;
import EnumeradoresDominio.TipoUnidad;
import restaurantedtos.IngredienteActualizadoDTO;
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
    
    //Contrato 3: Permite buscar ingredientes por nombre o unidad
    public abstract List<Ingrediente> buscarPorNombreUnidad(String nombreIngrediente, TipoUnidad unidadIngrediente) throws NegocioException;

    //Contrato 4: Permite actualizar la cantidad de un ingrediente.
    //Aquí se hacen las validaciones de la cantidad
    public abstract Ingrediente inventariarIngrediente(IngredienteActualizadoDTO ingredienteInventario) throws NegocioException;
 
    //Contrato 5: Lista todos los ingredientes como DTOs
    public abstract List<IngredienteDTO> consultarTodosLosIngredientes() throws NegocioException;    
}