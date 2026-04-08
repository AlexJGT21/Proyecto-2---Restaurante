package restaurantedtos;

import EnumeradoresDTO.TipoUnidad;
import java.math.BigDecimal;

/**
 *
 * @author Alex García Trejo
 */
public class IngredienteDTO {
    
    private Long id;
    private String nombre;
    private TipoUnidad unidad;
    private BigDecimal cantidad;
    private byte[] imagen;

    public IngredienteDTO() {
    }
  
    /**
     * CONSTRUCTOR NUEVO: Sin imagen (Opcional). 
     * Ideal para las pruebas y cuando el usuario decide no subir foto.
     */
    public IngredienteDTO(String nombre, TipoUnidad unidad, BigDecimal cantidad) {
        this.nombre = nombre;
        this.unidad = unidad;
        this.cantidad = cantidad;
    }

    /**
     * CONSTRUCTOR ORIGINAL: Con imagen.
     */
    public IngredienteDTO(String nombre, TipoUnidad unidad, BigDecimal cantidad, byte[] imagen) {
        this.nombre = nombre;
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.imagen = imagen;
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
}