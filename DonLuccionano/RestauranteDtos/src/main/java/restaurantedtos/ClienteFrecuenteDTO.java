
package restaurantedtos;

import java.time.LocalDate;

/**
 *
 * @author JAR
 */
public class ClienteFrecuenteDTO {
    
    //Roberto agrego la id... y ya xd
    
    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String email; //Este puede ser opcional
    private LocalDate fechaRegistro;

    public ClienteFrecuenteDTO(String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String email, LocalDate fechaRegistro) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.email = email;
        this.fechaRegistro = fechaRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    

    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }
}