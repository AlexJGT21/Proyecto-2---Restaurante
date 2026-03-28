package restaurantepersistencia;

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

/**
 *
 * @author Alex García Trejo
 */
public class ClienteFrecuenteDAO implements IClienteFrecuenteDAO {

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

//    @Override
//    public List<ClienteFrecuente> buscarNombre(String nombreCliente) throws PersistenciaException {
//        try {
//            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
//            TypedQuery<ClienteFrecuente> query = entityManager.createQuery(
//                    """
//            SELECT c.nombre, c.apellido_paterno, c.apellido_materno, c.fecha_registro, c.numero_telefonico
//            FROM ClienteFrecuente c
//            WHERE c.nombre LIKE :nombreCliente
//            """, ClienteFrecuente.class);
//            query.setParameter("nombreCliente", nombreCliente);
//
//            List<ClienteFrecuente> clienteFrecuente = query.getResultList();
//            return clienteFrecuente;
//        } catch (PersistenceException e) {
//            LOGGER.severe(e.getMessage());
//            throw new PersistenciaException("NO SE PUDOO CONSULTAR EL CLIENTE FRECUENTE");
//        }
//    }

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
            """, ClienteFrecuente.class
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
    

    /**
     * Este metodo es busqueda pero en lista.
     * Es el que se usara
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
                                builder.like(builder.lower(clienteFreciente.get("nombre")), "%" + nombreCliente.trim().toLowerCase() + "%"),
                                builder.like(builder.lower(clienteFreciente.get("apellidoPaterno")), "%" + apellidoPaterno.trim().toLowerCase() + "%"),
                                builder.like(builder.lower(clienteFreciente.get("apellidoMaterno")), "%" + apellidoMaterno.trim().toLowerCase() + "%")
                        )
                );
            } else if (nombre && apellidoPa) {
                criteria.select(clienteFreciente).where(
                        builder.and(
                                builder.like(builder.lower(clienteFreciente.get("nombre")), "%" + nombreCliente.trim().toLowerCase() + "%"),
                                builder.like(builder.lower(clienteFreciente.get("apellidoPaterno")), "%" + apellidoPaterno.trim().toLowerCase() + "%")
                        )
                );                
            } else if (nombre && apellidoMa) {
                criteria.select(clienteFreciente).where(
                        builder.and(
                                builder.like(builder.lower(clienteFreciente.get("nombre")), "%" + nombreCliente.trim().toLowerCase() + "%"),
                                builder.like(builder.lower(clienteFreciente.get("apellidoMaterno")), "%" + apellidoMaterno.trim().toLowerCase() + "%")
                        )
                );
            } else if (apellidoPa && apellidoMa) {
                criteria.select(clienteFreciente).where(
                        builder.and(               
                                builder.like(builder.lower(clienteFreciente.get("apellidoPaterno")), "%" + apellidoPaterno.trim().toLowerCase() + "%"),
                                builder.like(builder.lower(clienteFreciente.get("apellidoMaterno")), "%" + apellidoMaterno.trim().toLowerCase() + "%")
                        )
                );
            } else if (nombre) {
                criteria.select(clienteFreciente).where(
                        builder.and(
                                builder.like(builder.lower(clienteFreciente.get("nombre")), "%" + nombreCliente.trim().toLowerCase() + "%")                
                        )
                );
            } else if (apellidoPa) {
                criteria.select(clienteFreciente).where(
                        builder.and(
                                builder.like(builder.lower(clienteFreciente.get("apellidoPaterno")), "%" + apellidoPaterno.trim().toLowerCase() + "%")
                        )
                );
            } else if (apellidoMa) {
                criteria.select(clienteFreciente).where(
                        builder.and(
                                builder.like(builder.lower(clienteFreciente.get("apellidoMaterno")), "%" + apellidoMaterno.trim().toLowerCase() + "%")
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
}
