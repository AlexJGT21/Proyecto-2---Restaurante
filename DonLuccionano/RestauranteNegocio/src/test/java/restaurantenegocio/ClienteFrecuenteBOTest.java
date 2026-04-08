
package restaurantenegocio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import restaurantedominio.ClienteFrecuente;
import restaurantepersistencia.ClienteFrecuenteDAO;

/**
 *
 * @author maild
 */
public class ClienteFrecuenteBOTest {
    
    private ClienteFrecuenteBO bo;
    private ClienteFrecuenteDAO dao;
    
    public ClienteFrecuenteBOTest() {
        
        
    }

    @BeforeEach
    public void init() {
        this.bo = new ClienteFrecuenteBO();
        this.dao = new ClienteFrecuenteDAO();
    }
    
//    @Test
//    public void testActualizasVisitaClienteFrecuente() {
//        Long id = 2L;
//        Double nuevoTotalGastado = 160.0;
//        Integer nuevosPuntos = 8;
//        Integer nuevasVisitas = 2;
//        assertDoesNotThrow(() -> {
//            ClienteFrecuente cliente = bo.actualizarVisita(2L, 100.0);
//            ClienteFrecuente buscar = dao.buscarPorId(id);
//            
//            assertEquals(buscar.getPuntos(), nuevosPuntos);
//            assertEquals(buscar.getTotalGastado(), nuevoTotalGastado);
//            assertEquals(buscar.getTotalVisitas(), nuevasVisitas);
//        });
//    }
//    
//    @Test
//    public void testActualizarVisitaClienteNoEncontrado() {
//        NegocioException assertThrows = assertThrows(NegocioException.class, () -> {
//            ClienteFrecuente cliente = bo.actualizarVisita(4L, 100.0);
//        });
//        assertEquals("El cliente no existe.", 
//                     assertThrows.getMessage());
//    }        
}