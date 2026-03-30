
package restaurantenegocio;

import Interfaces.IIngredienteBO;
import Interfaces.IIngredienteDAO;
import java.math.BigDecimal;
import java.util.logging.Logger;
import restaurantedominio.Ingrediente;
import restaurantedtos.IngredienteDTO;
import restaurantepersistencia.IngredienteDAO;
import restaurantepersistencia.PersistenciaException;

/**
 *
 * @author Alex García Trejo
 */
public class IngredienteBO implements IIngredienteBO {

    private static final Logger LOGGER = Logger.getLogger(IngredienteBO.class.getName());   
    
    private final IIngredienteDAO ingredienteDAO;
    
    public IngredienteBO() {
        ingredienteDAO = new IngredienteDAO();
    }
    
    @Override
    public Ingrediente nuevoIngrediente(IngredienteDTO nuevoIngredienteDTO) throws NegocioException {
        if (nuevoIngredienteDTO == null) {
            throw new NegocioException("El ingrediente debe tener atributos para poder ser registrado. No debe tener campos vacios.");
        }
        
        //Validaciones por nombre
        if (nuevoIngredienteDTO.getNombre() == null) {
            throw new NegocioException("El nombre del ingrediente no puede ser nulo");
        }
        if (nuevoIngredienteDTO.getNombre().trim().isEmpty()) {
            throw new NegocioException("El nombre del ingrediente no puede estar vacio");
        }
        if (nuevoIngredienteDTO.getNombre().length() > 20) {
            throw new NegocioException("El nombre del ingrediente es muy largo. Excede los 20 caracteres");
        }
        
        //Validaciones por cantidad
        if (nuevoIngredienteDTO.getCantidad() == null) {
            throw new NegocioException("La cantidad del ingrediente no puede ser nula");
        }
        if (nuevoIngredienteDTO.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegocioException("La cantidad no puede ser negativa");
        }
        if (nuevoIngredienteDTO.getCantidad().scale() > 2) {
            throw new NegocioException("La cantidad no puede tener mas de dos decimales");
        }
        
        //Validaciones de unidad
        if (nuevoIngredienteDTO.getUnidad() == null) {
            throw new NegocioException("La unidad no puede ser nula.");
        }
        
        try {
            Ingrediente ingrediente = ingredienteDAO.nuevoIngrediente(nuevoIngredienteDTO);
            return ingrediente;
        } catch (PersistenciaException e) {
            LOGGER.severe(e.getMessage());
            throw new NegocioException("Error al registrar ingrediente.");
        }
    }
    
}