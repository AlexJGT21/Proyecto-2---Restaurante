/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurantenegocio;

import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;

/**
 *
 * @author JAR
 */
public interface IClienteFrecuenteBO  {
    
    ClienteFrecuente crearCliente(ClienteFrecuenteDTO clienteDTO) throws NegocioException;
}
