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
import restaurantedominio.Producto;

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
    public void actualizarComanda(Comanda comandaActualizada) throws PersistenciaException {
        EntityManager entityManager = null;
        try {
            entityManager = Conexion.ManejadorConexiones.crearEntityManager();

            // Iniciamos la transacción porque vamos a modificar datos
            entityManager.getTransaction().begin();

            // Buscamos la comanda original en la BD para NO perder su Mesa, Cliente ni Folio
            Comanda comandaDB = entityManager.find(Comanda.class, comandaActualizada.getId());

            if (comandaDB == null) {
                throw new PersistenciaException("No se encontró la comanda a actualizar.");
            }

            // Le actualizamos el dinero)
            comandaDB.setTotalVenta(comandaActualizada.getTotalVenta());

            // Le actualizamos la lista de productos
            List<Producto> productosReales = new java.util.ArrayList<>();
            for (restaurantedominio.Producto pObj : comandaActualizada.getProductos()) {
                restaurantedominio.Producto pDB = entityManager.find(restaurantedominio.Producto.class, pObj.getId());
                if (pDB != null) {
                    productosReales.add(pDB);
                }
            }
            comandaDB.setProductos(productosReales);

            // 4. Guardamos los cambios
            entityManager.merge(comandaDB);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
      
            throw new restaurantepersistencia.PersistenciaException("Error al actualizar la comanda: " + e.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
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
     * Requerimiento: Módulo de Reportes. Permite obtener comandas para el
     * reporte de ventas por rango.
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

    @Override
    public java.util.List<restaurantedominio.Comanda> obtenerComandasActivas() throws restaurantepersistencia.PersistenciaException {
        javax.persistence.EntityManager entityManager = null;
        try {
            entityManager = Conexion.ManejadorConexiones.crearEntityManager();

            // Buscamos todas las comandas cuyo estado sea ABIERTA
            String jpql = "SELECT c FROM Comanda c WHERE c.estado = :estadoBuscado";
            javax.persistence.TypedQuery<restaurantedominio.Comanda> query = entityManager.createQuery(jpql, restaurantedominio.Comanda.class);

            query.setParameter("estadoBuscado", EnumeradoresDominio.EstadoComanda.ABIERTA);

            return query.getResultList();

        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar las comandas activas: " + e.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public restaurantedominio.Comanda consultarComanda(Long idComanda) throws restaurantepersistencia.PersistenciaException {
        EntityManager entityManager = null;
        try {
            entityManager = Conexion.ManejadorConexiones.crearEntityManager();
            restaurantedominio.Comanda comanda = entityManager.find(restaurantedominio.Comanda.class, idComanda);

            if (comanda == null) {
                throw new restaurantepersistencia.PersistenciaException("No se encontró ninguna comanda con el ID proporcionado.");
            }

            return comanda;

        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar la comanda: " + e.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

}
