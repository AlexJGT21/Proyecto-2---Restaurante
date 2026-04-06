
package restaurantedtos;

import java.math.BigDecimal;

/**
 *
 * @author Alex García Trejo
 */
public class ProductoIngredientesDTO {
    
    private Long idProducto;
    private Long idIngrediente;
    private BigDecimal cantidad;

    public ProductoIngredientesDTO(Long idProducto, Long idIngrediente, BigDecimal cantidad) {
        this.idProducto = idProducto;
        this.idIngrediente = idIngrediente;
        this.cantidad = cantidad;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public Long getIdIngrediente() {
        return idIngrediente;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }
}