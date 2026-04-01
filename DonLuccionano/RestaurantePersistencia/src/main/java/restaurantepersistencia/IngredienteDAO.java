
package restaurantepersistencia;

import Conexion.ManejadorConexiones;
import Interfaces.IIngredienteDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    /**
     * Metodo de busqueda que se usa con "nuevoIngrediente" para verificar existencia de otros productos, evitando duplicados
     * @param nombre Argumento de busqueda
     * @param unidad Argumento de busqueda
     * @return El ingrediente repetido si es que existe
     * @throws PersistenciaException NO se pudo consultar el ingrediente
     */
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

    /**
     * Metodo que llenara la tabla en la presentación
     * @return Lista de todos los ingredientes en la base de datos
     * @throws PersistenciaException Si no se encontraron ingredientes en la base de datos
     */
    @Override
    public List<Ingrediente> llenarTabla() throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            TypedQuery<Ingrediente> query = entityManager.createQuery(
            """
            SELECT i
            FROM Ingrediente i
            """, Ingrediente.class
            );
            return query.getResultList();
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO SE PUDO CONSULTAR LOS INGREDIENTES DE LA BASE DE DATOS.");
        }        
    }

//    @Override
//    public List<Ingrediente> buscarPorNombreUnidad(String nombreIngrediente, TipoUnidad unidadIngrediente) throws PersistenciaException {
//        try {
//            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
//            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//            CriteriaQuery<Ingrediente> criteria = builder.createQuery(Ingrediente.class);
//            Root<Ingrediente> ingrediente = criteria.from(Ingrediente.class);
//            
//            //Predicado para aplicar filtros
//            List<Predicate> predicate = new ArrayList<>();
//            
//            if(nombreIngrediente != null && !nombreIngrediente.trim().isEmpty()) {
//                predicate.add(builder.and(
//                        builder.like(builder.lower(ingrediente.get("nombre")), "%" + nombreIngrediente.trim().toLowerCase() + "%")
//                        )
//                );
//            }
//            if (unidadIngrediente != null) {
//                predicate.add(builder.and(
//                        builder.equal(builder.lower(ingrediente.get("unidad")), unidadIngrediente)
//                        )
//                );
//            }            
//            criteria.select(ingrediente)
//                    .where(builder.and(predicate.toArray(new Predicate[0])));
//            TypedQuery<Ingrediente> query = entityManager.createQuery(criteria);
//            return query.getResultList();            
//        } catch (PersistenceException e) {
//            LOGGER.severe(e.getMessage());
//            throw new PersistenciaException("ERROR AL CONSULTAR INGREDIENTESS");
//        }
//    }
}