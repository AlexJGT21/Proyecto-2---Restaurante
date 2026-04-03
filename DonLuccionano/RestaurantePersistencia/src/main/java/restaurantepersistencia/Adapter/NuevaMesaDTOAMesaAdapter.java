
package restaurantepersistencia.Adapter;

import EnumeradoresDominio.Disponibilidad;
import restaurantedominio.Mesa;
import restaurantedtos.MesaDTO;

/**
 *
 * @author Alex García Trejo
 */
public class NuevaMesaDTOAMesaAdapter {
    
    public static Mesa adaptar(MesaDTO mesaDTO) {
        Disponibilidad disponible;
        switch(mesaDTO.getDisponible()) {
            case DISPONIBLE -> disponible = Disponibilidad.DISPONIBLE;
            case NO_DISPONIBLE -> disponible = Disponibilidad.NO_DISPONIBLE;
            default -> throw new IllegalArgumentException("Disponibilidad no valida");                        
        }
        return new Mesa(mesaDTO.getNumMesa(), disponible);
    }
}