
package restaurantedtos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Alex García Trejo
 */
public class ClienteFrecuenteReporteDTO {
    
    private String nombre;
    private Integer totalVisitas;
    private Double totalGastado;
    private LocalDateTime fechaUltimaComanda;

    public ClienteFrecuenteReporteDTO(String nombre, Integer totalVisitas, Double totalGastado, LocalDateTime fechaUltimaComanda) {
        this.nombre = nombre;
        this.totalVisitas = totalVisitas;
        this.totalGastado = totalGastado;
        this.fechaUltimaComanda = fechaUltimaComanda;
    }   
        
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTotalVisitas() {
        return totalVisitas;
    }

    public void setTotalVisitas(Integer totalVisitas) {
        this.totalVisitas = totalVisitas;
    }

    public Double getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(Double totalGastado) {
        this.totalGastado = totalGastado;
    }

    public LocalDateTime getFechaUltimaComanda() {
        return fechaUltimaComanda;
    }

    public void setFechaUltimaComanda(LocalDateTime fechaUltimaComanda) {
        this.fechaUltimaComanda = fechaUltimaComanda;
    }
    
    public String getFechaFormato() {
        if (fechaUltimaComanda == null) {
            return "";
        }
        return fechaUltimaComanda.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    
    
}