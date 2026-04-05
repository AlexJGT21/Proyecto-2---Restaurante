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
import interfaces.IComandaBO;
import restaurantepersistencia.ComandaDAO;
import restaurantepersistencia.MesaDAO;
import Interfaces.IComandaDAO;
import Interfaces.IMesaDAO;
import Interfaces.IProductoDAO;
import java.util.ArrayList;
import restaurantedominio.ClienteFrecuente;
import restaurantedominio.Producto;
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
            // Asumimos que tu IMesaDAO tiene un buscarPorId (si no, hay que agregarlo a tu MesaDAO)
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
                // Nota: Asegúrate de tener el método buscarPorId en tu IProductoDAO
                Producto producto = productoDAO.buscarPorId(prodDTO.getId());
                
                if (producto == null) {
                    throw new NegocioException("El producto seleccionado no existe en la base de datos.");
                }
                
                // TODO: Validar que el producto esté "Activo" (Requerimiento ITSON)
                // if (!producto.isActivo()) { throw new NegocioException("El producto no está activo"); }
                
                // TODO: Validar Inventario de Ingredientes (Requerimiento ITSON)
                // Aquí iría tu ciclo para revisar si hay stock suficiente en la receta
                
                productosReales.add(producto);
            }

            // REGLA 4: Validar y Mapear Cliente Frecuente (Es Opcional)
            ClienteFrecuente clienteReal = null;
            if (comandaDTO.getCliente() != null && comandaDTO.getCliente().getId() != null) {
                // Si viene un ID, buscamos al cliente específico
                // Nota: Asegúrate de tener el método buscarPorId en tu IClienteFrecuenteDAO
                clienteReal = clienteFrecuenteDAO.buscarPorId(comandaDTO.getCliente().getId());
                if (clienteReal == null) {
                    throw new NegocioException("El cliente frecuente seleccionado no es válido.");
                }
            } else {
                // Requerimiento ITSON: "Cliente General"
                // Reutilizamos el método buscarClienteLista que ya tienes en tu DAO
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
        // Lógica para marcar como cancelada y liberar la mesa...
    }

    @Override
    public void entregarComanda(Long idComanda) throws NegocioException {
        // Lógica para marcar como entregada, liberar la mesa y sumar puntos al cliente...
    }

    @Override
    public ComandaDTO consultarPorFolio(String folio) throws NegocioException {
       // Lógica de consulta...
       return null;
    }
}