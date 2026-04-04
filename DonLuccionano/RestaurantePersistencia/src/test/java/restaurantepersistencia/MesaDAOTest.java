
package restaurantepersistencia;

import EnumeradoresDominio.Disponibilidad;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import restaurantedominio.Mesa;
import restaurantedtos.MesaDTO;

/**
 *
 * @author Alex García Trejp
 */
public class MesaDAOTest {
    
    private MesaDAO dao;
    
    public MesaDAOTest() {
    }
    
    @BeforeEach
    public void init() {
        dao = new MesaDAO();
    }
    
//    @Test
//    public void testRegistrarMesa() {
//        MesaDTO mesaDTO = new MesaDTO(1, EnumeradoresDTO.Disponibilidad.DISPONIBLE);
//        assertDoesNotThrow(() -> {
//           Mesa mesa = dao.registrarMesa(mesaDTO);           
//        });
//    }
//    
//    @Test
//    public void testConsultarCantidadMesasCreadas() {
//        int mesasCreadas = 2; //Aquí sabemos que tenemos dos mesas creadas
//        assertDoesNotThrow(() -> {
//            int mesa = dao.consultarCantidad();
//            assertEquals(mesa, mesasCreadas);
//        });
//    }
//    
//    @Test
//    public void testConocerMesaYaExistente() {
//        int mesaCreada = 7;
//        assertDoesNotThrow(() -> {
//            boolean mesa = dao.existeMesa(mesaCreada);
//            assertTrue(mesa);
//        });
//    }
//    
//    @Test
//    public void testNoExistenteMesa() {
//        int mesaCreada = 8;
//        assertDoesNotThrow(() -> {
//            boolean mesa = dao.existeMesa(mesaCreada);
//            assertFalse(mesa);
//        });
//    } 
//    
//    @Test
//    public void testListarMesasCreadas() {
//        assertDoesNotThrow(() -> {
//           List<Mesa> mesas = dao.listarMesas();
//            for (Mesa m: mesas) {
//                System.out.println(m.toString());
//            }
//        });
//    }
//    
//    @Test
//    public void testCambiarNoDisponiblidadDeMesa() {
//        assertDoesNotThrow(() -> {
//            Mesa mesa = dao.cambiarDisponibilidad(1L, Disponibilidad.NO_DISPONIBLE);
//        });
//    }
//    
//    @Test
//    public void testCambiarDisponiblidadDeMesa() {
//        assertDoesNotThrow(() -> {
//            Mesa mesa = dao.cambiarDisponibilidad(5L, Disponibilidad.DISPONIBLE);
//        });
//    }
}