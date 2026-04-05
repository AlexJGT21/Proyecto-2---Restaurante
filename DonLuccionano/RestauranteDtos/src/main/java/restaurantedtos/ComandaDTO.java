package restaurantedtos;

import EnumeradoresDTO.EstadoComandaDTO;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author JAR (JAIME, ALEJANDRO, ROBERTO)
 */
public class ComandaDTO implements Serializable {

    private Long id;
    private String folio;
    private LocalDate fecha;
    private EstadoComandaDTO estado;
    private String comentarios;
    private Long totalVenta;

    
    private MesaDTO mesa;
    private List<ProductoDTO> productos;
    private ClienteFrecuenteDTO cliente; // Puede ser null

    public ComandaDTO() {
    }

    /**
     * Constructor completo para mostrar datos (UI)
     */
    public ComandaDTO(Long id, String folio, LocalDate fecha, EstadoComandaDTO estado,
            String comentarios, Long totalVenta, MesaDTO mesa,
            List<ProductoDTO> productos, ClienteFrecuenteDTO cliente) {
        this.id = id;
        this.folio = folio;
        this.fecha = fecha;
        this.estado = estado;
        this.comentarios = comentarios;
        this.totalVenta = totalVenta;
        this.mesa = mesa;
        this.productos = productos;
        this.cliente = cliente;
    }

    // GETTERS Y SETTERS...
    
    public MesaDTO getMesa() {
        return mesa;
    }

    public void setMesa(MesaDTO mesa) {
        this.mesa = mesa;
    }

    public List<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public EstadoComandaDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoComandaDTO estado) {
        this.estado = estado;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Long getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Long totalVenta) {
        this.totalVenta = totalVenta;
    }

    public ClienteFrecuenteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteFrecuenteDTO cliente) {
        this.cliente = cliente;
    }

}
