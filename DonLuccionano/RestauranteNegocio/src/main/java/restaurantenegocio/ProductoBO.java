/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantenegocio;

import Interfaces.IProductoBO;
import Interfaces.IProductoDAO;
import java.util.logging.Logger;
import restaurantedominio.Producto;
import restaurantedtos.ProductoDTO;
import restaurantepersistencia.PersistenciaException;
import restaurantepersistencia.ProductoDAO;

/**
 *
 * @author Jaime
 */
public class ProductoBO implements IProductoBO {

    private static final Logger LOGGER = Logger.getLogger(ProductoBO.class.getName());
    
    private final IProductoDAO productoDAO;
    
    public ProductoBO(){
        this.productoDAO = new ProductoDAO();
    }

    @Override
    public Producto crearProducto(ProductoDTO productoDTO) throws NegocioException {
        if (productoDTO == null) {
            throw new NegocioException("El producto no puede estar vacío.");
        }
        
        // Validaciones nombre
        if (productoDTO.getNombre() == null || productoDTO.getNombre().trim().isEmpty()) {
            throw new NegocioException("El nombre del producto es obligatorio.");
        }
        if (productoDTO.getNombre().length() > 50) {
            throw new NegocioException("El nombre no puede exceder los 50 caracteres.");
        }
        
        // Validaciones precio
        if (productoDTO.getPrecio() == null) {
            throw new NegocioException("El precio es obligatorio.");
        }
        if (productoDTO.getPrecio() <= 0) {
            throw new NegocioException("El precio debe ser mayor a 0.");
        }

        try {
            Producto existe = productoDAO.buscarPorNombre(productoDTO.getNombre());
            
            if (existe != null) {
                throw new NegocioException("No se puede ingresar producto, ya existe uno con ese nombre");
            }        
            
            Producto producto = productoDAO.crearProducto(productoDTO);
            return producto;
            
        } catch (PersistenciaException e) {
            LOGGER.severe(e.getMessage());
            throw new NegocioException("Error al registrar producto.");
        }
    }
    
    @Override
    public java.util.List<Producto> llenarTabla() throws NegocioException {
        try {
            return productoDAO.llenarTabla();
        } catch (PersistenciaException e) {
            LOGGER.severe(e.getMessage());
            throw new NegocioException("Error al cargar la tabla de productos.");
        }
    }
    
    @Override
    public Producto buscarPorNombre(String nombre) throws NegocioException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new NegocioException("El nombre del producto a buscar no puede estar vacío.");
        }
        
        try {
            return productoDAO.buscarPorNombre(nombre);
        } catch (PersistenciaException e) {
            LOGGER.severe(e.getMessage());
            throw new NegocioException("Error al intentar buscar el producto en la base de datos.");
        }
    }
}
