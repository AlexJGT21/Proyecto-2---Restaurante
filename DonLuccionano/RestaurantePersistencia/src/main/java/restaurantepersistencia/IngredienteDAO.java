
package restaurantepersistencia;

import Conexion.ManejadorConexiones;
import Interfaces.IIngredienteDAO;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import restaurantedominio.Ingrediente;
import restaurantedominio.TipoUnidad;
import restaurantedtos.IngredienteDTO;
import restaurantepersistencia.Adapter.NuevoIngredienteDTOAIngredienteAdapter;

/**
 *
 * @author Alex García Trejo
 */
public class IngredienteDAO implements IIngredienteDAO {

    private static final Logger LOGGER = Logger.getLogger(IngredienteDAO.class.getName());
    
    /**
     * Metodo que persiste un Ingrediente en la Base de datos
     * @param nuevoIngrediente Ingrediente nuevo por persistir
     * @return Ingrediente persistido
     * @throws PersistenciaException Si no se pudo persistir
     */
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

    @Override
    public Ingrediente buscarPorNombreYUnidad(String nombre, TipoUnidad unidad) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            TypedQuery<Ingrediente> query = entityManager.createQuery(
            """
            SELECT i
            FROM Ingrediente i
            WHERE i.nombre = :nombre AND i.unidad = :unidad
            """, Ingrediente.class
            );
            query.setParameter("nombre", nombre);
            query.setParameter("unidad", unidad);
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO SE PUDO CONSULTAR INGREDIENTE");
        }        
    }
}