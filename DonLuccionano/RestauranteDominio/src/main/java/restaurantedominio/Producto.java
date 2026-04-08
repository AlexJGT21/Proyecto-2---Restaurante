package restaurantedominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author JAR
 */
@Entity
@Table(name = "productos")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;
    
    @Column(name = "nombre", nullable = false, unique = true, length = 50) 
    private String nombre;
    
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    
    @Column(name = "tipo", nullable = false, length = 30) 
    private String tipo;
    
    @Column(name = "precio", nullable = false)
    private Float precio;
    
    @Column(name = "activo", nullable = false)
    private boolean activo; 
    
    @Lob
    @Column(name = "imagen", columnDefinition = "MEDIUMBLOB", nullable = true)
    private byte[] imagen;

    // LA RELACIÓN CON LA CLASE INTERMEDIA PRODUCTO_INGREDIENTES
    @OneToMany(mappedBy = "productos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoIngredientes> listaIngredientes;

    public Producto() {
        this.activo = true; // Por defecto está activo
        this.listaIngredientes = new ArrayList<>();
    }

    public Producto(String nombre, String descripcion, String tipo, Float precio, boolean activo) {
        this();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
        this.activo = activo;
    }

    // GETTERS Y SETTERS
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
    
    public String getDescripcion() {
        return descripcion; 
    }
    
    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion; 
    }
    
    public String getTipo() { 
        return tipo; 
    }
    
    public void setTipo(String tipo) { 
        this.tipo = tipo; 
    }
    
    public Float getPrecio() { 
        return precio; 
    }
    
    public void setPrecio(Float precio) { 
        this.precio = precio; 
    }
    
    public boolean isActivo() { 
        return activo; 
    }
    
    public void setActivo(boolean activo) { 
        this.activo = activo;
    }
    
    public byte[] getImagen() { 
        return imagen;
    }
    
    public void setImagen(byte[] imagen) { 
        this.imagen = imagen;
    }
    
    // GETTER Y SETTER ACTUALIZADOS
    public List<ProductoIngredientes> getListaIngredientes() { 
        return listaIngredientes; 
    }
    
    public void setListaIngredientes(List<ProductoIngredientes> listaIngredientes) { 
        this.listaIngredientes = listaIngredientes; 
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Producto)) return false;
        Producto other = (Producto) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "Producto{" + "nombre=" + nombre + ", activo=" + activo + '}';
    }
}