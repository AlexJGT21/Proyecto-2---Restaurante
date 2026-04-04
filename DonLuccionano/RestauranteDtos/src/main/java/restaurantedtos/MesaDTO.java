
package restaurantedtos;

import EnumeradoresDTO.Disponibilidad;

/**
 *
 * @author Alex García Trejo
 */
public class MesaDTO {
    
    private Integer numMesa;
    private Disponibilidad disponible;

    public MesaDTO(Integer numMesa, Disponibilidad disponible) {
        this.numMesa = numMesa;
        this.disponible = disponible;
    }

    public Integer getNumMesa() {
        return numMesa;
    }

    public Disponibilidad getDisponible() {
        return disponible;
    }

    public void setNumMesa(Integer numMesa) {
        this.numMesa = numMesa;
    }

    public void setDisponible(Disponibilidad disponible) {
        this.disponible = disponible;
    }        
}