
package restaurantedtos;

import java.math.BigDecimal;

/**
 *
 * @author Alex García Trejo
 */
public class IngredienteDTO {
    
    private String nombre;
    private TipoUnidad unidad;
    private BigDecimal cantidad;

    public IngredienteDTO(String nombre, TipoUnidad unidad, BigDecimal cantidad) {
        this.nombre = nombre;
        this.unidad = unidad;
        this.cantidad = cantidad;
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
}