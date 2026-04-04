/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantepersistencia.Adapter;

import restaurantedominio.Producto;
import restaurantedtos.ProductoDTO;

/**
 *
 * @author Jaime
 */
public class NuevoProductoDTOAProductoAdapter {
    
    public static Producto adaptar(ProductoDTO nuevoProducto) {
        return new Producto(nuevoProducto.getNombre(), nuevoProducto.getPrecio(), nuevoProducto.getImagen());
    }
}
