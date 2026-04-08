
package restaurantenegocio;

import Interfaces.IIngredienteBO;
import Interfaces.IIngredienteDAO;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;
import restaurantedominio.Ingrediente;
import EnumeradoresDominio.TipoUnidad;
import restaurantedtos.IngredienteActualizadoDTO;
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
    
    /**
     * Metodo que valida la entrada de datos al momento de registrar un nuevo ingrediente
     * @param nuevoIngredienteDTO Datos por validar
     * @return Nuevo ingrediente registrad
     * @throws NegocioException Esto se lanzara en caso de no poder registrar el nuevo ingrediente
     */
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
        if (nuevoIngredienteDTO.getNombre().matches("\\d+")) {
            throw new NegocioException("El nombre del ingrediente no puede ser numero.");
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
            //Convierte la unidad de DTO a DOMINIO
            EnumeradoresDominio.TipoUnidad unidad = EnumeradoresDominio.TipoUnidad.valueOf(nuevoIngredienteDTO.getUnidad().name());
            
            //Busca en la DAO
            Ingrediente existe = ingredienteDAO.buscarPorNombreYUnidad(nuevoIngredienteDTO.getNombre(), unidad);
            if (existe != null) {
                throw new NegocioException("NO SE PUEDE INGRESAR INGREDIENTE. YA EXISTE UNO IGUAL");
            }        
            
            Ingrediente ingrediente = ingredienteDAO.nuevoIngrediente(nuevoIngredienteDTO);
            return ingrediente;
        } catch (PersistenciaException e) {
            LOGGER.severe(e.getMessage());
            throw new NegocioException("Error al registrar ingrediente.");
        }
    }

    /**
     * Metodo que llama a la persistencia para llenar la tabla de ingredientes
     * @return Listado de ingredientes
     * @throws NegocioException Esto se lanzara en caso de no poder consultar la BD
     */
    @Override
    public List<Ingrediente> llenarTabla() throws NegocioException {
        try {
            List<Ingrediente> llenarTabla = ingredienteDAO.llenarTabla();
            return llenarTabla;
        } catch (PersistenciaException e) {
            LOGGER.severe(e.getMessage());
            throw new NegocioException("NO SE PUDO CONSULTAR LA BD PARA LLENAR LA TABLA");
        }        
    }    

    /**
     * Metodo que lista todos los ingredientes relacionados a un argumento de busqueda
     * @param nombreIngrediente Argumento de busqueda
     * @param unidadIngrediente Argumento de busqueda
     * @return Lista de ingredientes relacionados al argumento de busqueda
     * @throws NegocioException Si hubo un error al consultar ingredientes
     */
    @Override
    public List<Ingrediente> buscarPorNombreUnidad(String nombreIngrediente, TipoUnidad unidadIngrediente) throws NegocioException {
        try {
            List<Ingrediente> lista = ingredienteDAO.buscarPorNombreUnidad(nombreIngrediente, unidadIngrediente);
            return lista;
        } catch (PersistenciaException e) {
            LOGGER.severe(e.getMessage());
            throw new NegocioException("ERROR AL BUSCAR INGREDIENTE");
        }
    }

    /**
     * Metodo que permite inventariar ingrediente(s)
     * @param ingredienteInventario Ingrediente(s) el cual se va a inventariar
     * @return Nuevo inventario de ingrediente(s)
     * @throws NegocioException Si no fue posible actualizar el inventario del ingrediente(s)
     */
    @Override
    public Ingrediente inventariarIngrediente(IngredienteActualizadoDTO ingredienteInventario) throws NegocioException {        
        if (ingredienteInventario.getId() == null) {
            throw new NegocioException("Ningun ingrediente seleccionado. Seleccione uno para actualizar");           
        }
        
        //Validaciones por cantidad
        if (ingredienteInventario.getCantidad() == null) {
            throw new NegocioException("La cantidad del ingrediente no puede ser nula");
        }
        if (ingredienteInventario.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegocioException("La cantidad no puede ser negativa");
        }
        if (ingredienteInventario.getCantidad().scale() > 2) {
            throw new NegocioException("La cantidad no puede tener mas de dos decimales");
        }
        
        try {
            Ingrediente ingredienteActualizar = ingredienteDAO.inventariarIngrediente(ingredienteInventario);
            return ingredienteActualizar;
        } catch (PersistenciaException e) {
            LOGGER.severe(e.getMessage());
            throw new NegocioException("FALLO EN LA ACTUALIZACIÓN DE INGREDIENTE");
        }        
    }

    @Override
    public List<IngredienteDTO> consultarTodosLosIngredientes() throws Exception {
        try {
            return ingredienteDAO.consultarTodosLosIngredientes();
        } catch (Exception e) {
            throw new restaurantenegocio.NegocioException("Error al obtener la lista de ingredientes.");
        }
    }
}