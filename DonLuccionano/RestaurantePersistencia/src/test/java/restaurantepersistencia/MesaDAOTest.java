
package restaurantepersistencia;

import EnumeradoresDTO.Disponibilidad;
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
//        MesaDTO mesaDTO = new MesaDTO(1, Disponibilidad.DISPONIBLE);
//        assertDoesNotThrow(() -> {
//           Mesa mesa = dao.registrarMesa(mesaDTO);
//        });
//    }
//    
    
}
