
package restaurantenegocio;

import Interfaces.IMesaBO;
import java.util.logging.Logger;
import restaurantedominio.Mesa;
import restaurantedtos.MesaDTO;
import restaurantepersistencia.MesaDAO;
import restaurantepersistencia.PersistenciaException;

/**
 *
 * @author maild
 */
public class MesaBO implements IMesaBO {

    private final MesaDAO mesaDAO;
    private static final Logger LOGGER = Logger.getLogger(MesaBO.class.getName());
    
    public MesaBO() {
        this.mesaDAO = new MesaDAO();
    }
    
    @Override
    public Mesa registrarMesa(MesaDTO nuevaMesa) throws NegocioException {
        //Validaciones de numero de mesa
        if (nuevaMesa.getNumMesa() == null) {
            throw new NegocioException("El número de mesa no puede ser nulo.");
        }
        if (nuevaMesa.getNumMesa() <= 0) {
            throw new NegocioException("El número de mesa no puede ser menor a 0.");
        }
        
        //Jerarquia de validaciones
        try {
            //Validacion de cantidad
            int cantidad = mesaDAO.consultarCantidad();
            if (cantidad > 20) {
                throw new NegocioException("NO SE PUEDEN CREAR MAS DE 20 MESAS.");
            }
            //Validacion de existencia
            boolean mesaDisponible = mesaDAO.existeMesa(nuevaMesa.getNumMesa());
            if (mesaDisponible) {
                throw new NegocioException("NO SE PUEDE CREAR MESA. YA EXISTE UNA CREADA.");
            }            
            Mesa mesa = mesaDAO.registrarMesa(nuevaMesa);        
            return mesa;
        } catch (PersistenciaException e) {
            LOGGER.severe(e.getMessage());
            throw new NegocioException("NO FUE POSIBLE REGISTRAR LA MESA.");
        }                        
    }
}