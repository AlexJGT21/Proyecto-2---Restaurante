
package restaurantepersistencia;

import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import restaurantedominio.Ingrediente;
import restaurantedtos.IngredienteDTO;
import restaurantepersistencia.Adapter.NuevoIngredienteDTOAIngredienteAdapter;

/**
 *
 * @author Alex García Trejo
 */
public class IngredienteDAO implements IIngredienteDAO {

    private static final Logger LOGGER = Logger.getLogger(IngredienteDAO.class.getName());
    
    @Override
    public Ingrediente nuevoIngrediente(IngredienteDTO nuevoIngrediente) throws PersistenciaException {
        Ingrediente ingrediente = NuevoIngredienteDTOAIngredienteAdapter.adaptar(nuevoIngrediente);
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(ingrediente);
            entityManager.getTransaction().commit();
            return ingrediente;
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO SE PUDO GUARDAR EL NUEVO INGREDIENTE");
        } 
    }
}