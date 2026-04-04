
package restaurantepersistencia;

import Conexion.ManejadorConexiones;
import EnumeradoresDominio.Disponibilidad;
import Interfaces.IMesaDAO;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import restaurantedominio.Mesa;
import restaurantedtos.MesaDTO;
import restaurantepersistencia.Adapter.NuevaMesaDTOAMesaAdapter;

/**
 *
 * @author Alex García Trejo
 */
public class MesaDAO implements IMesaDAO {

    private static final Logger LOGGER = Logger.getLogger(MesaDAO.class.getName());    

    /**
     * Metodo que permite crear una mesa nueva
     * @param nuevaMesa Mesa nueva
     * @return Mesa nueva
     * @throws PersistenciaException En caso de no poder crear la mesa
     */
    @Override
    public Mesa registrarMesa(MesaDTO nuevaMesa) throws PersistenciaException {
        Mesa mesa = NuevaMesaDTOAMesaAdapter.adaptar(nuevaMesa);
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(mesa);
            entityManager.getTransaction().commit();
            return mesa;
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO FUE POSIBLE REGISTRAR LA MESA.");
        }
    }

    /**
     * Este metodo sera usado por "registrarMesa" en la capa de negocio para validad la cantidad total de mesas
     * siendo un total (de momento) de 20 en tota.
     * @return Cantidad actual de mesas creadas.
     * @throws PersistenciaException Si hubo un error al consultar cantidad actual de mesas
     */
    @Override
    public int consultarCantidad() throws PersistenciaException {
        try {
            EntityManager entitytManager = ManejadorConexiones.crearEntityManager();
            TypedQuery<Long> query = entitytManager.createQuery(
            """
            SELECT COUNT(m)
            FROM Mesa m
            """, Long.class);
            Long cantidad_actual = query.getSingleResult();
            return cantidad_actual.intValue();
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO SE PUDO CONSULTAR LA CANTIDAD DE MESAS ACTUALES.");
        }
    }

    /**
     * Metodo que consulta la existencia de una mes, esto es para saber si podemos crearla o no.
     * @param numMesa El numero de una mesa
     * @return true si la mesa existe, false en caso contrario
     * @throws PersistenciaException Si no fue posible consultar la mesa
     */
    @Override
    public boolean existeMesa(Integer numMesa) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            TypedQuery<Long> query = entityManager.createQuery(
            """
            SELECT COUNT(m)
            FROM Mesa m
            WHERE m.numMesa = :numMesa
            """, Long.class);
            query.setParameter("numMesa", numMesa);
            
            Long count = query.getSingleResult();
            return count > 0;
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO FUE POSIBLE CONSULTAR LA EXISTENCIA DE LA MESA.");
        }
    }

    /**
     * Metodo que permite listar todas las mesas independientemente si estan disponibles
     * @return Listado de todas las mesas
     * @throws PersistenciaException Si no fue posible realizar el listado de mesas
     */
    @Override
    public List<Mesa> listarMesas() throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            TypedQuery<Mesa> query = entityManager.createQuery(
            """
            SELECT m
            FROM Mesa m
            """, Mesa.class);
            return query.getResultList();
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO FUE POSIBLE LISTAR LAS MESAS.");
        }
    }

    /**
     * Metodo que permite cambiar el estado de disponiblidad de una mesa, puede ser en ambos sentidos
     * @param id Argumento de busqueda para cambiar el estado
     * @param disponibilidad Cambio de estado de disponibilidad de la mesa
     * @return Cambio de estado
     * @throws PersistenciaException Si no fue posible cambiar el estado de una mesa
     */
    @Override
    public Mesa cambiarDisponibilidad(Long id, Disponibilidad disponibilidad) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            
            Mesa mesa = entityManager.find(Mesa.class, id);
            if (mesa == null) {
                throw new PersistenciaException("LA MESA NO EXISTE.");
            }
            
            mesa.setDisponible(disponibilidad);
            entityManager.getTransaction().commit();
            return mesa;
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO FUE POSIBLE CAMBIAR EL ESTADO DE LA MESA.");
        }        
    }
}