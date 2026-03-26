package restaurantepersistencia;

import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;

/**
 *
 * @author Alex García Trejo
 */
public class ClienteFrecuenteDAO implements IClienteFrecuente {

    private static final Logger LOGGER = Logger.getLogger(ClienteFrecuenteDAO.class.getName());

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

    @Override
    public List<ClienteFrecuente> buscarNombre(String nombreCliente) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            TypedQuery<ClienteFrecuente> query = entityManager.createQuery(
                    """
            SELECT c.nombre, c.apellido_paterno, c.apellido_materno, c.fecha_registro, c.numero_telefonico
            FROM ClienteFrecuente c
            WHERE c.nombre LIKE :nombreCliente
            """, ClienteFrecuente.class);
            query.setParameter("nombreCliente", nombreCliente);

            List<ClienteFrecuente> clienteFrecuente = query.getResultList();
            return clienteFrecuente;
        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("NO SE PUDOO CONSULTAR EL CLIENTE FRECUENTE");
        }
    }

    @Override
    public List<ClienteFrecuente> numeroCliente(String numeroCliente) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ClienteFrecuente> emailCliente(String emailCliente) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ClienteFrecuente buscarCliente(String nombre, String apellidoP, String apellidoM) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();

            TypedQuery<ClienteFrecuente> query = entityManager.createQuery(
                    """
            SELECT c
            FROM ClienteFrecuente c
            WHERE c.nombre = :nombre
            AND c.apellidoPaterno = :apellidoP
            AND c.apellidoMaterno = :apellidoM
            """,
                    ClienteFrecuente.class
            );

            query.setParameter("nombre", nombre);
            query.setParameter("apellidoP", apellidoP);
            query.setParameter("apellidoM", apellidoM);

            return query.getSingleResult();

        } catch (NoResultException e) {
            return null;

        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("No se pudo consultar el cliente frecuente");
        }
    }

    @Override
    public ClienteFrecuente buscarPorTelefono(String telefono) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();

            TypedQuery<ClienteFrecuente> query = entityManager.createQuery(
                    """
            SELECT c
            FROM ClienteFrecuente c
            WHERE c.numeroTelefonico = :telefono
            """,
                    ClienteFrecuente.class
            );

            query.setParameter("telefono", telefono);

            return query.getSingleResult();

        } catch (NoResultException e) {
            return null; // cuando no eziste 

        } catch (PersistenceException e) {
            LOGGER.severe(e.getMessage());
            throw new PersistenciaException("No se pudo consultar el cliente por teléfono");
        }
    }

    @Override
    public ClienteFrecuente buscarPorCorreo(String correo) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();

            TypedQuery<ClienteFrecuente> query = entityManager.createQuery(
                    """
            SELECT c
            FROM ClienteFrecuente c
            WHERE c.correo = :correo
            """,
                    ClienteFrecuente.class
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
}
