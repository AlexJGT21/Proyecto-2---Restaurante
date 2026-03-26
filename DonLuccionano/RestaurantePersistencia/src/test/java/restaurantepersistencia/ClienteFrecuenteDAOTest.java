
package restaurantepersistencia;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;

/**
 *
 * @author Alex García Trejo
 * JAR
 */
public class ClienteFrecuenteDAOTest {
    
    private ClienteFrecuenteDAO dao;
    
    public ClienteFrecuenteDAOTest() {
    }
    
    @BeforeEach
    public void init() {
        this.dao = new ClienteFrecuenteDAO();
    }
//    
//    @Test
//    public void testCrearClienteFrecuente() {
//        ClienteFrecuenteDTO cliente = new ClienteFrecuenteDTO("Sidharta", "Gautama", "Buda", "159753624", "DiosPropio@gmail.com", LocalDate.of(2026, 03, 24));
//        assertDoesNotThrow(() -> {
//            ClienteFrecuente clienteFc = dao.crearCliente(cliente);
//            assertNotNull(clienteFc);
//        });
//    }
//    
//    @Test
//    public void testBuscarClientePorNombre() {
//        String nombre = "Mario";
//        String apellidoPaterno = "Alberto";
//        String apellidoMaterno = "Quaquity";
//        assertDoesNotThrow(() -> {
//            ClienteFrecuente clienteFr = dao.buscarCliente(nombre, apellidoPaterno, apellidoMaterno);
//            assertNotNull(clienteFr);
//        });
//    }
//    
//    @Test
//    public void testBuscarClientePorNumeroTelefonico() {
//        String numeroTelefonico = "159753624";
//        assertDoesNotThrow(() -> {
//            ClienteFrecuente clienteFr = dao.buscarPorTelefono(numeroTelefonico);
//            assertNotNull(clienteFr);
//        });
//    }
//    
//    @Test
//    public void testBuscarClientePorCorreoElectronico() {
//        String correoElectronico = "DiosPropio@gmail.com";
//        assertDoesNotThrow(() -> {
//            ClienteFrecuente clienteFr = dao.buscarPorCorreo(correoElectronico);
//            assertNotNull(clienteFr);
//        });
//    }
}