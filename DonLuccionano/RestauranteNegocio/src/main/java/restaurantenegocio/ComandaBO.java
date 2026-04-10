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
import Interfaces.IIngredienteDAO;
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
import restaurantepersistencia.IngredienteDAO;
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
    private final IIngredienteDAO ingredienteDAO;

    public ComandaBO() {
        this.comandaDAO = new ComandaDAO();
        this.mesaDAO = new MesaDAO();
        this.productoDAO = new ProductoDAO();
        this.clienteFrecuenteDAO = new ClienteFrecuenteDAO();
        this.ingredienteDAO = new IngredienteDAO();
    }

    @Override
    public ComandaDTO abrirComanda(ComandaDTO comandaDTO) throws NegocioException {
        try {
            if (comandaDTO.getDetalles() == null || comandaDTO.getDetalles().isEmpty()) {
                throw new NegocioException("La comanda debe contener al menos un producto.");
            }

            Mesa mesa = mesaDAO.listarMesas().stream()
                    .filter(m -> m.getId().equals(comandaDTO.getMesa().getId()))
                    .findFirst()
                    .orElseThrow(() -> new NegocioException("La mesa seleccionada no existe."));

            if (mesa.getDisponible() != Disponibilidad.DISPONIBLE) {
                throw new NegocioException("La mesa no está disponible. Ya tiene una comanda activa.");
            }

            String folioUnico = generarFolio();

            // REGLA 3: Mapeo, Validación y Multiplicación de Cantidades
            List<restaurantedominio.DetalleComanda> detallesReales = new java.util.ArrayList<>();
            
            for (restaurantedtos.DetalleComandaDTO detalleDTO : comandaDTO.getDetalles()) {
                Producto producto = productoDAO.buscarPorId(detalleDTO.getProducto().getId());

                if (producto == null) throw new NegocioException("El producto seleccionado no existe.");
                if (!producto.isActivo()) throw new NegocioException("El producto '" + producto.getNombre() + "' no está activo.");

                // VALIDACIÓN DE INVENTARIO MULTIPLICADO
                for (restaurantedominio.ProductoIngredientes detalleIng : producto.getListaIngredientes()) {
                    restaurantedominio.Ingrediente ingrediente = detalleIng.getIngredientes();

                    // ¡MAGIA!: Multiplicamos lo que pide la receta por la CANTIDAD que ordenó el cliente
                    java.math.BigDecimal cantidadRequerida = detalleIng.getCantidad().multiply(new java.math.BigDecimal(detalleDTO.getCantidad()));

                    if (ingrediente.getCantidad().compareTo(cantidadRequerida) < 0) {
                        throw new NegocioException("Stock insuficiente de '" + ingrediente.getNombre()
                                + "' para preparar " + detalleDTO.getCantidad() + "x '" + producto.getNombre() + "'.");
                    }
                    java.math.BigDecimal nuevoStock = ingrediente.getCantidad().subtract(cantidadRequerida);
                    ingrediente.setCantidad(nuevoStock);
                    ingredienteDAO.actualizarIngrediente(ingrediente);
                }

                restaurantedominio.DetalleComanda detalleNuevo = new restaurantedominio.DetalleComanda();
                detalleNuevo.setProducto(producto);
                detalleNuevo.setCantidad(detalleDTO.getCantidad());
                detalleNuevo.setSubtotal(detalleDTO.getSubtotal());

                detallesReales.add(detalleNuevo);
            }

            // REGLA 4: Cliente
            ClienteFrecuente clienteReal = null;
            if (comandaDTO.getCliente() != null && comandaDTO.getCliente().getId() != null) {
                clienteReal = clienteFrecuenteDAO.buscarPorId(comandaDTO.getCliente().getId());
                if (clienteReal == null) throw new NegocioException("Cliente no válido.");
            } else {
                List<ClienteFrecuente> clientesGen = clienteFrecuenteDAO.buscarClienteLista("Cliente General", "", "");
                if (clientesGen != null && !clientesGen.isEmpty()) clienteReal = clientesGen.get(0);
            }

            Comanda nuevaComanda = Adapters.NuevaComandaDTOAComandaAdapter.adaptar(
                    comandaDTO, mesa, clienteReal, detallesReales, folioUnico
            );

            comandaDAO.registrarComanda(nuevaComanda);
            mesaDAO.cambiarDisponibilidad(mesa.getId(), Disponibilidad.NO_DISPONIBLE);

            comandaDTO.setId(nuevaComanda.getId());
            comandaDTO.setFolio(folioUnico);
            comandaDTO.setEstado(EnumeradoresDTO.EstadoComandaDTO.ABIERTA);

            return comandaDTO;

        } catch (restaurantepersistencia.PersistenciaException e) {
            throw new NegocioException("Error en la BD al abrir la comanda: " + e.getMessage());
        }
    }

    @Override
    public void actualizarComanda(ComandaDTO comandaDTO) throws NegocioException {
        try {
            Comanda comanda = new Comanda();
            comanda.setId(comandaDTO.getId());
            comanda.setTotalVenta(comandaDTO.getTotalVenta());

            List<restaurantedominio.DetalleComanda> listaDetalles = new java.util.ArrayList<>();
            for (restaurantedtos.DetalleComandaDTO dDTO : comandaDTO.getDetalles()) {
                restaurantedominio.DetalleComanda det = new restaurantedominio.DetalleComanda();
                Producto p = new Producto();
                p.setId(dDTO.getProducto().getId());
                det.setProducto(p);
                det.setCantidad(dDTO.getCantidad());
                det.setSubtotal(dDTO.getSubtotal());
                listaDetalles.add(det);
            }
            comanda.setDetalles(listaDetalles);

            comandaDAO.actualizarComanda(comanda);
            
        } catch (restaurantepersistencia.PersistenciaException ex) {
            throw new NegocioException("Error en negocio al actualizar comanda.", ex);
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
            comandaDAO.cambiarEstado(idComanda, EstadoComanda.ENTREGADA);

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

    @Override
    public List<Comanda> obtenerComandasActivas() throws restaurantenegocio.NegocioException {
        try {
            return comandaDAO.obtenerComandasActivas();
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudieron cargar las comandas activas.", ex);
        }
    }

    @Override
    public Comanda consultarComanda(Long idComanda) throws restaurantenegocio.NegocioException {
        try {
            return comandaDAO.consultarComanda(idComanda);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No fue posible consultar la información de la comanda.", ex);
        }
    }

}
