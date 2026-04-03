
package restaurantepersistencia;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import restaurantedominio.Producto;
import restaurantedtos.ProductoDTO;

/**
 *
 * @author Jaime
 */
public class ProductoDAOTest {
    
    private ProductoDAO productoDAO;
    
    public ProductoDAOTest() {
    }

    @BeforeEach
    public void setUp() {
        productoDAO = new ProductoDAO();
    }
    
//    @Test
//    public void registrarNuevoProducto() {
//        String nombrePrueba = "Pizza Especial"; 
//        ProductoDTO producto = new ProductoDTO(nombrePrueba, 250.50f);
//        
//        assertDoesNotThrow(() -> {
//            Producto productoNuevo = productoDAO.crearProducto(producto);
//            assertNotNull(productoNuevo);
//            assertEquals(nombrePrueba, productoNuevo.getNombre());
//            assertEquals(250.50f, productoNuevo.getPrecio());
//        });
//    }
//    
//    @Test
//    public void buscarParaValidarProductoNombre() {
//        String nombrePrueba = "Hamburguesa Doble";
//        
//        assertDoesNotThrow(() -> {
//            productoDAO.crearProducto(new ProductoDTO(nombrePrueba, 120.0f));
//            Producto productoBuscar = productoDAO.buscarPorNombre(nombrePrueba);
//            assertNotNull(productoBuscar);
//            assertEquals(nombrePrueba, productoBuscar.getNombre());
//        });
//    }
//    
//    @Test
//    public void buscarProductoQueNoExiste() {
//        String nombrePrueba = "Tacos de Therizinosaurus jeje";
//        
//        assertDoesNotThrow(() -> {
//            Producto productoBuscar = productoDAO.buscarPorNombre(nombrePrueba);
//            assertNull(productoBuscar);
//        });
//    }   
}
