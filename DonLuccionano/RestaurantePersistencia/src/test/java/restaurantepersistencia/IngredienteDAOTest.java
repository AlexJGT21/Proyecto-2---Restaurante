
package restaurantepersistencia;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import restaurantedominio.Ingrediente;
import restaurantedtos.IngredienteDTO;
import restaurantedtos.TipoUnidad;

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
    
    @Test
    public void registrarNuevoIngrediente() {
        IngredienteDTO ingrediente = new IngredienteDTO("Carne Molida", TipoUnidad.GRAMOS, 500);
        assertDoesNotThrow(() -> {
            Ingrediente ingredienteNuevo = dao.nuevoIngrediente(ingrediente);
            assertNotNull(ingredienteNuevo);
        });
    }    
}
