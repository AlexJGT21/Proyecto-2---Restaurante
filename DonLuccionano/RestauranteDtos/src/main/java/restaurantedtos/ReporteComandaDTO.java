
package restaurantedtos;

import java.time.LocalDateTime;
import EnumeradoresDominio.EstadoComanda;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Alex García Trejo
 */
public class ReporteComandaDTO {
    
    private EstadoComanda estado;
    private LocalDateTime fecha;
    private Long totalVenta;
    private Integer mesa;
    private String nombreCliente;

    public ReporteComandaDTO(EstadoComanda estado, LocalDateTime fecha, Long totalVenta, Integer mesa, String nombreCliente) {
        this.estado = estado;
        this.fecha = fecha;
        this.totalVenta = totalVenta;
        this.mesa = mesa;
        this.nombreCliente = nombreCliente;
    }
    
    public EstadoComanda getEstado() {
        return estado;
    }

    public void setEstado(EstadoComanda estado) {
        this.estado = estado;
    }
    
    public String getEstadoTexto() {
        return estado != null ?  estado.name() : "";
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
    public String getFechaFormato() {
        if (fecha == null) {
            return "";
        }
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public Long getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Long totalVenta) {
        this.totalVenta = totalVenta;
    }    

    public Integer getMesa() {
        return mesa;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }      
}