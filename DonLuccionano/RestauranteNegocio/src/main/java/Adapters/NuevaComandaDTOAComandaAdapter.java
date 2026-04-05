package Adapters;

import EnumeradoresDominio.EstadoComanda;
import java.time.LocalDate;
import java.util.List;
import restaurantedominio.ClienteFrecuente;
import restaurantedominio.Comanda;
import restaurantedominio.Mesa;
import restaurantedominio.Producto;
import restaurantedtos.ComandaDTO;

/**
 * @author JAR (JAIME, ALEJANDRO, ROBERTO)
 */
public class NuevaComandaDTOAComandaAdapter {

    /**
     * Adapta los datos del DTO y las Entidades validadas en una nueva Comanda.
     * @param dto El DTO proveniente de la vista.
     * @param mesa La entidad Mesa (ya consultada de la BD).
     * @param cliente La entidad Cliente (puede ser null).
     * @param productosReales La lista de productos JPA (ya validados).
     * @param folio El folio generado.
     * @return Entidad Comanda lista para persistir.
     */
    public static Comanda adaptar(ComandaDTO dto, Mesa mesa, ClienteFrecuente cliente, List<Producto> productosReales, String folio) {
        Comanda comanda = new Comanda();
        
        comanda.setFolio(folio);
        comanda.setFecha(LocalDate.now());
        comanda.setEstado(EstadoComanda.ABIERTA); // Por defecto al crear
        comanda.setComentarios(dto.getComentarios());
        comanda.setTotalVenta(dto.getTotalVenta());
        
        // Asignamos las entidades mapeadas
        comanda.setMesa(mesa);
        comanda.setCliente(cliente);
        comanda.setProductos(productosReales);
        
        return comanda;
    }
}