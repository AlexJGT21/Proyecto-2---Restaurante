//package Pruebas;
//
//import EnumeradoresDTO.EstadoComandaDTO;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//import restaurantedominio.ClienteFrecuente;
//import restaurantedominio.Ingrediente;
//import restaurantedominio.Mesa;
//import restaurantedominio.Producto;
//import restaurantedtos.ClienteFrecuenteDTO;
//import restaurantedtos.ComandaDTO;
//import restaurantedtos.IngredienteDTO;
//import restaurantedtos.MesaDTO;
//import restaurantedtos.ProductoDTO;
//import restaurantedtos.ProductoIngredientesDTO;
//
//import restaurantenegocio.ClienteFrecuenteBO;
//import restaurantenegocio.ComandaBO;
//import restaurantenegocio.IngredienteBO;
//import restaurantenegocio.MesaBO;
//import restaurantenegocio.NegocioException;
//import restaurantenegocio.ProductoBO;
//import EnumeradoresDTO.Disponibilidad;
//import EnumeradoresDTO.TipoUnidad;
//import org.junit.jupiter.api.Disabled;
//
///**
// * Prueba de Integración Exclusiva de Capa de Negocio (BO)
// */
//@Disabled("Desactivado temporalmente para no golpear la base de datos en el Clean and Build")
//public class ComandaBOPuroTest {
//
//    // Declaramos todos los BOs
//    private ComandaBO comandaBO;
//    private MesaBO mesaBO;
//    private IngredienteBO ingredienteBO;
//    private ProductoBO productoBO;
//    private ClienteFrecuenteBO clienteBO;
//
//    @BeforeEach
//    public void setUp() {
//        // Inicializamos los BOs antes de la prueba
//        comandaBO = new ComandaBO();
//        mesaBO = new MesaBO();
//        ingredienteBO = new IngredienteBO();
//        productoBO = new ProductoBO();
//        clienteBO = new ClienteFrecuenteBO();
//    }
//
//    @Test
//    public void testFlujoCompletoMedianteBOs() {
//        try {
//            System.out.println("--- INICIANDO PRUEBA CON CAPA DE NEGOCIO (BO) ---");
//
//            // =========================================================
//            // FASE 1: PREPARACIÓN DESDE LA CAPA DE NEGOCIO (Simulando la Interfaz)
//            // =========================================================
//            
//            // 1.1 Registrar Mesa usando MesaBO
//            int numeroMesaAleatorio = (int) (Math.random() * 9000) + 1000;
//            MesaDTO mesaMoc = new MesaDTO(numeroMesaAleatorio, Disponibilidad.DISPONIBLE);
//            Mesa mesaGuardada = mesaBO.registrarMesa(mesaMoc);
//
//            // 1.2 Registrar Ingrediente usando IngredienteBO
//            String nombreIngrediente = "Pan BO " + System.currentTimeMillis(); 
//            IngredienteDTO ingDTO = new IngredienteDTO(nombreIngrediente, TipoUnidad.PIEZAS, new BigDecimal("100.00"));
//            Ingrediente ingGuardado = ingredienteBO.nuevoIngrediente(ingDTO);
//
//            // 1.3 Registrar Producto usando ProductoBO
//            ProductoIngredientesDTO recetaItem = new ProductoIngredientesDTO(ingGuardado.getId(), nombreIngrediente, new BigDecimal("2.00"));
//            List<ProductoIngredientesDTO> receta = new ArrayList<>();
//            receta.add(recetaItem);
//            
//            ProductoDTO prodDTO = new ProductoDTO("HotDog BO " + System.currentTimeMillis(), 
//                    "Hecho desde BO", "Platillo", 60.0f, true, null, receta);
//            Producto prodGuardado = productoBO.crearProducto(prodDTO);
//
//            // 1.4 Registrar Cliente usando ClienteFrecuenteBO
//            // Generamos un teléfono aleatorio para evitar problemas de duplicados (Unique)
//            String telAleatorio = "644" + ((int) (Math.random() * 9000000) + 1000000); 
//            ClienteFrecuenteDTO clienteDTO = new ClienteFrecuenteDTO("Ana", "Lopez", "Ruiz", telAleatorio, "ana@itson.mx", LocalDate.now());
//            ClienteFrecuente clienteGuardado = clienteBO.crearCliente(clienteDTO);
//
//            // =========================================================
//            // FASE 2: ACCIÓN (Abrir Comanda con ComandaBO)
//            // =========================================================
//            
//            ComandaDTO comandaAAbrir = new ComandaDTO();
//            comandaAAbrir.setComentarios("Mucha catsup");
//            comandaAAbrir.setTotalVenta(60L);
//
//            // Mapeamos lo generado a la comanda
//            MesaDTO mesaParaComanda = new MesaDTO(mesaGuardada.getId(), mesaGuardada.getNumMesa(), Disponibilidad.DISPONIBLE);
//            comandaAAbrir.setMesa(mesaParaComanda);
//
//            ClienteFrecuenteDTO clienteParaComanda = new ClienteFrecuenteDTO();
//            clienteParaComanda.setId(clienteGuardado.getId());
//            comandaAAbrir.setCliente(clienteParaComanda);
//
//            ProductoDTO productoParaComanda = new ProductoDTO();
//            productoParaComanda.setId(prodGuardado.getId());
//            List<ProductoDTO> listaProductosComanda = new ArrayList<>();
//            listaProductosComanda.add(productoParaComanda);
//            comandaAAbrir.setProductos(listaProductosComanda);
//
//            // Ejecutamos abrirComanda
//            System.out.println("BO: Abriendo Comanda...");
//            ComandaDTO comandaAbierta = comandaBO.abrirComanda(comandaAAbrir);
//
//            // Validaciones (Asserts)
//            assertNotNull(comandaAbierta.getId(), "El BO debió devolver una comanda con ID.");
//            assertEquals(EstadoComandaDTO.ABIERTA, comandaAbierta.getEstado(), "El BO debió abrirla correctamente.");
//
//            // =========================================================
//            // FASE 3: ACCIÓN (Entregar Comanda y Sumar Puntos)
//            // =========================================================
//            
//            System.out.println("BO: Entregando Comanda y actualizando cliente...");
//            
//            // 3.1 Entregamos la comanda
//            comandaBO.entregarComanda(comandaAbierta.getId());
//            
//            // 3.2 Simulamos lo que haría la interfaz gráfica (mandar llamar al ClienteBO para sumarle sus puntos)
//            // Convertimos el Long a Double como lo pide el método actualizarVisita
//            Double totalGastoDouble = comandaAAbrir.getTotalVenta().doubleValue(); 
//            ClienteFrecuente clienteActualizado = clienteBO.actualizarVisita(clienteGuardado.getId(), totalGastoDouble);
//
//            // Validaciones finales
//            assertNotNull(clienteActualizado.getPuntos(), "El cliente debe tener puntos calculados.");
//            assertTrue(clienteActualizado.getTotalVisitas() > 0, "Las visitas debieron aumentar.");
//            
//            System.out.println("--- PRUEBA BO COMPLETADA CON ÉXITO ---");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("La prueba falló en la capa de negocio: " + e.getMessage());
//        }
//    }
//}