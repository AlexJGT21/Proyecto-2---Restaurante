
package restaurantedtos;

import EnumeradoresDTO.EstadoComandaDTO;
import java.time.LocalDateTime;
import EnumeradoresDominio.EstadoComanda;

/**
 *
 * @author Alex García Trejo
 */
public class ReporteComandaDTO {
    
    private EstadoComandaDTO estado;
    private LocalDateTime fecha;
    private Long totalVenta;
    private int mesa;
    private String nombreCliente;

   
    public ReporteComandaDTO(String nombreCliente, EstadoComanda estado, LocalDateTime fecha, Long totalVenta, Integer mesa) {
        this.nombreCliente = nombreCliente;
        this.estado = EstadoComandaDTO.valueOf(estado.name()); 
        this.fecha = fecha;
        this.totalVenta = totalVenta;
        this.mesa = mesa;
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

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    
}