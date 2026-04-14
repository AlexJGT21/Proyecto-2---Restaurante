package pruebas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// Importaciones de JUnit 5
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Importaciones de tu proyecto
import restaurantedtos.ClienteFrecuenteDTO;
import restaurantedtos.ComandaDTO;
import restaurantedtos.DetalleComandaDTO;
import restaurantedtos.IngredienteDTO;
import restaurantedtos.MesaDTO;
import restaurantedtos.ProductoDTO;
import EnumeradoresDTO.TipoUnidad;
import static EnumeradoresDTO.Disponibilidad.DISPONIBLE;
import EnumeradoresDTO.EstadoComandaDTO;
import java.time.LocalDate;
import org.junit.jupiter.api.Disabled;

import restaurantedominio.ClienteFrecuente;
import restaurantedominio.Comanda;
import restaurantedominio.Ingrediente;
import restaurantedominio.Mesa;
import restaurantedominio.Producto;
import restaurantedominio.ProductoIngredientes;

import restaurantenegocio.ClienteFrecuenteBO;
import restaurantenegocio.ComandaBO;
import restaurantenegocio.IngredienteBO;
import restaurantenegocio.MesaBO;
import restaurantenegocio.ProductoBO;
import restaurantenegocio.ProductoIngredientesBO;
import restaurantenegocio.NegocioException;

/**
 * Pruebas de Integración para el módulo de Comandas (Modo Leyenda)
 */
@Disabled("Desactivado temporalmente para no golpear la base de datos en el Clean and Build")
public class ComandaTest {

    // BOs a utilizar
    private MesaBO mesaBO;
    private ClienteFrecuenteBO clienteBO;
    private IngredienteBO ingredienteBO;
    private ProductoBO productoBO;
    private ProductoIngredientesBO recetaBO;
    private ComandaBO comandaBO;

    // IDs generados durante la preparación para usarlos en el test
    private Long idMesaSeleccionada;
    private Long idClienteSeleccionado;
    private Long idProductoHotDog;

//    /**
//     * @BeforeEach se ejecuta ANTES de cada @Test.
//     * Aquí preparamos la base de datos (Mesas, Clientes, Ingredientes, Platillo y Receta).
//     */
//    @BeforeEach
//    public void prepararBaseDeDatos() {
//        System.out.println("--- Preparando Base de Datos ---");
//        mesaBO = new MesaBO();
//        clienteBO = new ClienteFrecuenteBO();
//        ingredienteBO = new IngredienteBO();
//        productoBO = new ProductoBO();
//        recetaBO = new ProductoIngredientesBO();
//        comandaBO = new ComandaBO();
//
//        try {
//           // 1. Crear Mesas
//            MesaDTO m1 = new MesaDTO(); 
//            m1.setNumMesa(10); 
//            m1.setDisponible(DISPONIBLE);
//
//            MesaDTO m2 = new MesaDTO(); 
//            m2.setNumMesa(11); 
//            m2.setDisponible(DISPONIBLE);
//
//            MesaDTO m3 = new MesaDTO(); 
//            m3.setNumMesa(12); 
//            m3.setDisponible(DISPONIBLE);
//
//            MesaDTO m4 = new MesaDTO(); 
//            m4.setNumMesa(13); 
//            m4.setDisponible(DISPONIBLE);
//            
//            Mesa mesaRegistrada = mesaBO.registrarMesa(m1);
//            idMesaSeleccionada = mesaRegistrada.getId();
//            mesaBO.registrarMesa(m2);
//            mesaBO.registrarMesa(m3);
//            mesaBO.registrarMesa(m4);
//
//            // 2. Crear Clientes
//            // 2. Crear Clientes
//            ClienteFrecuenteDTO c1 = new ClienteFrecuenteDTO();
//            c1.setNombre("Alex Test"); 
//            c1.setApellidoPaterno("Garcia"); 
//            c1.setApellidoMaterno("Perez"); 
//            c1.setTelefono("6440000001");
//            c1.setEmail("alex.test@correo.com");
//            c1.setFechaRegistro(LocalDate.now()); // <--- FECHA AGREGADA (Cámbialo a LocalDate.now() si te marca rojo)
//            
//            ClienteFrecuente clienteRegistrado = clienteBO.crearCliente(c1);
//            idClienteSeleccionado = clienteRegistrado.getId();
//
//            ClienteFrecuenteDTO c2 = new ClienteFrecuenteDTO();
//            c2.setNombre("Jaime Test"); 
//            c2.setApellidoPaterno("Lopez"); 
//            c2.setApellidoMaterno("Gomez"); 
//            c2.setTelefono("6440000002");
//            c2.setEmail("jaime.test@correo.com");
//            c2.setFechaRegistro(LocalDate.now()); // <--- FECHA AGREGADA
//            clienteBO.crearCliente(c2);
//
//            // 3. Crear Ingredientes
//            IngredienteDTO ingPan = new IngredienteDTO();
//            ingPan.setNombre("Pan de HotDog Test");
//            ingPan.setCantidad(new BigDecimal("100.00"));
//            ingPan.setUnidad(TipoUnidad.PIEZAS);
//
//            IngredienteDTO ingSalchicha = new IngredienteDTO();
//            ingSalchicha.setNombre("Salchicha Test");
//            ingSalchicha.setCantidad(new BigDecimal("100.00"));
//            ingSalchicha.setUnidad(TipoUnidad.PIEZAS);
//
//            Ingrediente panGuardado = ingredienteBO.nuevoIngrediente(ingPan);
//            Ingrediente salchichaGuardada = ingredienteBO.nuevoIngrediente(ingSalchicha);
//
//            // 4. Crear Platillo
//            ProductoDTO prodDTO = new ProductoDTO();
//            prodDTO.setNombre("Hot Dog Test");
//            prodDTO.setPrecio(45.5f);
//            prodDTO.setTipo("Platillo");
//            prodDTO.setActivo(true);
//
//            Producto productoGuardado = productoBO.crearProducto(prodDTO);
//            idProductoHotDog = productoGuardado.getId();
//
//            // 5. Armar la Receta (Ligar ingredientes al producto)
//            // Receta para 1 Hot Dog: 1 Pan y 1 Salchicha
//            ProductoIngredientes recetaPan = new ProductoIngredientes();
//            recetaPan.setProductos(productoGuardado);
//            recetaPan.setIngredientes(panGuardado);
//            recetaPan.setCantidad(new BigDecimal("1.0"));
//            recetaBO.agregarProductoIngrediente(recetaPan);
//
//            ProductoIngredientes recetaSalchicha = new ProductoIngredientes();
//            recetaSalchicha.setProductos(productoGuardado);
//            recetaSalchicha.setIngredientes(salchichaGuardada);
//            recetaSalchicha.setCantidad(new BigDecimal("1.0"));
//            recetaBO.agregarProductoIngrediente(recetaSalchicha);
//
//            System.out.println("--- BD Preparada Exitosamente ---");
//
//        } catch (NegocioException ex) {
//            fail("Falló la preparación de la BD: " + ex.getMessage());
//        }
//    }
//
//    /**
//     * Prueba el flujo completo de abrir una comanda con múltiples cantidades del mismo producto.
//     */
//    @Test
//    public void testAbrirComandaConMultiplesCantidades() {
//        System.out.println("--- Ejecutando Test: Abrir Comanda ---");
//        
//        try {
//            // 1. Instanciamos los DTOs necesarios con los IDs de la preparación
//            MesaDTO mesa = new MesaDTO();
//            mesa.setId(idMesaSeleccionada);
//
//            ClienteFrecuenteDTO cliente = new ClienteFrecuenteDTO();
//            cliente.setId(idClienteSeleccionado);
//
//            ProductoDTO producto = new ProductoDTO();
//            producto.setId(idProductoHotDog);
//
//            // 2. Creamos la Comanda DTO
//            ComandaDTO comandaDTO = new ComandaDTO();
//            comandaDTO.setMesa(mesa);
//            comandaDTO.setCliente(cliente);
//            comandaDTO.setComentarios("Sin ketchup por favor");
//
//            // 3. Simulamos que el cliente pide 5 Hot Dogs (Probando el Modo Leyenda)
//            int cantidadPedida = 5;
//            double precioUnitario = 45.0;
//            double subtotalCalculado = cantidadPedida * precioUnitario; // 225.0
//
//            DetalleComandaDTO detalleHotDog = new DetalleComandaDTO(cantidadPedida, subtotalCalculado, producto);
//
//            List<DetalleComandaDTO> orden = new ArrayList<>();
//            orden.add(detalleHotDog);
//
//            comandaDTO.setDetalles(orden);
//            comandaDTO.setTotalVenta((long) subtotalCalculado); // 225
//
//            // 4. EJECUTAMOS LA LÓGICA DE NEGOCIO
//            ComandaDTO comandaExitosa = comandaBO.abrirComanda(comandaDTO);
//
//            // 5. VALIDAMOS LOS RESULTADOS (Assertions)
//            assertNotNull(comandaExitosa.getId(), "La comanda debe tener un ID generado");
//            assertNotNull(comandaExitosa.getFolio(), "La comanda debe tener un Folio generado");
//            assertEquals(EstadoComandaDTO.ABIERTA, comandaExitosa.getEstado(), "El estado debe ser ABIERTA");
//            System.out.println("Folio Generado: " + comandaExitosa.getFolio());
//
//            // 6. VALIDAMOS QUE SE DESCONTÓ EL INVENTARIO CORRECTAMENTE
//            // Si el test funcionó, el inventario debió multiplicarse: 100 - (1 pan * 5 hotdogs) = 95 panes
//            // NOTA: Descomenta esto si quieres asegurar el descuento (necesitarás un método buscarPorId en IngredienteBO)
//            /*
//            Ingrediente panActualizado = ingredienteBO.buscarPorNombre("Pan de HotDog Test"); // O buscarPorId
//            assertEquals(new BigDecimal("95.00"), panActualizado.getCantidad(), "Debe haber descontado 5 panes");
//            */
//
//        } catch (NegocioException ex) {
//            fail("El test falló inesperadamente: " + ex.getMessage());
//        }
//    }
}