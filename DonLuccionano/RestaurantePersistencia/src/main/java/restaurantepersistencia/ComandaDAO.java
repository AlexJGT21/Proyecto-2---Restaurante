package restaurantepersistencia;

import Conexion.ManejadorConexiones;
import Interfaces.IComandaDAO;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import restaurantedominio.Comanda;

/**
 * @author JAR (JAIME, ALEJANDRO, ROBERTO)
 */
public class ComandaDAO implements IComandaDAO {

    private static final Logger LOGGER = Logger.getLogger(ComandaDAO.class.getName());

    @Override
    public Comanda registrarComanda(Comanda comanda) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(comanda);
            entityManager.getTransaction().commit();
            return comanda;
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO FUE POSIBLE REGISTRAR LA COMANDA.");
        }
    }

    @Override
    public void actualizarComanda(Comanda comanda) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(comanda);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO SE PUDO ACTUALIZAR LA COMANDA.");
        }
    }

    @Override
    public Comanda buscarPorFolio(String folio) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            TypedQuery<Comanda> query = entityManager.createQuery(
            """
            SELECT c 
            FROM Comanda c 
            WHERE c.folio = :folio
            """, Comanda.class);
            query.setParameter("folio", folio);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("ERROR AL BUSCAR COMANDA POR FOLIO.");
        }
    }

    @Override
    public Long consultarCantidadPorDia(LocalDate fecha) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            TypedQuery<Long> query = entityManager.createQuery(
            """
            SELECT COUNT(c) 
            FROM Comanda c 
            WHERE c.fecha = :fecha
            """, Long.class);
            query.setParameter("fecha", fecha);
            return query.getSingleResult();
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("ERROR AL CONSULTAR CONTEO DIARIO.");
        }
    }

    /**
     * Requerimiento: Módulo de Reportes.
     * Permite obtener comandas para el reporte de ventas por rango.
     */
    @Override
    public List<Comanda> consultarPorRangoFechas(LocalDate inicio, LocalDate fin) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            TypedQuery<Comanda> query = entityManager.createQuery(
            """
            SELECT c 
            FROM Comanda c 
            WHERE c.fecha BETWEEN :inicio AND :fin
            ORDER BY c.fecha ASC, c.folio ASC
            """, Comanda.class);
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            return query.getResultList();
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("ERROR AL GENERAR LISTA PARA REPORTE.");
        }
    }

    @Override
    public Comanda buscarPorId(Long id) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            return entityManager.find(Comanda.class, id);
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("ERROR AL BUSCAR COMANDA POR ID.");
        }
    }

    @Override
    public List<Comanda> consultarPorCliente(Long idCliente) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            TypedQuery<Comanda> query = entityManager.createQuery(
            """
            SELECT c 
            FROM Comanda c 
            WHERE c.cliente.id = :idCliente
            """, Comanda.class);
            query.setParameter("idCliente", idCliente);
            return query.getResultList();
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("ERROR AL CONSULTAR HISTORIAL DEL CLIENTE.");
        }
    }
}