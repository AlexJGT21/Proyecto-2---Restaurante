/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import restaurantedominio.Producto;
import restaurantedtos.ProductoDTO;
import restaurantenegocio.NegocioException;

/**
 *
 * @author Jaime
 */
public interface IProductoBO {
    public Producto crearProducto(ProductoDTO productoDTO) throws NegocioException;
    
}
