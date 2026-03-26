
package restaurantepersistencia;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;

/**
 *
 * @author maild
 */
public class ClienteFrecuenteDAOTest {
    
    private ClienteFrecuenteDAO dao;
    
    public ClienteFrecuenteDAOTest() {
    }
    
    @BeforeEach
    public void init() {
        this.dao = new ClienteFrecuenteDAO();
    }
    
//    @Test
//    public void testCrearClienteFrecuente() {
//        ClienteFrecuenteDTO cliente = new ClienteFrecuenteDTO("JAR", "Lopez", "Osuna", "1234567890", "tilin@gmail.com", LocalDate.of(2026, 03, 24));        
//        assertDoesNotThrow(() -> {
//            ClienteFrecuente clienteFc = dao.crearCliente(cliente);
//            assertNotNull(clienteFc);
//        });
//    }
}