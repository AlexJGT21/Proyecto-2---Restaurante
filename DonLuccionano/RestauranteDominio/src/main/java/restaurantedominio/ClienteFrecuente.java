
package restaurantedominio;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author JAR (JAIME, ALEJANDRO, ROBERTO)
 */
@Entity
@Table(name = "cliente_frecuente")
public class ClienteFrecuente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente_frecuente", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    
    @Column(name = "apellido_paterno", nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", nullable = false, length = 50)
    private String apellidoMaterno;
    
    @Column(name = "numero_telefonico", nullable = false, length = 255)   
    @Convert(converter = TelefonoConverter.class)
    private String numeroTelefonico;
    
    @Column(name = "correo_electronico", length = 50) //No se le puso "nullable" para hacerlo opcional
    private String correo;
    
    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    //NUEVOS PARAMETROS
    
    @Column(name = "total_gastado")
    private Double totalGastado;
    
    @Column(name = "puntos")
    private Integer puntos;
    
    @Column(name = "total_visitas")
    private Integer totalVisitas;        
    
    /**
     * Constructor por defecto para JPA.
     */
    public ClienteFrecuente() {    
    }

    /**
     * Constructor con todos los parametros
     * @param id OBLIGATORIA
     * @param nombre OBLIGATORIA
     * @param apellidoPaterno OBLIGATORIA
     * @param apellidoMaterno OBLIGATORIA
     * @param numeroTelefonico OBLIGATORIA
     * @param correo OBLIGATORIA
     * @param fechaRegistro OBLIGATORIA
     */
    public ClienteFrecuente(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String numeroTelefonico, String correo, LocalDate fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.numeroTelefonico = numeroTelefonico;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Constructor con todos los atributos excepto el ID
     * @param nombre OBLIGATORIA
     * @param apellidoPaterno OBLIGATORIA
     * @param apellidoMaterno OBLIGATORIA
     * @param numeroTelefonico OBLIGATORIA
     * @param correo OBLIGATORIA
     * @param fechaRegistro OBLIGATORIA
     */
    public ClienteFrecuente(String nombre, String apellidoPaterno, String apellidoMaterno, String numeroTelefonico, String correo, LocalDate fechaRegistro) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.numeroTelefonico = numeroTelefonico;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Constructor con todos los parametros excepto el ID y Correo (Usar en caso de no ingresar correo opcional o dejar en null)
     * @param nombre OBLIGATORIA
     * @param apellidoPaterno OBLIGATORIA
     * @param apellidoMaterno OBLIGATORIA
     * @param numeroTelefonico OBLIGATORIA
     * @param fechaRegistro OBLIGATORIA
     */
    public ClienteFrecuente(String nombre, String apellidoPaterno, String apellidoMaterno, String numeroTelefonico, LocalDate fechaRegistro) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.numeroTelefonico = numeroTelefonico;
        this.fechaRegistro = fechaRegistro;
    }

    
    /**
     * Constructor que crea un cliente frecuente con total gastado, puntos y total visitas
     * @param nombre OBLIGATORIO
     * @param apellidoPaterno OBLIGATORO
     * @param apellidoMaterno OBLIGATORIO
     * @param numeroTelefonico OBLIGATORIO
     * @param correo OBLIGATORIO
     * @param fechaRegistro OBLIGATORIO
     * @param totalGastado OLBIGATORIO
     * @param puntos OBLIGATORIO
     * @param totalVisitas OBLIGATORIO
     */
    public ClienteFrecuente(String nombre, String apellidoPaterno, String apellidoMaterno, String numeroTelefonico, String correo, LocalDate fechaRegistro, Double totalGastado, Integer puntos, Integer totalVisitas) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.numeroTelefonico = numeroTelefonico;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
        this.totalVisitas = totalVisitas;
    }

    /**
     * Constructor para actualizar total gastado, puntos y numero de visitas
     * @param totalGastado OBLIGATORIO
     * @param puntos OBLIGATORIO
     * @param totalVisitas OBLIGATORIO
     */
    public ClienteFrecuente(Double totalGastado, Integer puntos, Integer totalVisitas) {
        this.totalGastado = totalGastado;
        this.puntos = puntos;
        this.totalVisitas = totalVisitas;
    }        

    //GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    //NUEVOS GETTS AND SETTS

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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteFrecuente)) {
            return false;
        }
        ClienteFrecuente other = (ClienteFrecuente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }    

    @Override
    public String toString() {
        return "ClienteFrecuente{" + "id=" + id + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", numeroTelefonico=" + numeroTelefonico + ", correo=" + correo + ", fechaRegistro=" + fechaRegistro + ", puntos=" + puntos + ", totalVisitas=" + totalVisitas + '}';
    }            
}