
package restaurantedominio;

/**
 *
 * @author Alex García Trejo
 */
public class ClienteFrecuentePVDTO {
    
    //Esta DTO sera usada cuando un cliente frecuente realize una compra (pedido)
    //Y se requiera actualizar sus puntos, total gastado y numero de visitas
    
//    private String nombre;
//    private String apellidoPaterno;
//    private String apellidoMaterno;
//    private String telefono;
//    private String email; //Este puede ser opcional
//    private LocalDate fechaRegistro;
    private Long id;
    private Double totalGastado;
    private Integer puntos;
    private Integer totalVisitas;

    public ClienteFrecuentePVDTO(Long id, Double totalGastado, Integer puntos, Integer totalVisitas) {
        this.id = id;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
        this.totalVisitas = totalVisitas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(Double totalGastado) {
        this.totalGastado = totalGastado;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Integer getTotalVisitas() {
        return totalVisitas;
    }

    public void setTotalVisitas(Integer totalVisitas) {
        this.totalVisitas = totalVisitas;
    }
}