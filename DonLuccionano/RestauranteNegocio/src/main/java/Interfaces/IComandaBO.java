package Interfaces;

import java.util.List;
import restaurantedtos.ComandaDTO;
import restaurantenegocio.NegocioException;

/**
 * @author JAR (JAIME, ALEJANDRO, ROBERTO)
 */
public interface IComandaBO {
    ComandaDTO abrirComanda(ComandaDTO comandaDTO) throws NegocioException;
    void cancelarComanda(Long idComanda) throws NegocioException;
    void entregarComanda(Long idComanda) throws NegocioException;
    ComandaDTO consultarPorFolio(String folio) throws NegocioException;
}