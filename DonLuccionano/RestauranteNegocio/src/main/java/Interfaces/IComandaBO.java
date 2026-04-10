package Interfaces;

import java.time.LocalDate;
import java.util.List;
import restaurantedominio.Comanda;
import restaurantedtos.ComandaDTO;
import restaurantedtos.ReporteComandaDTO;
import restaurantenegocio.NegocioException;

/**
 * @author JAR (JAIME, ALEJANDRO, ROBERTO)
 */
public interface IComandaBO {
    ComandaDTO abrirComanda(ComandaDTO comandaDTO) throws NegocioException;
    void cancelarComanda(Long idComanda) throws NegocioException;
    void entregarComanda(Long idComanda) throws NegocioException;
    ComandaDTO consultarPorFolio(String folio) throws NegocioException;
    public List<Comanda> obtenerComandasActivas() throws NegocioException;
    public Comanda consultarComanda(Long idComanda) throws NegocioException;
    public void actualizarComanda(ComandaDTO comandaDTO) throws NegocioException;
    
    //Metodo de generacion de reporte
    public List<ReporteComandaDTO> generarReporteComanda(LocalDate fechaInicio, LocalDate fechaFin) throws NegocioException;
}