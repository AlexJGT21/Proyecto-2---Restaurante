
package restaurantedtos;

import java.math.BigDecimal;

/**
 *
 * @author Alex García Trejo
 */
public class IngredienteActualizadoDTO {
    
    private Long id;
    private BigDecimal cantidad;

    public IngredienteActualizadoDTO(Long id, BigDecimal cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }       
}