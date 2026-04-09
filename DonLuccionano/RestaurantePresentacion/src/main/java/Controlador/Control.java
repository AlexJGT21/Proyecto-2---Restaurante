
package Controlador;

import Interfaces.IClienteFrecuenteBO;
import Interfaces.IIngredienteBO;
import Interfaces.IMesaBO;
import Interfaces.IProductoBO;
import Interfaces.IProductoIngredientesBO;
import java.util.List;
import restaurantedominio.ClienteFrecuente;
import restaurantedominio.Ingrediente;
import restaurantedominio.Mesa;
import restaurantedominio.ProductoIngredientes;
import restaurantedtos.ClienteFrecuenteDTO;
import restaurantedtos.ClienteFrecuenteReporteDTO;
import restaurantedtos.IngredienteDTO;
import restaurantedtos.MesaDTO;
import restaurantedtos.ProductoDTO;
import restaurantenegocio.ClienteFrecuenteBO;
import restaurantenegocio.IngredienteBO;
import restaurantenegocio.MesaBO;
import restaurantenegocio.NegocioException;
import restaurantenegocio.ProductoBO;
import restaurantenegocio.ProductoIngredientesBO;
import restaurantepresentacionFORMS.AdministradorFORM;
import restaurantepresentacionFORMS.AdministrarClientesFrecuentesFORM;
import restaurantepresentacionFORMS.GestionClientesFORM;
import restaurantepresentacionFORMS.GestionIngredientesFORM;
import restaurantepresentacionFORMS.GestionProductosFORM;
import restaurantepresentacionFORMS.GestionRestauranteFORM;
import restaurantepresentacionFORMS.SistemaReportesFORM;

/**
 *
 * @author Alex García Trejo
 */
public class Control {
    
    private IClienteFrecuenteBO clienteFrecuenteBO;
    private IIngredienteBO ingredienteBO;
    private IProductoIngredientesBO piBO;
    private IProductoBO productoBO;
    private IMesaBO mesaBO;
    
    public Control() {
        this.clienteFrecuenteBO = new ClienteFrecuenteBO();
        this.ingredienteBO = new IngredienteBO();
        this.piBO = new ProductoIngredientesBO();
        this.productoBO = new ProductoBO();
        this.mesaBO = new MesaBO();
    }

    public void mostrarGestionRestauranteFORM() {
        new GestionRestauranteFORM(this).setVisible(true);
    }
    
    public void mostrarGestionClientesFORM() {
        new GestionClientesFORM(this).setVisible(true);
    }
    
    public void regresarGestionRestauranteFORM() {
        new GestionClientesFORM(this).setVisible(true);
    }
    
    public void mostrarAdministrarClientesFrecuentesFORM() {
        new AdministrarClientesFrecuentesFORM(this).setVisible(true);
    }
    
    public List<ClienteFrecuenteDTO> obtenerListaClientes() throws NegocioException {
        return clienteFrecuenteBO.listaClientesF();
    }
    
    public ClienteFrecuente registrarCliente(ClienteFrecuenteDTO clienteFrecuente) throws NegocioException {
        ClienteFrecuente cliente = clienteFrecuenteBO.crearCliente(clienteFrecuente);
        return cliente;
    }
    
    public ClienteFrecuente buscarClientePorTelefono(String telefono) throws NegocioException {
        return clienteFrecuenteBO.buscarPorTelefono(telefono);
    }
    
    public ClienteFrecuente buscarClientePorCorreo(String correo) throws NegocioException {
        return clienteFrecuenteBO.buscarPorCorreo(correo);
    }
    
    public List<ClienteFrecuente> buscarClientePorNombreApellidos(String nombre, String apellidoP, String apellidoM) throws NegocioException {
        return clienteFrecuenteBO.buscarClienteLista(nombre, apellidoP, apellidoM);
    }
    
    public void mostrarAdministradorFORM() {
        new AdministradorFORM(this).setVisible(true);
    }
    
    public void mostrarGestionIngredientesFORM() {
        new GestionIngredientesFORM(this).setVisible(true);
    }
    
    public List<Ingrediente> llenarTabla() throws NegocioException {
        return ingredienteBO.llenarTabla();
    }
    
    public Ingrediente nuevoIngrediente(IngredienteDTO nuevoIngrediente) throws NegocioException {
        Ingrediente ingrediente = ingredienteBO.nuevoIngrediente(nuevoIngrediente);
        return ingrediente;
    }    
    
    public List<IngredienteDTO> consultarTodosLosIngredientes() throws Exception {
        return ingredienteBO.consultarTodosLosIngredientes();
    }
    
    public List<ProductoIngredientes> obtenerIngredientesPorProducto(Long idProducto) throws NegocioException {
        return piBO.obtenerIngredientesPorProducto(idProducto);
    }
    
    public ProductoIngredientes agregarProductoIngrediente(ProductoIngredientes pi) throws NegocioException {
        return piBO.agregarProductoIngrediente(pi);
    }
    
    public List<ProductoDTO> obtenerProductos() throws NegocioException {
        return productoBO.obtenerProductos();
    }
    
    public Mesa registrarMesa(MesaDTO nuevaMesa) throws NegocioException {
        return mesaBO.registrarMesa(nuevaMesa);
    }
    
    public void mostrarGestionProductosFORM() {
        new GestionProductosFORM(this).setVisible(true);
    }
    
    public void mostrarSistemaReportesFORM() {
        new SistemaReportesFORM(this).setVisible(true);
    }
    
    public List<ClienteFrecuenteReporteDTO> filtrarClientes(String nombre, Integer visitas) throws NegocioException {
        return clienteFrecuenteBO.filtrarClientes(nombre, visitas);
    }
    
    public boolean generarReporteClientesFrecuentes(String nombre, Integer visitas) throws NegocioException {
        return clienteFrecuenteBO.generarReporteClientesFrecuentes(nombre, visitas);
    }
    
    
    
}