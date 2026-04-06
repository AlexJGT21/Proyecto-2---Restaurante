
package restaurantedominio;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Alex García Trejo
 */
@Entity
@Table(name = "producto_ingredientes")
public class ProductoIngredientes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto productos;

    @ManyToOne
    @JoinColumn(name = "id_ingrediente", nullable = false)    
    private Ingrediente ingredientes;
    
    @Column(name = "cantidad", nullable = false)
    private BigDecimal cantidad;

    /**
     * Constructor por defecto para JPA.
     */
    public ProductoIngredientes() {
    }

    /**
     * Constructor que contiene un producto y sus ingredientes (con id)
     * @param id OBLIGATORIO
     * @param productos OBLIGATORIO
     * @param ingredientes OBLIGATORIO
     * @param cantidad OBLIGATORIO
     */
    public ProductoIngredientes(Long id, Producto productos, Ingrediente ingredientes, BigDecimal cantidad) {
        this.id = id;
        this.productos = productos;
        this.ingredientes = ingredientes;
        this.cantidad = cantidad;
    }

    /**
     * Constructor que contiene un productos y sus ingrediente (sin id)
     * @param productos OBLIGATORIO
     * @param ingredientes OBLIGATORIO
     * @param cantidad OBLIGATORIO
     */
    public ProductoIngredientes(Producto productos, Ingrediente ingredientes, BigDecimal cantidad) {
        this.productos = productos;
        this.ingredientes = ingredientes;
        this.cantidad = cantidad;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProductos() {
        return productos;
    }

    public void setProductos(Producto productos) {
        this.productos = productos;
    }

    public Ingrediente getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(Ingrediente ingredientes) {
        this.ingredientes = ingredientes;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
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
        if (!(object instanceof ProductoIngredientes)) {
            return false;
        }
        ProductoIngredientes other = (ProductoIngredientes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProductoIngredientes{" + "id=" + id + ", productos=" + productos + ", ingredientes=" + ingredientes + ", cantidad=" + cantidad + '}';
    }
}