
package Controlador;

import Interfaces.IClienteFrecuenteBO;
import Interfaces.IComandaBO;
import Interfaces.IIngredienteBO;
import Interfaces.IMesaBO;
import Interfaces.IProductoBO;
import Interfaces.IProductoIngredientesBO;
import java.util.List;
import restaurantedominio.ClienteFrecuente;
import restaurantedominio.Comanda;
import restaurantedominio.Ingrediente;
import restaurantedominio.Mesa;
import restaurantedominio.Producto;
import restaurantedominio.ProductoIngredientes;
import restaurantedtos.ClienteFrecuenteDTO;
import restaurantedtos.ClienteFrecuenteReporteDTO;
import restaurantedtos.ComandaDTO;
import restaurantedtos.IngredienteDTO;
import restaurantedtos.MesaDTO;
import restaurantedtos.ProductoDTO;
import restaurantenegocio.ClienteFrecuenteBO;
import restaurantenegocio.ComandaBO;
import restaurantenegocio.IngredienteBO;
import restaurantenegocio.MesaBO;
import restaurantenegocio.NegocioException;
import restaurantenegocio.ProductoBO;
import restaurantenegocio.ProductoIngredientesBO;
import restaurantepresentacionFORMS.CobrarComandaFORM;
import restaurantepresentacionFORMS.AbrirComandaFORM;
import restaurantepresentacionFORMS.AdministradorFORM;
import restaurantepresentacionFORMS.AdministrarClientesFrecuentesFORM;
import restaurantepresentacionFORMS.ComandasActivasFORM;
import restaurantepresentacionFORMS.EditarComandaFORM;
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
    private IComandaBO comandaBO;

    
    public Control() {
        this.clienteFrecuenteBO = new ClienteFrecuenteBO();
        this.ingredienteBO = new IngredienteBO();
        this.piBO = new ProductoIngredientesBO();
        this.productoBO = new ProductoBO();
        this.comandaBO = new ComandaBO();
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
    
    public List<ClienteFrecuenteDTO> obtenerListaClientesFrecuentes() throws NegocioException {
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
    
    public List<Ingrediente> llenarTablaIngredientes() throws NegocioException {
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
    
    public List<Producto> llenarTabla() throws NegocioException {
        return productoBO.llenarTabla();
    }

    public void mostrarAbrirComandaFORM() {
        new AbrirComandaFORM(this).setVisible(true);
    }
    
    public ComandaDTO abrirComanda(ComandaDTO comandaDTO) throws NegocioException {
        return comandaBO.abrirComanda(comandaDTO);
    }
    
    public void mostrarComandasActivasFORM() {
        new ComandasActivasFORM(this).setVisible(true);
    }
    
    public void mostrarEditarComandaFORM() {
        new EditarComandaFORM(this).setVisible(true);
    }
    
    public void mostrarCobrarComandaFORM() {
        new CobrarComandaFORM(this).setVisible(true);
    }
    
    public Mesa registrarMesa(MesaDTO nuevaMesa) throws NegocioException {
        return mesaBO.registrarMesa(nuevaMesa);
    }
    
    public List<Comanda> obtenerComandasActivas() throws NegocioException {
        return comandaBO.obtenerComandasActivas();
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
    
     public List<Mesa> listarMesas() throws NegocioException {
         return mesaBO.listarMesas();
     }
    
    
    
}