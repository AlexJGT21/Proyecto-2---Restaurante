
package restaurantedominio;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author Alex García Trejo
 */
@Entity
@Table(name = "ingredientes")
public class Ingrediente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ingrediente", nullable = false)
    private Long id;
    
    @Column(name = "nombre_ingrediente", nullable = false, length = 30)
    private String nombre;

    @Column(name = "unidad" , nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TipoUnidad unidad;
    
    @Column(name = "cantidad_actual", precision = 10, scale = 2, nullable = false)
    private BigDecimal cantidad;
    
    /**
     * Atributo para cargar la imagen en mi ingrediente
     * SE CAMBIO A OPCIONAL PARA PODER INGRESAR O NO LA IMAGEN
     */
    @Lob
    @Column(name = "imagen_producto", columnDefinition = "LONGBLOB", nullable = true)
    private byte[] imagen;    

    /**
     * Constructor por defecto para JPA.
     */
    public Ingrediente() {
    }

    /**
     * Constructor que crea un ingrediente con todos sus atributos
     * @param id OBLIGATORIO
     * @param nombre OBLIGATORIO
     * @param unidad OBLIGATORIO
     * @param cantidad OBLIGATORIO
     */    
    public Ingrediente(Long id, String nombre, TipoUnidad unidad, BigDecimal cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.unidad = unidad;
        this.cantidad = cantidad;
    }

    /**
     * Constructor que crea un ingrediente con nombre y unidad
     * @param nombre OBLIGATORIO
     * @param unidad OBLIGATORIO
     */
    public Ingrediente(String nombre, TipoUnidad unidad) {
        this.nombre = nombre;
        this.unidad = unidad;
    }

    /**
     * Constructor que crea un ingrediente con nombre, unidad y cantidad
     * @param nombre OBLIGATORIO
     * @param unidad OBLIGATORIO
     * @param cantidad OBLIGATORIO
     */
    public Ingrediente(String nombre, TipoUnidad unidad, BigDecimal cantidad) {
        this.nombre = nombre;
        this.unidad = unidad;
        this.cantidad = cantidad;
    }

    /**
     * Constructor que crea un ingrediente, con una imagen
     * @param nombre OBLIGATORIO
     * @param unidad OBLIGATORIO
     * @param cantidad OBLIGATORIO
     * @param imagen OPCIONAL
     */
    public Ingrediente(String nombre, TipoUnidad unidad, BigDecimal cantidad, byte[] imagen) {
        this.nombre = nombre;
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.imagen = imagen;
    }
    
    //GETTERS AND SETTERS
    
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

    public TipoUnidad getUnidad() {
        return unidad;
    }

    public void setUnidad(TipoUnidad unidad) {
        this.unidad = unidad;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }     

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }        

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingrediente)) {
            return false;
        }
        Ingrediente other = (Ingrediente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ingrediente{" + "id=" + id + ", nombre=" + nombre + ", unidad=" + unidad + ", cantidad=" + cantidad + '}';
    }
}