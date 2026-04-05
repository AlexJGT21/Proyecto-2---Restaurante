package restaurantedominio;

import EnumeradoresDominio.EstadoComanda;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author JAR (JAIME, ALEJANDRO, ROBERTO)
 */
@Entity
@Table(name = "comandas")
public class Comanda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comanda", nullable = false)
    private Long id;

    @Column(name = "folio", nullable = false, unique = true, length = 15)
    private String folio;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "id_mesa", nullable = false)
    private Mesa mesa;

    @ManyToMany
    @JoinTable(
        name = "comanda_productos",
        joinColumns = @JoinColumn(name = "id_comanda"),
        inverseJoinColumns = @JoinColumn(name = "id_producto")
    )
    private List<Producto> productos;

    @Column(name = "estado", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private EstadoComanda estado;

    @Column(name = "comentarios", length = 200)
    private String comentarios;

    @Column(name = "total_venta", nullable = false)
    private Long totalVenta;

    @ManyToOne
    @JoinColumn(name = "id_cliente_frecuente", nullable = true)
    private ClienteFrecuente cliente;

    /**
     * Constructor por defecto para JPA.
     */
    public Comanda() {
    }

    /**
     * Constructor con todos los parámetros.
     */
    public Comanda(Long id, String folio, LocalDate fecha, Mesa mesa, List<Producto> productos, EstadoComanda estado, String comentarios, long totalVenta, ClienteFrecuente cliente) {
        this.id = id;
        this.folio = folio;
        this.fecha = fecha;
        this.mesa = mesa;
        this.productos = productos;
        this.estado = estado;
        this.comentarios = comentarios;
        this.totalVenta = totalVenta;
        this.cliente = cliente;
    }

    /**
     * Constructor sin ID para creación de nuevas comandas.
     */
    public Comanda(String folio, LocalDate fecha, Mesa mesa, List<Producto> productos, EstadoComanda estado, String comentarios, long totalVenta) {
        this.folio = folio;
        this.fecha = fecha;
        this.mesa = mesa;
        this.productos = productos;
        this.estado = estado;
        this.comentarios = comentarios;
        this.totalVenta = totalVenta;
    }

    // GETTERS AND SETTERS

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

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public EstadoComanda getEstado() {
        return estado;
    }

    public void setEstado(EstadoComanda estado) {
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

    public ClienteFrecuente getCliente() {
        return cliente;
    }

    public void setCliente(ClienteFrecuente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Comanda)) {
            return false;
        }
        Comanda other = (Comanda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Comanda{" + "id=" + id + ", folio=" + folio + ", fecha=" + fecha + ", estado=" + estado + ", totalVenta=" + totalVenta + '}';
    }
}