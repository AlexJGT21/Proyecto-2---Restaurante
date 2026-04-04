    
package restaurantepersistencia.Adapter;

import restaurantedominio.Ingrediente;
import restaurantedominio.TipoUnidad;
import restaurantedtos.IngredienteDTO;

/**
 *
 * @author Alex García Trejo
 */
public class NuevoIngredienteDTOAIngredienteAdapter {
    
    public static Ingrediente adaptar(IngredienteDTO nuevoIngrediente) {
        TipoUnidad unidad;        
        switch (nuevoIngrediente.getUnidad()) {
            case GRAMOS -> unidad = TipoUnidad.GRAMOS;
            case KILOGRAMOS -> unidad = TipoUnidad.KILOGRAMOS;
            case PIEZAS -> unidad = TipoUnidad.PIEZAS;
            case MILILITROS -> unidad = TipoUnidad.MILILITROS;
            case LITROS -> unidad = TipoUnidad.LITROS;
            case ONZA -> unidad = TipoUnidad.ONZA;
            default -> throw new IllegalArgumentException("Unidad no valida");
        } 
        return new Ingrediente(nuevoIngrediente.getNombre(), unidad, nuevoIngrediente.getCantidad(), nuevoIngrediente.getImagen());
    }   
}