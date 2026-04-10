
package restaurantedtos;

import EnumeradoresDTO.EstadoComandaDTO;
import java.time.LocalDateTime;

/**
 *
 * @author Alex García Trejo
 */
public class ReporteComandaDTO {
    
    private String nombre;
    private EstadoComandaDTO estado;
    private LocalDateTime fecha;
    private Long totalVenta;

    public ReporteComandaDTO(String nombre, EstadoComandaDTO estado, LocalDateTime fecha, Long totalVenta) {
        this.nombre = nombre;
        this.estado = estado;
        this.fecha = fecha;
        this.totalVenta = totalVenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EstadoComandaDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoComandaDTO estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Long getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Long totalVenta) {
        this.totalVenta = totalVenta;
    }    
}