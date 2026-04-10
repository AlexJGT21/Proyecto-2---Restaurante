//package Pruebas;
//
//import EnumeradoresDominio.Disponibilidad;
//import EnumeradoresDominio.TipoUnidad;
//import EnumeradoresDTO.EstadoComandaDTO;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Disabled;
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
//import restaurantenegocio.ComandaBO;
//import restaurantenegocio.NegocioException;
//import restaurantepersistencia.ClienteFrecuenteDAO;
//import restaurantepersistencia.IngredienteDAO;
//import restaurantepersistencia.MesaDAO;
//import restaurantepersistencia.ProductoDAO;
//
///**
// * Prueba de Comandas.
// */
//@Disabled("Desactivado temporalmente para no golpear la base de datos en el Clean and Build")
//public class ComandaBOTest {
//
//    // Declaramos nuestras dependencias reales
//    private ComandaBO comandaBO;
//    private MesaDAO mesaDAO;
//    private IngredienteDAO ingredienteDAO;
//    private ProductoDAO productoDAO;
//    private ClienteFrecuenteDAO clienteDAO;
//
//    @BeforeEach
//    public void setUp() {
//        // Inicializamos los objetos reales antes de cada prueba
//        comandaBO = new ComandaBO();
//        mesaDAO = new MesaDAO();
//        ingredienteDAO = new IngredienteDAO();
//        productoDAO = new ProductoDAO();
//        clienteDAO = new ClienteFrecuenteDAO();
//    }
//
//    @Test
//    public void testFlujoCompletoAbrirYEntregarComanda() {
//        try {
//            System.out.println("--- INICIANDO PRUEBA DE INTEGRACIÓN ---");
//
//            // =========================================================
//            // FASE 1: PREPARACIÓN (Guardar datos reales en la BD)
//            // =========================================================
//            // 1.1 Registrar una Mesa Disponible
//            // Generamos un número de mesa aleatorio entre 1000 y 9999 para la prueba
//            int numeroMesaAleatorio = (int) (Math.random() * 9000) + 1000;
//            MesaDTO mesaMoc = new MesaDTO(numeroMesaAleatorio, EnumeradoresDTO.Disponibilidad.DISPONIBLE);
//            Mesa mesaGuardada = mesaDAO.registrarMesa(mesaMoc);
//
//            // 1.2 Registrar un Ingrediente con stock suficiente
//            // Usamos un nombre con un número aleatorio para no violar el UniqueConstraint si corres el test varias veces
//            String nombreIngrediente = "Carne Test " + System.currentTimeMillis();
//            IngredienteDTO ingDTO = new IngredienteDTO(nombreIngrediente, EnumeradoresDTO.TipoUnidad.GRAMOS, new BigDecimal("5000.00"));
//            Ingrediente ingGuardado = ingredienteDAO.nuevoIngrediente(ingDTO);
//
//            // 1.3 Registrar un Producto y su Receta
//            ProductoIngredientesDTO recetaItem = new ProductoIngredientesDTO(ingGuardado.getId(), nombreIngrediente, new BigDecimal("250.00"));
//            List<ProductoIngredientesDTO> receta = new ArrayList<>();
//            receta.add(recetaItem);
//
//            ProductoDTO prodDTO = new ProductoDTO("Hamburguesa Test " + System.currentTimeMillis(),
//                    "Prueba JUnit", "Platillo", 120.0f, true, null, receta);
//            Producto prodGuardado = productoDAO.crearProducto(prodDTO);
//
//            // 1.4 Registrar un Cliente Frecuente
//            ClienteFrecuenteDTO clienteDTO = new ClienteFrecuenteDTO("Roberto", "Garcia", "Prueba", "6441234567", "test@itson.mx", LocalDate.now());
//            ClienteFrecuente clienteGuardado = clienteDAO.crearCliente(clienteDTO);
//
//            // =========================================================
//            // FASE 2: ACCIÓN (Armar el DTO y llamar a abrirComanda)
//            // =========================================================
//            ComandaDTO comandaAAbrir = new ComandaDTO();
//            comandaAAbrir.setComentarios("Sin aderezos por favor");
//            comandaAAbrir.setTotalVenta(120L);
//
//            // Mapeamos la Mesa con el ID generado
//            MesaDTO mesaParaComanda = new MesaDTO(mesaGuardada.getId(), mesaGuardada.getNumMesa(), EnumeradoresDTO.Disponibilidad.DISPONIBLE);
//            comandaAAbrir.setMesa(mesaParaComanda);
//
//            // Mapeamos el Cliente con el ID generado
//            ClienteFrecuenteDTO clienteParaComanda = new ClienteFrecuenteDTO();
//            clienteParaComanda.setId(clienteGuardado.getId());
//            comandaAAbrir.setCliente(clienteParaComanda);
//
//            // Mapeamos los Productos (Solo necesitamos enviar el ID para que el BO lo busque)
//            ProductoDTO productoParaComanda = new ProductoDTO();
//            productoParaComanda.setId(prodGuardado.getId());
//            List<ProductoDTO> listaProductosComanda = new ArrayList<>();
//            listaProductosComanda.add(productoParaComanda);
//            comandaAAbrir.setProductos(listaProductosComanda);
//
//            // ¡EJECUTAMOS EL MÉTODO ESTRELLA!
//            System.out.println("Ejecutando abrirComanda()...");
//            ComandaDTO comandaAbierta = comandaBO.abrirComanda(comandaAAbrir);
//
//            // =========================================================
//            // FASE 3: VERIFICACIONES (Asserts) - Abrir Comanda
//            // =========================================================
//            assertNotNull(comandaAbierta.getId(), "El ID de la comanda no debe ser nulo tras guardarse.");
//            assertNotNull(comandaAbierta.getFolio(), "Se debió generar un folio para la comanda.");
//            assertTrue(comandaAbierta.getFolio().startsWith("OB-"), "El folio debe cumplir el formato OB-YYYYMMDD-XXX");
//            assertEquals(EstadoComandaDTO.ABIERTA, comandaAbierta.getEstado(), "El estado inicial debe ser ABIERTA.");
//
//            System.out.println("Comanda abierta exitosamente con folio: " + comandaAbierta.getFolio());
//
//            // Verificamos en la base de datos real si la mesa cambió a NO_DISPONIBLE
//            Mesa mesaPostAbrir = mesaDAO.listarMesas().stream()
//                    .filter(m -> m.getId().equals(mesaGuardada.getId())).findFirst().get();
//            assertEquals(Disponibilidad.NO_DISPONIBLE, mesaPostAbrir.getDisponible(), "La mesa debió cambiar a NO_DISPONIBLE.");
//
//            // =========================================================
//            // FASE 4: ACCIÓN Y VERIFICACIÓN - Entregar Comanda
//            // =========================================================
//            System.out.println("Ejecutando entregarComanda()...");
//            comandaBO.entregarComanda(comandaAbierta.getId());
//
//            // Consultamos la comanda para ver si cambió de estado
//            ComandaDTO comandaEntregada = comandaBO.consultarPorFolio(comandaAbierta.getFolio());
//            assertEquals(EstadoComandaDTO.ENTREGADA, comandaEntregada.getEstado(), "El estado debió cambiar a ENTREGADA.");
//
//            // Verificamos si la mesa se liberó
//            Mesa mesaPostEntregar = mesaDAO.listarMesas().stream()
//                    .filter(m -> m.getId().equals(mesaGuardada.getId())).findFirst().get();
//            assertEquals(Disponibilidad.DISPONIBLE, mesaPostEntregar.getDisponible(), "La mesa debió liberarse (DISPONIBLE) al entregar la comanda.");
//
//            System.out.println("--- PRUEBA COMPLETADA CON ÉXITO ---");
//
//        } catch (Exception e) {
//            e.printStackTrace(); // Imprime la traza completa en la consola para saber dónde falló
//            fail("La prueba de integración falló por una excepción: " + e.getMessage());
//        }
//    }
//}
////package Pruebas;
////
////import EnumeradoresDominio.Disponibilidad;
////import EnumeradoresDominio.TipoUnidad;
////import EnumeradoresDTO.EstadoComandaDTO;
////import java.math.BigDecimal;
////import java.time.LocalDate;
////import java.util.ArrayList;
////import java.util.List;
////import org.junit.jupiter.api.BeforeEach;
////import org.junit.jupiter.api.Test;
////import static org.junit.jupiter.api.Assertions.*;
////import org.junit.jupiter.api.Disabled;
////
////import restaurantedominio.ClienteFrecuente;
////import restaurantedominio.Ingrediente;
////import restaurantedominio.Mesa;
////import restaurantedominio.Producto;
////import restaurantedtos.ClienteFrecuenteDTO;
////import restaurantedtos.ComandaDTO;
////import restaurantedtos.IngredienteDTO;
////import restaurantedtos.MesaDTO;
////import restaurantedtos.ProductoDTO;
////import restaurantedtos.ProductoIngredientesDTO;
////import restaurantenegocio.ComandaBO;
////import restaurantenegocio.NegocioException;
////import restaurantepersistencia.ClienteFrecuenteDAO;
////import restaurantepersistencia.IngredienteDAO;
////import restaurantepersistencia.MesaDAO;
////import restaurantepersistencia.ProductoDAO;
////
/////**
//// * Prueba de Comandas.
//// */
//////@Disabled("Desactivado temporalmente para no golpear la base de datos en el Clean and Build")
////public class ComandaBOTest {
////
////    // Declaramos nuestras dependencias reales
////    private ComandaBO comandaBO;
////    private MesaDAO mesaDAO;
////    private IngredienteDAO ingredienteDAO;
////    private ProductoDAO productoDAO;
////    private ClienteFrecuenteDAO clienteDAO;
////
////    @BeforeEach
////    public void setUp() {
////        // Inicializamos los objetos reales antes de cada prueba
////        comandaBO = new ComandaBO();
////        mesaDAO = new MesaDAO();
////        ingredienteDAO = new IngredienteDAO();
////        productoDAO = new ProductoDAO();
////        clienteDAO = new ClienteFrecuenteDAO();
////    }
////
////    @Test
////    public void testFlujoCompletoAbrirYEntregarComanda() {
////        try {
////            System.out.println("--- INICIANDO PRUEBA DE INTEGRACIÓN ---");
////
////            // =========================================================
////            // FASE 1: PREPARACIÓN (Guardar datos reales en la BD)
////            // =========================================================
////            // 1.1 Registrar una Mesa Disponible
////            // Generamos un número de mesa aleatorio entre 1000 y 9999 para la prueba
////            int numeroMesaAleatorio = (int) (Math.random() * 9000) + 1000;
////            MesaDTO mesaMoc = new MesaDTO(numeroMesaAleatorio, EnumeradoresDTO.Disponibilidad.DISPONIBLE);
////            Mesa mesaGuardada = mesaDAO.registrarMesa(mesaMoc);
////
////            // 1.2 Registrar un Ingrediente con stock suficiente
////            // Usamos un nombre con un número aleatorio para no violar el UniqueConstraint si corres el test varias veces
////            String nombreIngrediente = "Carne Test " + System.currentTimeMillis();
////            IngredienteDTO ingDTO = new IngredienteDTO(nombreIngrediente, EnumeradoresDTO.TipoUnidad.GRAMOS, new BigDecimal("5000.00"));
////            Ingrediente ingGuardado = ingredienteDAO.nuevoIngrediente(ingDTO);
////
////            // 1.3 Registrar un Producto y su Receta
////            ProductoIngredientesDTO recetaItem = new ProductoIngredientesDTO(ingGuardado.getId(), nombreIngrediente, new BigDecimal("250.00"));
////            List<ProductoIngredientesDTO> receta = new ArrayList<>();
////            receta.add(recetaItem);
////
////            ProductoDTO prodDTO = new ProductoDTO("Hamburguesa Test " + System.currentTimeMillis(),
////                    "Prueba JUnit", "Platillo", 120.0f, true, null, receta);
////            Producto prodGuardado = productoDAO.crearProducto(prodDTO);
////
////            // 1.4 Registrar un Cliente Frecuente
////            ClienteFrecuenteDTO clienteDTO = new ClienteFrecuenteDTO("Roberto", "Garcia", "Prueba", "6441234567", "test@itson.mx", LocalDate.now());
////            ClienteFrecuente clienteGuardado = clienteDAO.crearCliente(clienteDTO);
////
////            // =========================================================
////            // FASE 2: ACCIÓN (Armar el DTO y llamar a abrirComanda)
////            // =========================================================
////            ComandaDTO comandaAAbrir = new ComandaDTO();
////            comandaAAbrir.setComentarios("Sin aderezos por favor");
////            comandaAAbrir.setTotalVenta(120L);
////
////            // Mapeamos la Mesa con el ID generado
////            MesaDTO mesaParaComanda = new MesaDTO(mesaGuardada.getId(), mesaGuardada.getNumMesa(), EnumeradoresDTO.Disponibilidad.DISPONIBLE);
////            comandaAAbrir.setMesa(mesaParaComanda);
////
////            // Mapeamos el Cliente con el ID generado
////            ClienteFrecuenteDTO clienteParaComanda = new ClienteFrecuenteDTO();
////            clienteParaComanda.setId(clienteGuardado.getId());
////            comandaAAbrir.setCliente(clienteParaComanda);
////
////            // Mapeamos los Productos (Solo necesitamos enviar el ID para que el BO lo busque)
////            ProductoDTO productoParaComanda = new ProductoDTO();
////            productoParaComanda.setId(prodGuardado.getId());
////            List<ProductoDTO> listaProductosComanda = new ArrayList<>();
////            listaProductosComanda.add(productoParaComanda);
////            comandaAAbrir.setProductos(listaProductosComanda);
////
////            // ¡EJECUTAMOS EL MÉTODO ESTRELLA!
////            System.out.println("Ejecutando abrirComanda()...");
////            ComandaDTO comandaAbierta = comandaBO.abrirComanda(comandaAAbrir);
////
////            // =========================================================
////            // FASE 3: VERIFICACIONES (Asserts) - Abrir Comanda
////            // =========================================================
////            assertNotNull(comandaAbierta.getId(), "El ID de la comanda no debe ser nulo tras guardarse.");
////            assertNotNull(comandaAbierta.getFolio(), "Se debió generar un folio para la comanda.");
////            assertTrue(comandaAbierta.getFolio().startsWith("OB-"), "El folio debe cumplir el formato OB-YYYYMMDD-XXX");
////            assertEquals(EstadoComandaDTO.ABIERTA, comandaAbierta.getEstado(), "El estado inicial debe ser ABIERTA.");
////
////            System.out.println("Comanda abierta exitosamente con folio: " + comandaAbierta.getFolio());
////
////            // Verificamos en la base de datos real si la mesa cambió a NO_DISPONIBLE
////            Mesa mesaPostAbrir = mesaDAO.listarMesas().stream()
////                    .filter(m -> m.getId().equals(mesaGuardada.getId())).findFirst().get();
////            assertEquals(Disponibilidad.NO_DISPONIBLE, mesaPostAbrir.getDisponible(), "La mesa debió cambiar a NO_DISPONIBLE.");
////
////            // =========================================================
////            // FASE 4: ACCIÓN Y VERIFICACIÓN - Entregar Comanda
////            // =========================================================
////            System.out.println("Ejecutando entregarComanda()...");
////            comandaBO.entregarComanda(comandaAbierta.getId());
////
////            // Consultamos la comanda para ver si cambió de estado
////            ComandaDTO comandaEntregada = comandaBO.consultarPorFolio(comandaAbierta.getFolio());
////            assertEquals(EstadoComandaDTO.ENTREGADA, comandaEntregada.getEstado(), "El estado debió cambiar a ENTREGADA.");
////
////            // Verificamos si la mesa se liberó
////            Mesa mesaPostEntregar = mesaDAO.listarMesas().stream()
////                    .filter(m -> m.getId().equals(mesaGuardada.getId())).findFirst().get();
////            assertEquals(Disponibilidad.DISPONIBLE, mesaPostEntregar.getDisponible(), "La mesa debió liberarse (DISPONIBLE) al entregar la comanda.");
////
////            System.out.println("--- PRUEBA COMPLETADA CON ÉXITO ---");
////
////        } catch (Exception e) {
////            e.printStackTrace(); // Imprime la traza completa en la consola para saber dónde falló
////            fail("La prueba de integración falló por una excepción: " + e.getMessage());
////        }
////    }
////}
