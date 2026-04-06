package restaurantenegocio;

import Adapters.NuevaComandaDTOAComandaAdapter;
import EnumeradoresDominio.Disponibilidad;
import EnumeradoresDominio.EstadoComanda;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;
import restaurantedominio.Comanda;
import restaurantedominio.Mesa;
import restaurantedtos.ComandaDTO;
import EnumeradoresDTO.EstadoComandaDTO;
import Interfaces.IClienteFrecuenteDAO;
import Interfaces.IComandaBO;
import restaurantepersistencia.ComandaDAO;
import restaurantepersistencia.MesaDAO;
import Interfaces.IComandaDAO;
import Interfaces.IMesaDAO;
import Interfaces.IProductoDAO;
import java.math.BigDecimal;
import java.util.ArrayList;
import restaurantedominio.ClienteFrecuente;
import restaurantedominio.Ingrediente;
import restaurantedominio.Producto;
import restaurantedominio.ProductoIngredientes;
import restaurantedtos.ProductoDTO;
import restaurantepersistencia.ClienteFrecuenteDAO;
import restaurantepersistencia.PersistenciaException;
import restaurantepersistencia.ProductoDAO;

/**
 * @author JAR (JAIME, ALEJANDRO, ROBERTO)
 */
public class ComandaBO implements IComandaBO {

    private static final Logger LOGGER = Logger.getLogger(ComandaBO.class.getName());

    private final IComandaDAO comandaDAO;
    private final IMesaDAO mesaDAO;
    private final IProductoDAO productoDAO;
    private final IClienteFrecuenteDAO clienteFrecuenteDAO;

    public ComandaBO() {
        this.comandaDAO = new ComandaDAO();
        this.mesaDAO = new MesaDAO();
        this.productoDAO = new ProductoDAO();
        this.clienteFrecuenteDAO = new ClienteFrecuenteDAO();
    }

    @Override
    public ComandaDTO abrirComanda(ComandaDTO comandaDTO) throws NegocioException {
        try {
            // REGLA 1: Validar que la comanda tenga al menos un producto
            if (comandaDTO.getProductos() == null || comandaDTO.getProductos().isEmpty()) {
                throw new NegocioException("La comanda debe contener al menos un producto.");
            }

            // REGLA 2: Validar disponibilidad de la mesa
            Mesa mesa = mesaDAO.listarMesas().stream()
                    .filter(m -> m.getId().equals(comandaDTO.getMesa().getId()))
                    .findFirst()
                    .orElseThrow(() -> new NegocioException("La mesa seleccionada no existe."));

            if (mesa.getDisponible() != Disponibilidad.DISPONIBLE) {
                throw new NegocioException("La mesa no está disponible. Ya tiene una comanda activa.");
            }

            // Generamos el Folio
            String folioUnico = generarFolio();

            // REGLA 3: Mapeo y Validación Estricta de Productos e Ingredientes
            List<Producto> productosReales = new ArrayList<>();
            for (ProductoDTO prodDTO : comandaDTO.getProductos()) {

                Producto producto = productoDAO.buscarPorId(prodDTO.getId());

                if (producto == null) {
                    throw new NegocioException("El producto seleccionado no existe en la base de datos.");
                }

                // VALIDACIÓN ITSON 1: Que el producto esté Activo
                if (!producto.isActivo()) {
                    throw new NegocioException("El producto '" + producto.getNombre() + "' no está activo y no se puede vender.");
                }

                // VALIDACIÓN ITSON 2: Validar Inventario de Ingredientes en stock
                // (Nota: Asegúrate de que en tu clase Producto tengas el getter para la lista, ej: getListaIngredientes())
                for (ProductoIngredientes detalle : producto.getListaIngredientes()) {

                    // Ojo aquí: Tu método en ProductoIngredientes se llama getIngredientes() aunque devuelve uno solo
                    Ingrediente ingrediente = detalle.getIngredientes();

                    // Como tu cantidad ya es BigDecimal en ProductoIngredientes, lo pasamos directo
                    BigDecimal cantidadRequerida = detalle.getCantidad();

                    // compareTo devuelve -1 si el stock actual es MENOR a la cantidad requerida
                    if (ingrediente.getCantidad().compareTo(cantidadRequerida) < 0) {
                        throw new NegocioException("Stock insuficiente del ingrediente '" + ingrediente.getNombre()
                                + "' para preparar el platillo '" + producto.getNombre() + "'.");
                    }
                }

                productosReales.add(producto);
            }

            // REGLA 4: Validar y Mapear Cliente Frecuente (Es Opcional)
            ClienteFrecuente clienteReal = null;
            if (comandaDTO.getCliente() != null && comandaDTO.getCliente().getId() != null) {
                clienteReal = clienteFrecuenteDAO.buscarPorId(comandaDTO.getCliente().getId());
                if (clienteReal == null) {
                    throw new NegocioException("El cliente frecuente seleccionado no es válido.");
                }
            } else {
                // Requerimiento ITSON: "Cliente General"
                List<ClienteFrecuente> clientesGen = clienteFrecuenteDAO.buscarClienteLista("Cliente General", "", "");
                if (clientesGen != null && !clientesGen.isEmpty()) {
                    clienteReal = clientesGen.get(0);
                }
            }

            // 4. De DTO a Entidad (Usando el Adapter que creamos)
            Comanda nuevaComanda = NuevaComandaDTOAComandaAdapter.adaptar(
                    comandaDTO, mesa, clienteReal, productosReales, folioUnico
            );

            // 5. Guardar la comanda en la base de datos
            comandaDAO.registrarComanda(nuevaComanda);

            // 6. Cambiar el estado de la mesa a NO DISPONIBLE
            mesaDAO.cambiarDisponibilidad(mesa.getId(), Disponibilidad.NO_DISPONIBLE);

            // 7. Retornar el DTO actualizado con el nuevo ID y Folio
            comandaDTO.setId(nuevaComanda.getId());
            comandaDTO.setFolio(folioUnico);
            comandaDTO.setEstado(EstadoComandaDTO.ABIERTA);

            return comandaDTO;

        } catch (PersistenciaException e) {
            LOGGER.severe(e.getMessage());
            throw new NegocioException("Error en la base de datos al abrir la comanda: " + e.getMessage());
        }
    }

    /**
     * Método que genera el folio con el formato requerido
     */
    private String generarFolio() throws PersistenciaException {
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String fechaFormateada = hoy.format(formatter);

        // Consultamos cuántas comandas van hoy
        Long conteoDia = comandaDAO.consultarCantidadPorDia(hoy);

        // Sumamos 1 y formateamos a 3 dígitos (ej. 001, 002)
        String consecutivo = String.format("%03d", conteoDia + 1);

        return "OB-" + fechaFormateada + "-" + consecutivo;
    }

    @Override
    public void cancelarComanda(Long idComanda) throws NegocioException {
        try {
            // 1. Buscamos la comanda
            Comanda comanda = comandaDAO.buscarPorId(idComanda);
            if (comanda == null) {
                throw new NegocioException("La comanda especificada no existe.");
            }

            // 2. Validamos la regla de negocio: Solo se cancelan comandas abiertas
            if (comanda.getEstado() != EstadoComanda.ABIERTA) {
                throw new NegocioException("Solo se pueden cancelar comandas que estén en estado ABIERTA.");
            }

            // 3. Cambiamos el estado a cancelada y actualizamos
            comanda.setEstado(EstadoComanda.CANCELADA);
            comandaDAO.actualizarComanda(comanda);

            // 4. Liberamos la mesa para que pueda ser usada de nuevo
            mesaDAO.cambiarDisponibilidad(comanda.getMesa().getId(), Disponibilidad.DISPONIBLE);

        } catch (PersistenciaException e) {
            LOGGER.severe(e.getMessage());
            throw new NegocioException("Error en la base de datos al cancelar la comanda: " + e.getMessage());
        }
    }

    @Override
    public void entregarComanda(Long idComanda) throws NegocioException {
        try {
            // 1. Buscamos la comanda
            Comanda comanda = comandaDAO.buscarPorId(idComanda);
            if (comanda == null) {
                throw new NegocioException("La comanda especificada no existe.");
            }

            // 2. Validamos la regla de negocio
            if (comanda.getEstado() != EstadoComanda.ABIERTA) {
                throw new NegocioException("Solo se pueden entregar comandas que estén en estado ABIERTA.");
            }

            // 3. Cambiamos el estado a entregada y actualizamos
            comanda.setEstado(EstadoComanda.ENTREGADA);
            comandaDAO.actualizarComanda(comanda);

            // 4. Liberamos la mesa (los comensales ya terminaron)
            mesaDAO.cambiarDisponibilidad(comanda.getMesa().getId(), Disponibilidad.DISPONIBLE);

            // Nota: El cálculo de puntos ITSON se hará al vuelo en el módulo de clientes 
            // sumando el "totalVenta" de todas las comandas ENTREGADAS de un cliente.
        } catch (PersistenciaException e) {
            LOGGER.severe(e.getMessage());
            throw new NegocioException("Error en la base de datos al entregar la comanda: " + e.getMessage());
        }
    }

    @Override
    public ComandaDTO consultarPorFolio(String folio) throws NegocioException {
        try {
            // 1. Buscamos la entidad real en la base de datos
            Comanda comanda = comandaDAO.buscarPorFolio(folio);

            if (comanda == null) {
                return null; // Retornamos null para que la Vista decida qué mensaje mostrar ("No se encontró", etc.)
            }

            // 2. Mapeamos de Entidad a DTO para enviarlo a la Vista
            // (Lo ideal sería tener un ComandaAComandaDTOAdapter, pero aquí lo mapeo directo)
            ComandaDTO dto = new ComandaDTO();
            dto.setId(comanda.getId());
            dto.setFolio(comanda.getFolio());
            dto.setEstado(EstadoComandaDTO.valueOf(comanda.getEstado().name()));
            dto.setComentarios(comanda.getComentarios());
            dto.setTotalVenta(comanda.getTotalVenta());

            // Nota: Si en tu Vista necesitas mostrar qué mesa y qué cliente tiene este folio, 
            // deberás instanciar y setear el MesaDTO y ClienteFrecuenteDTO aquí mismo basándote en 'comanda'.
            return dto;

        } catch (PersistenciaException e) {
            LOGGER.severe(e.getMessage());
            throw new NegocioException("Error al consultar la comanda por folio: " + e.getMessage());
        }
    }
}
