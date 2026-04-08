
package restaurantepersistencia;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;
import restaurantedtos.ClienteFrecuenteReporteDTO;

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
//        ClienteFrecuenteDTO cliente = new ClienteFrecuenteDTO("Alex", "García", "Trejo", "6241857098", "alexGarciaTrejo@gmail.com", LocalDate.of(2003, 04, 28));
//        assertDoesNotThrow(() -> {
//            ClienteFrecuente clienteFc = dao.crearCliente(cliente);
//            assertNotNull(clienteFc);
//        });
//    }
//    
//    @Test
//    public void testBuscarClientePorNumeroTelefonico() {
//        String numeroTelefonico = "6241857098";
//        assertDoesNotThrow(() -> {
//            ClienteFrecuente clienteFr = dao.buscarPorTelefono(numeroTelefonico);
//            assertNotNull(clienteFr);
//            System.out.println(clienteFr.toString());
//        });
//    }
//    
//    @Test
//    public void testBuscarClientePorCorreoElectronico() {
//        String correoElectronico = "alexGarciaTrejo@gmail.com";
//        assertDoesNotThrow(() -> {
//            ClienteFrecuente clienteFr = dao.buscarPorCorreo(correoElectronico);
//            assertNotNull(clienteFr);
//            System.out.println(clienteFr.toString());
//        });
//    }
//    
//    @Test
//    public void testBuscarClientePorNombreOtrosCamposVacios() {
//        String nombre = "Alex";
//        assertDoesNotThrow(() -> {
//            List<ClienteFrecuente> clienteFr = dao.buscarClienteLista(nombre, null, null);
//            for (ClienteFrecuente c: clienteFr) {
//                System.out.println(c.toString());
//            }
//        });
//    }
//    
//    @Test
//    public void testBuscarClientePorApellidoPaternoOtrosCamposVacios() {
//        String apellidoPaterno = "Garcia";
//        assertDoesNotThrow(() -> {
//            List<ClienteFrecuente> clienteFr = dao.buscarClienteLista(null, apellidoPaterno, null);
//            for (ClienteFrecuente c: clienteFr) {
//                System.out.println(c.toString());
//            }
//        });
//    }
//    
//    @Test
//    public void testBuscarClientePorApellidoMaternoOtrosCamposVacios() {
//        String apellidoMaterno = "Trejo";
//        assertDoesNotThrow(() -> {
//            List<ClienteFrecuente> clienteFr = dao.buscarClienteLista(null, null, apellidoMaterno);
//            for (ClienteFrecuente c: clienteFr) {
//                System.out.println(c.toString());
//            }
//        });
//    }  
//    
//    @Test
//    public void testBuscarClientesTodosCamposVacios() {
//        assertDoesNotThrow(() -> {
//            List<ClienteFrecuente> clienteFr = dao.buscarClienteLista(null, null, null);
//            for (ClienteFrecuente c: clienteFr) {
//                System.out.println(c.toString());
//            }
//        });
//    }
//    
//    @Test
//    public void testListarClientesFrecuenteFunciona() {
//        assertDoesNotThrow(() -> {
//           List<ClienteFrecuenteDTO> lista = dao.listaClientesF();
//            for (ClienteFrecuenteDTO c: lista) {
//                System.out.println(c.toString());
//            }
//        });
//    }
//    
//    @Test
//    public void testBuscarClientePorIdExiste() {
//        assertDoesNotThrow(() -> {
//            ClienteFrecuente cliente = dao.buscarPorId(1);
//            assertNotNull(cliente);
//        });
//    }
//    
//    @Test
//    public void testBuscarClientePorIdNoExiste() {
//        assertDoesNotThrow(() -> {
//            ClienteFrecuente cliente = dao.buscarPorId(3);
//            assertNull(cliente);
//        });
//    }
//    
//    @Test
//    public void testFiltrarClienteFrecuentePorNombre() {
//        assertDoesNotThrow(() -> {
//            List<ClienteFrecuenteReporteDTO> cliente = dao.filtrarClientes("Ana", null);
//            assertNotNull(cliente);
//        });
//    }
//    
//    @Test
//    public void testFiltrarClienteFrecuentePorVisitas() {
//        assertDoesNotThrow(() -> {
//            List<ClienteFrecuenteReporteDTO> cliente = dao.filtrarClientes(null, 2);
//            assertNotNull(cliente);
//        });
//    }
//    
//    @Test
//    public void testFiltrarClienteFrecuenteSinNombreYVisitas() {
//        assertDoesNotThrow(() -> {
//            List<ClienteFrecuenteReporteDTO> cliente = dao.filtrarClientes(null, null);
//            for (ClienteFrecuenteReporteDTO c: cliente) {
//                System.out.println(c.toString());
//            }
//        });
//    }        
}