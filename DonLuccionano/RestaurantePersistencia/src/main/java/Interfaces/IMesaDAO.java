
package Interfaces;

import EnumeradoresDominio.Disponibilidad;
import java.util.List;
import restaurantedominio.Mesa;
import restaurantedtos.MesaDTO;
import restaurantepersistencia.PersistenciaException;

/**
 *
 * @author Alex García Trejo
 */
public interface IMesaDAO {
    
    //Contrato 1: Permite crear una nueva mesa
    //Se espera que este sea el unico metodo de esta clase, no se contempla la eliminación, actualizacion o lectura de mesas (al menos no ahora)
    public abstract Mesa registrarMesa(MesaDTO nuevaMesa) throws PersistenciaException;
    
    //Contrato 2: Este sera el metodo que permita consultar la cantidad total de mesas. Este sera usado por "registroMesa"
    //Retornando la cantidad para determinar si se pueden añadir o no más mesas
    public abstract int consultarCantidad() throws PersistenciaException;
    
    //Contrato 3: Este metodo sera usado por "registroMesa" para validar si existe o no una mesa
    public abstract boolean existeMesa(Integer numMesa) throws PersistenciaException;
    
    //Contrato 4: Lista todas las mesas (disponibles y no disponibles)
    public abstract List<Mesa> listarMesas() throws PersistenciaException;
    
    //Contrato 5: Permite cambiar el estado de una mesa de disponible a no disponible y visceversa
    public abstract Mesa cambiarDisponibilidad(Long id, Disponibilidad disponibilidad) throws PersistenciaException;
}
