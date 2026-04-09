
package Interfaces;

import java.util.List;
import restaurantedominio.ProductoIngredientes;
import restaurantenegocio.NegocioException;

/**
 *
 * @author Jaime
 */
public interface IProductoIngredientesBO {
    public ProductoIngredientes agregarProductoIngrediente(ProductoIngredientes pi) throws NegocioException;
    
    public List<ProductoIngredientes> obtenerIngredientesPorProducto(Long idProducto) throws NegocioException;
}
