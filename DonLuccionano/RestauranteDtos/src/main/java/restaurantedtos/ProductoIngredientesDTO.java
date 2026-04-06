package restaurantedtos;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductoIngredientesDTO implements Serializable {

    private Long idIngrediente; // Para saber qué ingrediente buscar en la BD
    private String nombreIngrediente; // Para mostrarlo en la JTable de NetBeans fácilmente
    private BigDecimal cantidadRequerida; // Lo que lleva la receta (ej. 2.5 gramos)

    public ProductoIngredientesDTO() {}

    public ProductoIngredientesDTO(Long idIngrediente, String nombreIngrediente, BigDecimal cantidadRequerida) {
        this.idIngrediente = idIngrediente;
        this.nombreIngrediente = nombreIngrediente;
        this.cantidadRequerida = cantidadRequerida;
    }

    public Long getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Long idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getNombreIngrediente() {
        return nombreIngrediente;
    }

    public void setNombreIngrediente(String nombreIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
    }

    public BigDecimal getCantidadRequerida() {
        return cantidadRequerida;
    }

    public void setCantidadRequerida(BigDecimal cantidadRequerida) {
        this.cantidadRequerida = cantidadRequerida;
    }

    
}