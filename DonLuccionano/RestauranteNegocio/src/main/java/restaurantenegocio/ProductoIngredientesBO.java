/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantenegocio;

import Interfaces.IProductoIngredientesBO;
import Interfaces.IProductoIngredientesDAO;
import java.util.List;
import java.util.logging.Logger;
import restaurantedominio.ProductoIngredientes;
import restaurantepersistencia.PersistenciaException;
import restaurantepersistencia.ProductoIngredientesDAO;

/**
 *
 * @author Jaime
 */
public class ProductoIngredientesBO implements IProductoIngredientesBO{

    private static final Logger LOGGER = Logger.getLogger(ProductoIngredientesBO.class.getName());
    
    private final IProductoIngredientesDAO ProductoIngredientesDAO;
    
    public ProductoIngredientesBO() {
        this.ProductoIngredientesDAO = new ProductoIngredientesDAO(); 
    }

    @Override
        public ProductoIngredientes agregarProductoIngrediente(ProductoIngredientes pi) throws NegocioException {
        // Validar que el objeto principal no venga nulo
        if (pi == null) {
            throw new NegocioException("El registro no puede estar vacío.");
        }
        
        // Validar que traiga un Producto válido con su ID
        if (pi.getProductos() == null || pi.getProductos().getId() == null) {
            throw new NegocioException("Error: Se requiere un producto válido para asignarle la receta.");
        }
        
        // Validar que traiga un Ingrediente válido con su ID
        if (pi.getIngredientes() == null || pi.getIngredientes().getId() == null) {
            throw new NegocioException("Error: No se ha seleccionado un ingrediente válido.");
        }

        try {
            // Revisamos si el producto ya tiene este ingrediente exacto antes de guardarlo (evitar duplicados)
            List<ProductoIngredientes> recetaActual = ProductoIngredientesDAO.obtenerIngredientesPorProducto(pi.getProductos().getId());
            for (ProductoIngredientes existente : recetaActual) {
                if (existente.getIngredientes().getId().equals(pi.getIngredientes().getId())) {
                    throw new NegocioException("Este ingrediente ya fue agregado previamente a este producto.");
                }
            }
          
            return ProductoIngredientesDAO.agregarProductoIngrediente(pi);
            
        } catch (PersistenciaException e) {
            LOGGER.severe(e.getMessage());
            throw new NegocioException("Error en la base de datos al guardar el ingrediente: " + e.getMessage());
        }
    }

    @Override
    public List<ProductoIngredientes> obtenerIngredientesPorProducto(Long idProducto) throws NegocioException {
        // Validar que el ID sea valido
        if (idProducto == null || idProducto <= 0) {
            throw new NegocioException("El ID del producto proporcionado es inválido.");
        }
        
        try {
            return ProductoIngredientesDAO.obtenerIngredientesPorProducto(idProducto);
        } catch (PersistenciaException e) {
            LOGGER.severe(e.getMessage());
            throw new NegocioException("Error al consultar la receta del producto.");
        }
    }
    
}
