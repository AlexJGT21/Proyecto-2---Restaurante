/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import java.time.LocalDate;
import java.util.List;
import restaurantedominio.Comanda;
import restaurantepersistencia.PersistenciaException;

/**
 *
 * @author Dell
 */
public interface IComandaDAO {
    public abstract Comanda registrarComanda(Comanda comanda) throws PersistenciaException;
    public abstract void actualizarComanda(Comanda comanda) throws PersistenciaException;
    public abstract Comanda buscarPorId(Long id) throws PersistenciaException;
    public abstract Comanda buscarPorFolio(String folio) throws PersistenciaException;
    public abstract Long consultarCantidadPorDia(LocalDate fecha) throws PersistenciaException;
    public abstract List<Comanda> consultarPorRangoFechas(LocalDate inicio, LocalDate fin) throws PersistenciaException;
    public abstract List<Comanda> consultarPorCliente(Long idCliente) throws PersistenciaException;
}
