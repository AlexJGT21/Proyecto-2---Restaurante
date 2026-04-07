
package restaurantedtos;

import java.time.LocalDate;

/**
 *
 * @author Alex García Trejo
 */
public class ClienteFrecuenteReporteDTO {
    
    private String nombre;
    private Integer totalVisitas;
    private Double totalGastado;
    private LocalDate fechaUltimaComanda;

    public ClienteFrecuenteReporteDTO(String nombre, Integer totalVisitas, Double totalGastado, LocalDate fechaUltimaComanda) {
        this.nombre = nombre;
        this.totalVisitas = totalVisitas;
        this.totalGastado = totalGastado;
        this.fechaUltimaComanda = fechaUltimaComanda;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getTotalVisitas() {
        return totalVisitas;
    }

    public Double getTotalGastado() {
        return totalGastado;
    }

    public LocalDate getFechaUltimaComanda() {
        return fechaUltimaComanda;
    }        
}