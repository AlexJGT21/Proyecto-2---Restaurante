package restaurantepersistencia.Adapter; // Revisa que este paquete sea el correcto en tu proyecto

import java.util.ArrayList;
import java.util.List;
import restaurantedominio.Ingrediente;
import restaurantedominio.Producto;
import restaurantedominio.ProductoIngredientes;
import restaurantedtos.ProductoDTO;
import restaurantedtos.ProductoIngredientesDTO;

/**
 * @author Jaime
 */
public class NuevoProductoDTOAProductoAdapter {
    
    public static Producto adaptar(ProductoDTO nuevoProducto) {
        
        // 1. Mapeo de datos básicos
        Producto producto = new Producto(
                nuevoProducto.getNombre(), 
                nuevoProducto.getDescripcion(), 
                nuevoProducto.getTipo(), 
                nuevoProducto.getPrecio(), 
                nuevoProducto.isActivo()
        );
        
        producto.setImagen(nuevoProducto.getImagen());
        
        // 2. NUEVO: Mapeo de la Receta (Ingredientes)
        if (nuevoProducto.getReceta() != null && !nuevoProducto.getReceta().isEmpty()) {
            List<ProductoIngredientes> listaEntidades = new ArrayList<>();
            
            for (ProductoIngredientesDTO dto : nuevoProducto.getReceta()) {
                ProductoIngredientes detalle = new ProductoIngredientes();
                
                // Asignamos la cantidad exacta de la receta
                detalle.setCantidad(dto.getCantidadRequerida());
                
                // REGLA DE ORO JPA: Enlazar el hijo con el padre
                detalle.setProductos(producto);
                
                // Enlazamos con el Ingrediente real de la base de datos
                // (En JPA, basta con instanciar un objeto vacío y ponerle el ID para que haga la conexión)
                Ingrediente ingredienteBD = new Ingrediente();
                ingredienteBD.setId(dto.getIdIngrediente());
                detalle.setIngredientes(ingredienteBD);
                
                listaEntidades.add(detalle);
            }
            
            // Le pasamos la lista terminada al producto
            producto.setListaIngredientes(listaEntidades);
        }
        
        // 3. Retornamos la entidad completa
        return producto;
    }
}