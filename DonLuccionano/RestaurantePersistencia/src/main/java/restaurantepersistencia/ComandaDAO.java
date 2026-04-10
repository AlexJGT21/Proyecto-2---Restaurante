package restaurantepersistencia;

import Conexion.ManejadorConexiones;
import Interfaces.IComandaDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import restaurantedominio.Comanda;
import restaurantedtos.ReporteComandaDTO;

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
    public void actualizarComanda(restaurantedominio.Comanda comandaActualizada) throws restaurantepersistencia.PersistenciaException {
        javax.persistence.EntityManager entityManager = null;
        try {
            entityManager = Conexion.ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();

            restaurantedominio.Comanda comandaDB = entityManager.find(restaurantedominio.Comanda.class, comandaActualizada.getId());

            if (comandaDB == null) {
                throw new restaurantepersistencia.PersistenciaException("No se encontró la comanda a actualizar.");
            }

            comandaDB.setTotalVenta(comandaActualizada.getTotalVenta());

            // Limpiamos los detalles viejos
            comandaDB.getDetalles().clear();

            // Agregamos los nuevos detalles
            for (restaurantedominio.DetalleComanda detActualizado : comandaActualizada.getDetalles()) {
                restaurantedominio.DetalleComanda nuevoDetalle = new restaurantedominio.DetalleComanda();
                nuevoDetalle.setCantidad(detActualizado.getCantidad());
                nuevoDetalle.setSubtotal(detActualizado.getSubtotal());

                // Buscamos el producto real
                restaurantedominio.Producto pDB = entityManager.find(restaurantedominio.Producto.class, detActualizado.getProducto().getId());
                nuevoDetalle.setProducto(pDB);

                // Enlazamos
                nuevoDetalle.setComanda(comandaDB);
                comandaDB.getDetalles().add(nuevoDetalle);
            }

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
            // Te cambie esta consulta para que funcione con los cambios que hice, ahora revisa el dia pero le indique que por rango de horas
            LocalDateTime inicioDia = fecha.atStartOfDay(); // 00:00:00
            LocalDateTime finDia = fecha.atTime(23, 59, 59, 999999999); // 23:59:59.999...

            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            TypedQuery<Long> query = entityManager.createQuery(
                    """
            SELECT COUNT(c) 
            FROM Comanda c 
            WHERE c.fecha BETWEEN :inicio AND :fin
            """, Long.class); // Cambie "=" por "BETWEEN"
            
            query.setParameter("inicio", inicioDia);
            query.setParameter("fin", finDia);
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
            // Aqui hoce lo mismo que en el otro metodo
            LocalDateTime inicioRango = inicio.atStartOfDay();
            LocalDateTime finRango = fin.atTime(23, 59, 59, 999999999);

            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            TypedQuery<Comanda> query = entityManager.createQuery(
                    """
            SELECT c 
            FROM Comanda c 
            WHERE c.fecha BETWEEN :inicio AND :fin
            ORDER BY c.fecha ASC, c.folio ASC
            """, Comanda.class);
            
            query.setParameter("inicio", inicioRango);
            query.setParameter("fin", finRango);
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

    @Override
    public void cambiarEstado(Long idComanda, EnumeradoresDominio.EstadoComanda nuevoEstado) throws PersistenciaException {
        javax.persistence.EntityManager entityManager = null;
        try {
            entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();

            
            restaurantedominio.Comanda comandaDB = entityManager.find(restaurantedominio.Comanda.class, idComanda);
            if (comandaDB != null) {
                // Solo le modificamos el estado
                comandaDB.setEstado(nuevoEstado);
                entityManager.merge(comandaDB);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al cambiar estado: " + e.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public List<ReporteComandaDTO> generarReporteComanda(LocalDateTime fechaInicio, LocalDateTime fechaFin) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            TypedQuery<ReporteComandaDTO> query = entityManager.createQuery(
            """
            SELECT new restaurantedtos.ReporteComandaDTO
            (cf.nombre, co.estado, co.fecha, co.totalVenta)
            FROM Comanda co
            LEFT JOIN co.cliente cf
            WHERE co.fecha >= :fechaInicio AND co.fecha < :fechaFin
            """, ReporteComandaDTO.class);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);            
            return query.getResultList();            
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO FUE POSIBLE GENERAR LA CONSULTA PARA EL REPORTE.");
        }
    }
}
