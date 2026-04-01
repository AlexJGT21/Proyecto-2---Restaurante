
package restaurantepersistencia;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;
import restaurantedominio.Ingrediente;
import restaurantedominio.TipoUnidad;
import restaurantedtos.IngredienteDTO;

/**
 *
 * @author Alex García Trejo
 */
public class IngredienteDAOTest {
    
    private IngredienteDAO dao;
    
    public IngredienteDAOTest() {
    }

    @BeforeEach
    public void init() {
        dao = new IngredienteDAO();
    }
    
//    @Test
//    public void registrarNuevoIngrediente() {
//        BigDecimal cantidad =  new BigDecimal("2");
//        IngredienteDTO ingrediente = new IngredienteDTO("Pan de Hamburguesa", restaurantedtos.TipoUnidad.PIEZAS, cantidad);
//        assertDoesNotThrow(() -> {
//            Ingrediente ingredienteNuevo = dao.nuevoIngrediente(ingrediente);
//            assertNotNull(ingredienteNuevo);
//        });
//    } 
//    
//    @Test
//    public void buscarParaValidarIngredienteNombreYUnidad() {
//        String nombre = "Carne Molida";
//        assertDoesNotThrow(() -> {
//            Ingrediente ingredienteBuscar = dao.buscarPorNombreYUnidad(nombre, TipoUnidad.PIEZAS);
//            assertNotNull(ingredienteBuscar);
//            assertEquals(nombre, ingredienteBuscar.getNombre());
//            assertEquals(TipoUnidad.PIEZAS, ingredienteBuscar.getUnidad());
//        });
//    }
//    
//    @Test
//    public void testConsultarIngredientesBD() {
//        assertDoesNotThrow(() -> {
//            List<Ingrediente> consultar = dao.llenarTabla();
//            for (Ingrediente i: consultar) {
//                System.out.println(i.toString());
//            }
//        });
//    }
}
