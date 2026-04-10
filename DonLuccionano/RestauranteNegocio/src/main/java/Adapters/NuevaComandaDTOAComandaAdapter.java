package Adapters;

import EnumeradoresDominio.EstadoComanda;
import java.time.LocalDateTime;
import java.util.List;
import restaurantedominio.ClienteFrecuente;
import restaurantedominio.Comanda;
import restaurantedominio.DetalleComanda;
import restaurantedominio.Mesa;
import restaurantedtos.ComandaDTO;

public class NuevaComandaDTOAComandaAdapter {

    public static Comanda adaptar(ComandaDTO dto, Mesa mesa, ClienteFrecuente cliente, List<DetalleComanda> detallesReales, String folio) {
        Comanda comanda = new Comanda();
        
        comanda.setFolio(folio);
        comanda.setFecha(LocalDateTime.now());
        comanda.setEstado(EstadoComanda.ABIERTA); 
        comanda.setComentarios(dto.getComentarios());
        comanda.setTotalVenta(dto.getTotalVenta());
        
        comanda.setMesa(mesa);
        comanda.setCliente(cliente);
        

        for(DetalleComanda det : detallesReales) {
            det.setComanda(comanda);
        }
        comanda.setDetalles(detallesReales);
        
        return comanda;
    }
}