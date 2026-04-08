/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import java.util.List;
import restaurantedominio.Producto;
import restaurantedtos.ProductoDTO;
import restaurantepersistencia.PersistenciaException;

/**
 *
 * @author Jaime
 */
public interface IProductoDAO {
    // Contrato 1: Permite crear un nuevo producto
    public Producto crearProducto(ProductoDTO productoDTO) throws PersistenciaException;
    
    // Contrato 2: Permite buscar un producto por su nombre
    public Producto buscarPorNombre(String nombre) throws PersistenciaException;
    
    //Contrato 3: Este metodo sirve para llenar la tabla de productos
    public abstract List<Producto> llenarTabla() throws PersistenciaException;

    public abstract Producto buscarPorId(Long id) throws PersistenciaException;
    
    public abstract List<Producto> obtenerProductos() throws PersistenciaException;

}
