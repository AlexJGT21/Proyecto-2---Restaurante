/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantepersistencia;

import Conexion.ManejadorConexiones;
import Interfaces.IProductoDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import restaurantedominio.Producto;
import restaurantedtos.ProductoDTO;
import restaurantepersistencia.Adapter.NuevoProductoDTOAProductoAdapter;

/**
 *
 * @author Jaime
 */
public class ProductoDAO implements IProductoDAO {
    
    @Override
    public Producto crearProducto(ProductoDTO productoDTO) throws PersistenciaException {
        Producto producto = NuevoProductoDTOAProductoAdapter.adaptar(productoDTO);
        
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(producto);
            entityManager.getTransaction().commit();
            return producto;
        } catch (PersistenceException e) {
            throw new PersistenciaException("No se pudo registrar el producto");
        }
    }

    @Override
    public Producto buscarPorNombre(String nombre) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            TypedQuery<Producto> query = entityManager.createQuery(
            """
            SELECT p
            FROM Producto p
            WHERE p.nombre = :nombre
            """, Producto.class
            );
            
            query.setParameter("nombre", nombre);
            return query.getSingleResult();
            
        } catch (NoResultException ex) {
            return null; 
        } catch (Exception e) {
            throw new PersistenciaException("No se pudo consultar el producto: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> llenarTabla() throws PersistenciaException {
        try {
            EntityManager entityManager = Conexion.ManejadorConexiones.crearEntityManager();
            TypedQuery<Producto> query = entityManager.createQuery(
                    """
                    SELECT p 
                    FROM Producto p
                    """, Producto.class
                    );
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al intentar obtener los productos: " + e.getMessage());
        }
    }

    @Override
    public Producto buscarPorId(Long id) throws PersistenciaException {
        try {
            EntityManager entityManager = Conexion.ManejadorConexiones.crearEntityManager();
            
            // El método find busca directamente por la llave primaria (@Id)
            Producto producto = entityManager.find(Producto.class, id);
            
            return producto;
            
        } catch (Exception e) {
            throw new PersistenciaException("Error al intentar buscar el producto por ID: " + e.getMessage());
        }
    }

 
    
}
