
package restaurantepersistencia;

import Conexion.ManejadorConexiones;
import Interfaces.IClienteFrecuenteDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;
import restaurantedtos.ClienteFrecuenteReporteDTO;

/**
 *
 * @author Alex García Trejo
 */
public class ClienteFrecuenteDAO implements IClienteFrecuenteDAO {

    private static final Logger LOGGER = Logger.getLogger(ClienteFrecuenteDAO.class.getName());

    /**
     * Metodo que persiste una Entidad Cliente en la Base de datos
     *
     * @param clienteFrecuente Entidad por persistir
     * @return Cliente persistido en la BD
     * @throws PersistenciaException En caso de no poder persistir el cliente en
     * la BD
     */
    @Override
    public ClienteFrecuente crearCliente(ClienteFrecuenteDTO clienteFrecuente) throws PersistenciaException {
        ClienteFrecuente cliente = new ClienteFrecuente(clienteFrecuente.getNombre(),
                clienteFrecuente.getApellidoPaterno(),
                clienteFrecuente.getApellidoMaterno(),
                clienteFrecuente.getTelefono(),
                clienteFrecuente.getEmail(),
                clienteFrecuente.getFechaRegistro());
        try {
            //Creamos conexion
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            //Iniciamos transaccion (operacion)
            entityManager.getTransaction().begin();
            //Realizamos operacion de insercion (persistir una entidad)
            entityManager.persist(cliente);
            //Commit a la entidad (Se formaliza la operacion)
            entityManager.getTransaction().commit();
            return cliente;
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO SE PUDO REGISTRAR EL CLIENTE");
        }
    }

    /**
     * Metodo de busqueda de cliente por nombre, apellido paterno o apellido
     * materno
     *
     * @param nombreCliente OPCIONAL
     * @param apellidoPaterno OPCIONAL
     * @param apellidoMaterno OPCIONAL
     * @return Un cliente con datos correspondientes
     * @throws restaurantepersistencia.PersistenciaException Si el cliente no
     * pudo ser consultado
     */
    @Override
    public List<ClienteFrecuente> buscarClienteLista(String nombreCliente, String apellidoPaterno, String apellidoMaterno) throws PersistenciaException {
        try {
            //Gestiona el contexto de persistencia y permite interactuar con la base de datos
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            //Permite construir consultar dinamicas utilizando la API Criteria
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            //Representa la consulta que se va a construir (tipo ClienteFrecuente)
            CriteriaQuery<ClienteFrecuente> criteria = builder.createQuery(ClienteFrecuente.class);
            //Representa la entidad principal sobre la cual se realizara la consulta (equivalente a FROM de SQL)
            Root<ClienteFrecuente> clienteFreciente = criteria.from(ClienteFrecuente.class);

            boolean nombre = nombreCliente != null && !nombreCliente.trim().isEmpty();
            boolean apellidoPa = apellidoPaterno != null && !apellidoPaterno.trim().isEmpty();
            boolean apellidoMa = apellidoMaterno != null && !apellidoMaterno.trim().isEmpty();

            //Un monton de if
            if (nombre && apellidoPa && apellidoMa) {
                criteria.select(clienteFreciente).where(
                        builder.and(
                                builder.like(builder.lower(clienteFreciente.<String>get("nombre")), "%" + nombreCliente.trim().toLowerCase() + "%"),
                                builder.like(builder.lower(clienteFreciente.<String>get("apellidoPaterno")), "%" + apellidoPaterno.trim().toLowerCase() + "%"),
                                builder.like(builder.lower(clienteFreciente.<String>get("apellidoMaterno")), "%" + apellidoMaterno.trim().toLowerCase() + "%")
                        )
                );
            } else if (nombre && apellidoPa) {
                criteria.select(clienteFreciente).where(
                        builder.and(
                                builder.like(builder.lower(clienteFreciente.<String>get("nombre")), "%" + nombreCliente.trim().toLowerCase() + "%"),
                                builder.like(builder.lower(clienteFreciente.<String>get("apellidoPaterno")), "%" + apellidoPaterno.trim().toLowerCase() + "%")
                        )
                );
            } else if (nombre && apellidoMa) {
                criteria.select(clienteFreciente).where(
                        builder.and(
                                builder.like(builder.lower(clienteFreciente.<String>get("nombre")), "%" + nombreCliente.trim().toLowerCase() + "%"),
                                builder.like(builder.lower(clienteFreciente.<String>get("apellidoMaterno")), "%" + apellidoMaterno.trim().toLowerCase() + "%")
                        )
                );
            } else if (apellidoPa && apellidoMa) {
                criteria.select(clienteFreciente).where(
                        builder.and(
                                builder.like(builder.lower(clienteFreciente.<String>get("apellidoPaterno")), "%" + apellidoPaterno.trim().toLowerCase() + "%"),
                                builder.like(builder.lower(clienteFreciente.<String>get("apellidoMaterno")), "%" + apellidoMaterno.trim().toLowerCase() + "%")
                        )
                );
            } else if (nombre) {
                criteria.select(clienteFreciente).where(
                        builder.and(
                                builder.like(builder.lower(clienteFreciente.<String>get("nombre")), "%" + nombreCliente.trim().toLowerCase() + "%")
                        )
                );
            } else if (apellidoPa) {
                criteria.select(clienteFreciente).where(
                        builder.and(
                                builder.like(builder.lower(clienteFreciente.<String>get("apellidoPaterno")), "%" + apellidoPaterno.trim().toLowerCase() + "%")
                        )
                );
            } else if (apellidoMa) {
                criteria.select(clienteFreciente).where(
                        builder.and(
                                builder.like(builder.lower(clienteFreciente.<String>get("apellidoMaterno")), "%" + apellidoMaterno.trim().toLowerCase() + "%")
                        )
                );
            } else {
                criteria.select(clienteFreciente);
            }

            TypedQuery<ClienteFrecuente> query = entityManager.createQuery(criteria);
            return query.getResultList();
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO SE PUDO CONSULTAR AL CLIENTE");
        }
    }

    /**
     * Metodo de busqueda un cliente por su telefono
     *
     * @param telefono Argumento de busqueda
     * @return Cliente con datos correspondientes
     * @throws PersistenciaException Si no se pudo consultar el cliente
     */
    @Override
    public ClienteFrecuente buscarPorTelefono(String telefono) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            TypedQuery<ClienteFrecuente> query = entityManager.createQuery(
                    """
            SELECT c
            FROM ClienteFrecuente c
            WHERE c.numeroTelefonico = :telefono        
            """, ClienteFrecuente.class
            );
            query.setParameter("telefono", telefono);
            return query.getSingleResult();
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("No se pudo consultar el cliente por teléfono");
        }
    }

    /**
     * Metodo de busqueda a un cliente por correo
     *
     * @param correo Argumento de busqueda
     * @return Cliente con datos correspondientes
     * @throws PersistenciaException Si no se pudo consultar el cliente
     */
    @Override
    public ClienteFrecuente buscarPorCorreo(String correo) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            TypedQuery<ClienteFrecuente> query = entityManager.createQuery(
                    """
            SELECT c
            FROM ClienteFrecuente c
            WHERE c.correo = :correo
            """, ClienteFrecuente.class
            );
            query.setParameter("correo", correo);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("No se pudo consultar el cliente por correo");
        }
    }

    /**
     * Metodo que permite listar todos los clientes refrecuentes
     *
     * @return Lista de clientes frecuentes
     * @throws PersistenciaException No fue posible listar clientes frecuentes
     */
    @Override
    public List<ClienteFrecuenteDTO> listaClientesF() throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            List<ClienteFrecuente> query = entityManager.createQuery(
            """
            SELECT c
            FROM ClienteFrecuente c
            """, ClienteFrecuente.class).getResultList();
            List<ClienteFrecuenteDTO> resultado = new ArrayList<>();

            for (ClienteFrecuente cf : query) {
                ClienteFrecuenteDTO clienteDescifrado = new ClienteFrecuenteDTO(
                        cf.getNombre(),
                        cf.getApellidoPaterno(),
                        cf.getApellidoMaterno(),
                        cf.getNumeroTelefonico(),
                        cf.getCorreo(),
                        cf.getFechaRegistro());
                resultado.add(clienteDescifrado);
            }
            return resultado;
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO FUE POSIBLE LISTAR CLIENTES FRECUENTES.");
        }
    }

    @Override
    public ClienteFrecuente buscarPorId(long id) throws PersistenciaException {
        try {
            EntityManager entityManager = Conexion.ManejadorConexiones.crearEntityManager();

            ClienteFrecuente cliente = entityManager.find(ClienteFrecuente.class, id);

            return cliente; // Retornará el cliente si existe, o null si no se encuentra

        } catch (Exception e) {
            throw new PersistenciaException("Error al intentar buscar el cliente por ID: " + e.getMessage());
        }
    }

    @Override
    public ClienteFrecuente actualizarVisita(ClienteFrecuente clienteFrecuenteVisita) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            ClienteFrecuente cliente = entityManager.merge(clienteFrecuenteVisita);
            entityManager.getTransaction().commit();
            return cliente;            
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO FUE POSIBLE ACTUALIZAR LOS PUNTOS, TOTAL Y VISITAS DEL CLIENTE.");
        }
    }

    /**
     * Metodo que permite filtrar clientes frecuentes por nombre o numero de visitas
     * @param nombre Argumento de busqueda
     * @param visitas Argumnento de busqueds
     * @return Lista de clientes frecuentes
     * @throws PersistenciaException No se pudo realizar filtrado de clientes frecuentes
     */
    @Override
    public List<ClienteFrecuenteReporteDTO> filtrarClientes(String nombre, Integer visitas) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            TypedQuery<ClienteFrecuenteReporteDTO> query = entityManager.createQuery(
            """
            SELECT new restaurantedtos.ClienteFrecuenteReporteDTO (
            cf.nombre, cf.totalVisitas, cf.totalGastado, MAX(co.fecha))
            FROM Comanda co
            JOIN co.cliente cf
            WHERE (:nombre IS NULL OR LOWER(cf.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
            AND (:visitas IS NULL OR cf.totalVisitas = :visitas)
            GROUP BY cf.id, cf.nombre, cf.totalVisitas, cf.totalGastado
            """, ClienteFrecuenteReporteDTO.class);
            query.setParameter("nombre", nombre);
            query.setParameter("visitas", visitas);
            
            return query.getResultList();
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO FUE POSIBLE REALIZAR FILTRADO DE CLIENTES FRECUENTES.");
        }
    }
}
