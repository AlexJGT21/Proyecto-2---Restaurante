
package restaurantepersistencia;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import restaurantedominio.Ingrediente;
import EnumeradoresDominio.TipoUnidad;
import org.junit.jupiter.api.Disabled;
import restaurantedtos.IngredienteActualizadoDTO;
import restaurantedtos.IngredienteDTO;

/**
 *
 * @author Alex García Trejo
 */
@Disabled
public class IngredienteDAOTest {
    
    private IngredienteDAO dao;
    
    public IngredienteDAOTest() {
    }

    @BeforeEach
    public void init() {
        dao = new IngredienteDAO();
    }
//    
//    @Test
//    public void testRegistrarNuevoIngrediente() {
//        BigDecimal cantidad =  new BigDecimal("2");
//        IngredienteDTO ingrediente = new IngredienteDTO("Pan de Hamburguesa", EnumeradoresDTO.TipoUnidad.PIEZAS, cantidad, null);
//        assertDoesNotThrow(() -> {
//            Ingrediente ingredienteNuevo = dao.nuevoIngrediente(ingrediente);
//            assertNotNull(ingredienteNuevo);
//        });
//    }        
//    
//    @Test
//    public void testBuscarParaValidarIngredienteNombreYUnidad() {
//        String nombre = "Pan de Hamburguesa";
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
//    
//    @Test
//    public void testBaseDeDatosVacia() {
//        assertDoesNotThrow(() -> {
//            List<Ingrediente> consultar = dao.llenarTabla();
//            assertTrue(consultar.isEmpty());
//        });            
//    }
//    
//    @Test
//    public void testBaseDeDatosNoEstaVacia() {
//        assertDoesNotThrow(() -> {
//            List<Ingrediente> consultar = dao.llenarTabla();
//            assertFalse(consultar.isEmpty());
//        });
//    }
//    
//    @Test
//    public void testBuscarIngredientesPorNombreCompleto() {
//        assertDoesNotThrow(() -> {
//            List<Ingrediente> lista = dao.buscarPorNombreUnidad("Harina", null);
//            assertNotNull(lista);
//            for (Ingrediente i: lista) {
//                System.out.println(i.toString());
//            }
//        });
//    }
//    
//    @Test
//    public void testBuscarIngredientesPorNombreParcial() {
//        assertDoesNotThrow(() -> {
//            List<Ingrediente> lista = dao.buscarPorNombreUnidad("Ace", null); //Aceite
//            assertNotNull(lista);
//            for (Ingrediente i: lista) {
//                System.out.println(i.toString());
//            }
//        });
//    }
//    
//    @Test
//    public void testBuscarIngredientesPorUnidadOnza() {
//        assertDoesNotThrow(() -> {
//            List<Ingrediente> lista = dao.buscarPorNombreUnidad(null, TipoUnidad.ONZA);
//            assertNotNull(lista);
//            for (Ingrediente i: lista) {
//                System.out.println(i.toString());
//            }
//        });
//    }
//    
//    @Test
//    public void testBuscarIngredientesPorUnidadKilogramos() {
//        assertDoesNotThrow(() -> {
//            List<Ingrediente> lista = dao.buscarPorNombreUnidad(null, TipoUnidad.KILOGRAMOS);
//            assertNotNull(lista);
//            for (Ingrediente i: lista) {
//                System.out.println(i.toString());
//            }
//        });
//    }
//    
//    @Test
//    public void testBuscarIngredientesPorUnidadPiezas() {
//        assertDoesNotThrow(() -> {
//            List<Ingrediente> lista = dao.buscarPorNombreUnidad(null, TipoUnidad.PIEZAS);
//            assertNotNull(lista);
//            for (Ingrediente i: lista) {
//                System.out.println(i.toString());
//            }
//        });
//    }
//    
//    @Test
//    public void testBuscarIngredientesPorUnidadMililitros() {
//        assertDoesNotThrow(() -> {
//            List<Ingrediente> lista = dao.buscarPorNombreUnidad(null, TipoUnidad.MILILITROS);
//            assertNotNull(lista);
//            for (Ingrediente i: lista) {
//                System.out.println(i.toString());
//            }
//        });
//    }
//    
//    @Test
//    public void testBuscarIngredientesPorUnidadLitros() {
//        assertDoesNotThrow(() -> {
//            List<Ingrediente> lista = dao.buscarPorNombreUnidad(null, TipoUnidad.LITROS);
//            assertNotNull(lista);
//            for (Ingrediente i: lista) {
//                System.out.println(i.toString());
//            }
//        });
//    }
//    
//    @Test
//    public void testBuscarIngredientesPorUnidadGramos() {
//        assertDoesNotThrow(() -> {
//            List<Ingrediente> lista = dao.buscarPorNombreUnidad(null, TipoUnidad.GRAMOS);
//            assertNotNull(lista);
//            for (Ingrediente i: lista) {
//                System.out.println(i.toString());
//            }
//        });
//    }
//    
//    @Test
//    public void testBuscarIngredientesSinArgumentosBusqueda() {
//        assertDoesNotThrow(() -> {
//            List<Ingrediente> lista = dao.buscarPorNombreUnidad(null, null);
//            assertNotNull(lista);
//            for (Ingrediente i: lista) {
//                System.out.println(i.toString());
//            }
//        });
//    }
//    
//    @Test
//    public void testActualizarCantidadIngrediente() {
//        BigDecimal cantidad = new BigDecimal("7");
//        IngredienteActualizadoDTO actualizar = new IngredienteActualizadoDTO(7L, cantidad);
//        assertDoesNotThrow(() -> {
//            Ingrediente ingrediente = dao.inventariarIngrediente(actualizar);
//        });
//    }
//    
//    @Test
//    public void testActualizarIngredienteNoExiste() {
//        BigDecimal cantidad = new BigDecimal("7");
//        IngredienteActualizadoDTO actualizar = new IngredienteActualizadoDTO(120L, cantidad);
//        assertThrows(NullPointerException.class, () -> { //Exception Forzada
//            Ingrediente ingrediente = dao.inventariarIngrediente(actualizar);
//        });
//    }
//    
//    @Test
//    public void testConsultarProductosDisponibles() {
//        assertDoesNotThrow(() -> {
//            List<IngredienteDTO> lista = dao.consultarTodosLosIngredientes();
//            assertNotNull(lista);
//        });
//    }
}