
package restaurantedominio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 *
 * @author Alex García Trejo
 */
// @MappedSuperclass: Esto es una base para las clases hijas, se pueden hacer consultas, pero la clase padre no existe en si, no se mapea en la BD
//Pero el riesgo es tener datos duplicados con mutiples tablas con los mismas columnas. USAR CON PRECAUCION
//@Entity
//@Table(name = "clientes")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "TIPO_CLIENTE")
//Este tipo de union crea una tabla unica, donde crea uan columna en la que cada clase hija que se extienda, se pondra
//El valor definido en las clases hijas, este caso "FRECUENTE" se pondra en la columna "TIPO_CLIENTE" que identifica
//A la clase hija
//NOTA: Se creara una tabla con los atributos del padre y la hija, con la diferencia que
//Existira una columna que distinguira si es general o frecuente
public abstract class Cliente implements Serializable {
    
    /**
     * ESTA CLASE SE DEJO PARA FUTURAS IMPLEMENTACIONES DE NUEVOS CLIENTES
     * PARA QUE LAS NUEVAS CLASES HIJAS SE DEBERA CAMBIAR EL MODO DE CREACION DE TABLAS DE CREATE A UPDATE
     * CON EL FIN DE AGREGAR LAS NUEVAS COLUMNAS DE SER NECESARIO Y NO PERDER DATOS
     */    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    
    @Column(name = "apellido_paterno", nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", nullable = false, length = 50)
    private String apellidoMaterno;
    
    @Column(name = "total_gastado")
    private Double totalGastado;

    public Cliente() {        
    }
    
    public Cliente(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, Double totalGastado) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.totalGastado = totalGastado;
    }

    public Cliente(String nombre, String apellidoPaterno, String apellidoMaterno, Double totalGastado) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.totalGastado = totalGastado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Double getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(Double totalGastado) {
        this.totalGastado = totalGastado;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", totalGastado=" + totalGastado + '}';
    }            
}