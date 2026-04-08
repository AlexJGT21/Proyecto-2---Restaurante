/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantepersistencia;

import Conexion.ManejadorConexiones;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import restaurantedominio.ProductoIngredientes;
import Interfaces.IProductoIngredientesDAO;

/**
 *
 * @author Jaime
 */
public class ProductoIngredientesDAO implements IProductoIngredientesDAO {

    @Override
    public ProductoIngredientes agregarProductoIngrediente(ProductoIngredientes pi) throws PersistenciaException {
        EntityManager entitymanager = ManejadorConexiones.crearEntityManager();
        try {
            entitymanager.getTransaction().begin();
            entitymanager.persist(pi);
            entitymanager.getTransaction().commit();
            return pi;
        } catch (Exception e) {
            entitymanager.getTransaction().rollback();
            throw new PersistenciaException("Error al conectar el ingrediente al producto: " + e.getMessage());
        } finally {
            entitymanager.close();
        }
    }

    @Override
    public List<ProductoIngredientes> obtenerIngredientesPorProducto(Long idProducto) throws PersistenciaException {
        EntityManager em = ManejadorConexiones.crearEntityManager();
        try {
            TypedQuery<ProductoIngredientes> query = em.createQuery(
                """
                SELECT pi 
                FROM ProductoIngredientes pi 
                WHERE pi.productos.id = :idProd
                """
                ,ProductoIngredientes.class);
            query.setParameter("idProd", idProducto);
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al cargar la receta.");
        } finally {
            em.close();
        }
    }
    
}
