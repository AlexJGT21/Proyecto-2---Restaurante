package restaurantepersistencia.Adapter; // Asegúrate de que el paquete sea el correcto

import restaurantedominio.Producto;
import restaurantedtos.ProductoDTO;

/**
 *
 * @author Jaime
 */
public class NuevoProductoDTOAProductoAdapter {
    
    public static Producto adaptar(ProductoDTO nuevoProducto) {
        
        // 1. Usamos el nuevo constructor que creamos en la Entidad Producto
        Producto producto = new Producto(
                nuevoProducto.getNombre(), 
                nuevoProducto.getDescripcion(), 
                nuevoProducto.getTipo(), 
                nuevoProducto.getPrecio(), 
                nuevoProducto.isActivo()
        );
        
        // 2. Seteamos la imagen (ya que es opcional y no está en el constructor)
        producto.setImagen(nuevoProducto.getImagen());
        
        // 3. Retornamos la entidad lista para guardarse en la BD
        return producto;
    }
}