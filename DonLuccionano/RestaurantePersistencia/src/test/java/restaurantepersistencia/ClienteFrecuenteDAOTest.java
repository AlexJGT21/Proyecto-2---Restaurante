
package restaurantepersistencia;

import java.time.LocalDate;
import java.util.List;
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
    
    @Test
    public void testCrearClienteFrecuente() {
        ClienteFrecuenteDTO cliente = new ClienteFrecuenteDTO("Alex", "García", "Trejo", "6241857098", "alexGarciaTrejo@gmail.com", LocalDate.of(2003, 04, 28));
        assertDoesNotThrow(() -> {
            ClienteFrecuente clienteFc = dao.crearCliente(cliente);
            assertNotNull(clienteFc);
        });
    }
    
    @Test
    public void testBuscarClientePorNombre() {
        String nombre = "Alex";
        String apellidoPaterno = "García";
        String apellidoMaterno = "Trejo";
        assertDoesNotThrow(() -> {
            ClienteFrecuente clienteFr = dao.buscarCliente(nombre, apellidoPaterno, apellidoMaterno);
            assertNotNull(clienteFr);
        });
    }
    
    @Test
    public void testBuscarClientePorNumeroTelefonico() {
        String numeroTelefonico = "6241857098";
        assertDoesNotThrow(() -> {
            ClienteFrecuente clienteFr = dao.buscarPorTelefono(numeroTelefonico);
            assertNotNull(clienteFr);
        });
    }
    
    @Test
    public void testBuscarClientePorCorreoElectronico() {
        String correoElectronico = "alexGarciaTrejo@gmail.com";
        assertDoesNotThrow(() -> {
            ClienteFrecuente clienteFr = dao.buscarPorCorreo(correoElectronico);
            assertNotNull(clienteFr);
        });
    }
    
    @Test
    public void testBuscarClientePorNombreOtrosCamposVacios() {
        String nombre = "Alex";
        assertDoesNotThrow(() -> {
            List<ClienteFrecuente> clienteFr = dao.buscarClienteLista(nombre, null, null);
            for (ClienteFrecuente c: clienteFr) {
                System.out.println(c.toString());
            }
        });
    }
    
    @Test
    public void testBuscarClientePorApellidoPaternoOtrosCamposVacios() {
        String apellidoPaterno = "Garcia";
        assertDoesNotThrow(() -> {
            List<ClienteFrecuente> clienteFr = dao.buscarClienteLista(null, apellidoPaterno, null);
            for (ClienteFrecuente c: clienteFr) {
                System.out.println(c.toString());
            }
        });
    }
    
    @Test
    public void testBuscarClientePorApellidoMaternoOtrosCamposVacios() {
        String apellidoMaterno = "Trejo";
        assertDoesNotThrow(() -> {
            List<ClienteFrecuente> clienteFr = dao.buscarClienteLista(null, null, apellidoMaterno);
            for (ClienteFrecuente c: clienteFr) {
                System.out.println(c.toString());
            }
        });
    }  
    
    @Test
    public void testBuscarClientesTodosCamposVacios() {
        assertDoesNotThrow(() -> {
            List<ClienteFrecuente> clienteFr = dao.buscarClienteLista(null, null, null);
            for (ClienteFrecuente c: clienteFr) {
                System.out.println(c.toString());
            }
        });
    }
}