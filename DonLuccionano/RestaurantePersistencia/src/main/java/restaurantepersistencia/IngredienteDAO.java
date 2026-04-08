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
import EnumeradoresDominio.TipoUnidad;
import restaurantedtos.IngredienteActualizadoDTO;
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
     *
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
     * Metodo de busqueda que se usa con "nuevoIngrediente" para verificar
     * existencia de otros productos, evitando duplicados
     *
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
     *
     * @return Lista de todos los ingredientes en la base de datos
     * @throws PersistenciaException Si no se encontraron ingredientes en la
     * base de datos
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

    @Override
    public List<Ingrediente> buscarPorNombreUnidad(String nombreIngrediente, TipoUnidad unidadIngrediente) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Ingrediente> criteria = builder.createQuery(Ingrediente.class);
            Root<Ingrediente> ingrediente = criteria.from(Ingrediente.class);

            //Predicado para aplicar filtros
            List<Predicate> predicate = new ArrayList<>();

            if (nombreIngrediente != null && !nombreIngrediente.trim().isEmpty()) {
                predicate.add(builder.and(
                        builder.like(builder.lower(ingrediente.get("nombre")), "%" + nombreIngrediente.trim().toLowerCase() + "%")                )
                );
            }
            if (unidadIngrediente != null) {
                predicate.add(
                        builder.equal(ingrediente.get("unidad"), unidadIngrediente)
                );
            }
            criteria.select(ingrediente)
                    .where(builder.and(predicate.toArray(new Predicate[0])));
            TypedQuery<Ingrediente> query = entityManager.createQuery(criteria);
            return query.getResultList();
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("ERROR AL CONSULTAR INGREDIENTESS");
        }
    }

    /**
     * Metodo que permite inventariar la cantidad actual de un ingrediente
     *
     * @param ingredienteInventario DTO que permite acceder a la ID del
     * ingrediente. Posteriormente se inventaria la cantidad
     * @return Cantidad actualizada de ingrediente
     * @throws PersistenciaException Si hubo un error al actualizar la cantidad
     * del ingrediente
     */
    @Override
    public Ingrediente inventariarIngrediente(IngredienteActualizadoDTO ingredienteInventario) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();

            Ingrediente ingrediente = entityManager.find(Ingrediente.class, ingredienteInventario.getId());
            ingrediente.setCantidad(ingredienteInventario.getCantidad());
            entityManager.getTransaction().begin();
            entityManager.merge(ingrediente);
            entityManager.getTransaction().commit();
            return ingrediente;
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO SE PUDO ACTUALIZAR EL INGREDIENTE");
        }

    }

    /**
     * Metodo que actualiza un ingrediente al realizar una comanda
     * @param ingrediente Ingrediente por actualizar
     * @throws PersistenciaException No fue posible realizar la actualizacion del ingrediente
     */
    @Override
    public void actualizarIngrediente(Ingrediente ingrediente) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(ingrediente); // Actualiza la entidad en la base de datos
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new PersistenciaException("Error al actualizar el stock del ingrediente: " + e.getMessage());
        }
    }

    @Override
    public List<IngredienteDTO> consultarTodosLosIngredientes() throws Exception {
        EntityManager entitymanager = Conexion.ManejadorConexiones.crearEntityManager();
        try {
            
            List<Ingrediente> lista = entitymanager.createQuery("""
                                                                SELECT i FROM Ingrediente i
                                                                """, Ingrediente.class).getResultList();
            
            List<IngredienteDTO> listaDTO = new ArrayList<>();
           
            for (restaurantedominio.Ingrediente ing : lista) {
                IngredienteDTO dto = new IngredienteDTO();
                dto.setId(ing.getId());
                dto.setNombre(ing.getNombre());
                dto.setCantidad(ing.getCantidad());
                
                if (ing.getUnidad() != null) {
                    // Sacamos el nombre en texto (ej. "GRAMOS") del Dominio
                    String nombreUnidad = ing.getUnidad().name(); 
                    
                    // Lo convertimos al Enum del DTO
                    EnumeradoresDTO.TipoUnidad unidadDTO = EnumeradoresDTO.TipoUnidad.valueOf(nombreUnidad);
                    
                    dto.setUnidad(unidadDTO);
                }
                listaDTO.add(dto);
            }
            return listaDTO;
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar ingredientes");
        } finally {
            entitymanager.close();
        }
    }
}
