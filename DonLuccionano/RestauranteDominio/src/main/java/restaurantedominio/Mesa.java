
package restaurantedominio;

import EnumeradoresDominio.Disponibilidad;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author JAR
 */
@Entity
@Table(name = "mesa")
public class Mesa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesa", nullable = false)
    private Long id;

    @Column(name = "numero_mesa", nullable = false, unique = true)
    private Integer numMesa;
    
    @Column(name = "disponibilidad", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Disponibilidad disponible;

    /**
     * Constructor por defecto para JPA.
     */
    public Mesa() {
    }

    /**
     * Constructor que permite crear una mesa con todos sus parametros
     * @param id OBLIGATORIO
     * @param numMesa OBLIGATORIO
     * @param disponible OBLIGATORIO
     */
    public Mesa(Long id, Integer numMesa, Disponibilidad disponible) {
        this.id = id;
        this.numMesa = numMesa;
        this.disponible = disponible;
    }

    /**
     * Constructor que permite crear una mesa con su numero y disponibilidad
     * @param numMesa OBLIGATORIO
     * @param disponible OBLIGATORIO
     */
    public Mesa(Integer numMesa, Disponibilidad disponible) {
        this.numMesa = numMesa;
        this.disponible = disponible;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumMesa() {
        return numMesa;
    }

    public void setNumMesa(Integer numMesa) {
        this.numMesa = numMesa;
    }

    public Disponibilidad getDisponible() {
        return disponible;
    }

    public void setDisponible(Disponibilidad disponible) {
        this.disponible = disponible;
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
        if (!(object instanceof Mesa)) {
            return false;
        }
        Mesa other = (Mesa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Mesa{" + "id=" + id + ", numMesa=" + numMesa + ", disponible=" + disponible + '}';
    }
}