/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import java.util.List;
import restaurantedominio.ProductoIngredientes;
import restaurantepersistencia.PersistenciaException;

/**
 *
 * @author Jaime
 */
public interface IProductoIngredientesDAO {
    public ProductoIngredientes agregarProductoIngrediente(ProductoIngredientes pi) throws PersistenciaException;
    
    public List<ProductoIngredientes> obtenerIngredientesPorProducto(Long idProducto) throws PersistenciaException;
    
}
