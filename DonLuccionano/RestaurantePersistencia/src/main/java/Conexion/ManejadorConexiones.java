
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Alex García Trejo
 */
public class ManejadorConexiones {
    
    //Este metodo se crea para crear "conexiones" de EntityManager 
    public static EntityManager crearEntityManager() {
        //Entrar a dominio - Other Sources, y buscar el .xml, entrar y copiar y pegar el nombre aquí
        //Fabrica que permite construir entityManager a partir de ciertas configuraciones
        EntityManagerFactory entityMF = Persistence.createEntityManagerFactory("RestauranteDominio");
        //Objeto  que permite hacer operaciones de BD
        EntityManager entityManager = entityMF.createEntityManager();        
        //Regresa una conexion
        return entityManager;
    }
    
    public static Connection crearConexionJDBC() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/restaurantebd";
        String usser = "root";
        String password = "SQLLUCI";
        return DriverManager.getConnection(url,usser, password);
    }
}