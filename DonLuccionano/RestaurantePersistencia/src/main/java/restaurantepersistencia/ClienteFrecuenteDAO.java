
package restaurantepersistencia;

import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
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
}