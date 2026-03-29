
package restaurantedtos;

/**
 *
 * @author Alex García Trejo
 */
public class IngredienteDTO {
    
    private String nombre;
    private TipoUnidad unidad;
    private Double cantidad;

    public IngredienteDTO(String nombre, TipoUnidad unidad, Double cantidad) {
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

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }
}