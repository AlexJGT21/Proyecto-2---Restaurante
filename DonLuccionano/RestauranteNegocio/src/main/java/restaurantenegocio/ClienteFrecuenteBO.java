
package restaurantenegocio;

import Conexion.ManejadorConexiones;
import Interfaces.IClienteFrecuenteBO;
import java.time.LocalDate;
import java.util.List;
import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;
import restaurantepersistencia.ClienteFrecuenteDAO;
import Interfaces.IClienteFrecuenteDAO;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import restaurantedtos.ClienteFrecuenteReporteDTO;
import restaurantepersistencia.PersistenciaException;

/**
 *
 * @author JAR
 */
public class ClienteFrecuenteBO implements IClienteFrecuenteBO {

    private final IClienteFrecuenteDAO clienteFrecuenteDAO;
    private static final Logger LOGGER = Logger.getLogger(ClienteFrecuenteBO.class.getName());        
    
    public ClienteFrecuenteBO() {
        this.clienteFrecuenteDAO = new ClienteFrecuenteDAO();
    }

    @Override
    public ClienteFrecuente crearCliente(ClienteFrecuenteDTO clienteDTO) throws NegocioException {
        if (clienteDTO == null) {
            throw new NegocioException("El Cliente No Puede Ser Nulo");
        }

        // Validaciones para el Nombre
        if (clienteDTO.getNombre() == null) {
            throw new NegocioException("El Nombre es Obligatorio");
        }
        if (clienteDTO.getNombre().trim().isEmpty()) {
            throw new NegocioException("El Nombre del Cliente no Puede Estar Vacio");
        }
        if (clienteDTO.getNombre().length() > 20) {
            throw new NegocioException("El Nombre es muy Largo, Excede los 20 Caracteres");
        }

        // Validaciones para el Apellido Paterno 
        if (clienteDTO.getApellidoPaterno() == null) {
            throw new NegocioException("El Apellido Paterno es Obligatorio");
        }
        if (clienteDTO.getApellidoPaterno().trim().isEmpty()) {
            throw new NegocioException("El Apellido Paterno no Puede Estar Vacio");
        }
        if (clienteDTO.getApellidoPaterno().length() > 20) {
            throw new NegocioException("El Apellido Paterno es Muy Largo Excede los 20 Caracteres");
        }

        // Validaciones para el Apellido Materno 
        if (clienteDTO.getApellidoMaterno() == null) {
            throw new NegocioException("El Apellido Materno es Obligatorio");
        }
        if (clienteDTO.getApellidoMaterno().trim().isEmpty()) {
            throw new NegocioException("El Apellido Maternos es Obligatorio No Puede Estar Vacio");
        }
        if (clienteDTO.getApellidoMaterno().length() > 20) {
            throw new NegocioException("El Apellido Materno es Muy Largo Excede los 20 Caracteres");
        }

        // Validaciones para el Teléfono 
        if (clienteDTO.getTelefono() == null) {
            throw new NegocioException("El Teléfono es Obligatorio");
        }
        if (clienteDTO.getTelefono().trim().isEmpty()) {
            throw new NegocioException("El Teléfono no Puede Estar Vacio");
        }
        if (!clienteDTO.getTelefono().matches("\\d{10}")) {
            throw new NegocioException("El Teléfono debe contener exactamente 10 dígitos numéricos");
        }

        // Validaciones para el Email (Por si llega a usarse)
        if (clienteDTO.getEmail() != null && !clienteDTO.getEmail().trim().isEmpty()) {
            if (clienteDTO.getEmail().length() > 50) {
                throw new NegocioException("El Correo es Muy Largo Excede los 50 Caracteres");
            }
            if (!clienteDTO.getEmail().contains("@") || !clienteDTO.getEmail().contains(".")) {
                throw new NegocioException("El Formato del Correo Electrónico es Inválido");
            }
        }

        // Validaciones para la Fecha de Registro 
        if (clienteDTO.getFechaRegistro() == null) {
            throw new NegocioException("La Fecha de Registro es Obligatoria");
        }

        LocalDate fechaHoy = LocalDate.now();
        if (clienteDTO.getFechaRegistro().isAfter(fechaHoy)) {
            throw new NegocioException("La Fecha De Registro No Puede Ser Futura");
        }

        // Inserción en la BDs
        try {
            ClienteFrecuente cliente = this.clienteFrecuenteDAO.crearCliente(clienteDTO);
            return cliente;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al Crear al Cliente", ex);
        }
    }
    
     @Override
    public List<ClienteFrecuente> buscarClienteLista(String nombre, String apellidoP, String apellidoM) throws NegocioException {        
        // Verificamos cuales de los campos traen texto
        boolean hasNombre = nombre != null && !nombre.trim().isEmpty();
        boolean hasApellidoP = apellidoP != null && !apellidoP.trim().isEmpty();
        boolean hasApellidoM = apellidoM != null && !apellidoM.trim().isEmpty();
        
        // Obligamos a poner si o si un dato
        if (!hasNombre && !hasApellidoP && !hasApellidoM) {
            throw new NegocioException("Debe ingresar al menos un criterio de búsqueda (Nombre o Apellidos).");
        }
        
        // Validamos la longitudes máximas de los datos
        if (hasNombre && nombre.length() > 20) {
            throw new NegocioException("El nombre no puede exceder los 20 caracteres.");
        }
        if (hasApellidoP && apellidoP.length() > 20) {
            throw new NegocioException("El apellido paterno no puede exceder los 20 caracteres.");
        }
        if (hasApellidoM && apellidoM.length() > 20) {
            throw new NegocioException("El apellido materno no puede exceder los 20 caracteres.");
        }
        
        try {
            // Llamamos al método de la DAO jijija
            return clienteFrecuenteDAO.buscarClienteLista(nombre, apellidoP, apellidoM);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar la lista de clientes: " + ex.getMessage(), ex);
        }
    }
    
    @Override
    public ClienteFrecuente buscarPorTelefono(String telefono) throws NegocioException {
        // Validaciones del teléfono del cliente
       if (telefono == null || telefono.trim().isEmpty()) {
            throw new NegocioException("El teléfono a buscar no puede estar vacío");
        }
        if (!telefono.matches("\\d{10}")) {
            throw new NegocioException("El teléfono a buscar debe tener exactamente 10 dígitos numéricos");
        }
        // Inserción en la BDs
        try {
            return clienteFrecuenteDAO.buscarPorTelefono(telefono);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar cliente por teléfono: " + ex.getMessage(), ex);
        }
    }
    
    @Override
    public ClienteFrecuente buscarPorCorreo(String correo) throws NegocioException {
        // Validaciones del correo del cliente
        if (correo == null || correo.trim().isEmpty()) {
            throw new NegocioException("El correo a buscar no puede estar vacío");
        }
        if (!correo.contains("@")) {
            throw new NegocioException("El formato del correo a buscar es inválido");
        }
        // Inserción en la BDs
        try {
            return clienteFrecuenteDAO.buscarPorCorreo(correo);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar cliente por correo: " + ex.getMessage(), ex);
        }
    }       

    /**
     * Metodo que crea una lista de clientes frecuentes
     * @return Lista de clientes frecuentes
     * @throws NegocioException En caso de existir un error al listar clientes
     */
    @Override
    public List<ClienteFrecuenteDTO> listaClientesF() throws NegocioException {
        try {
            List<ClienteFrecuenteDTO> lista = clienteFrecuenteDAO.listaClientesF();
            return lista;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al generar lista de clientes frecuentes: " + e.getMessage());
        }
    }

    /**
     * Este metodo sera usado por el modulo de comandas para pasarle el ID del cliente y el total de venta, para hacer los
     * calculos necesarios y actualizar su vista
     * @param idCliente Argumento de busqueda
     * @param totalVenta Parametro para iniciar los calculos correspondientes
     * @return Visita del cliente actualizado
     * @throws NegocioException No se pudo realizar la actualizacion del cliente
     */
    @Override
    public ClienteFrecuente actualizarVisita(Long idCliente, Double totalVenta) throws NegocioException {
        try {
            ClienteFrecuente clienteFrecuente = clienteFrecuenteDAO.buscarPorId(idCliente);
            
            if (clienteFrecuente == null) {
                throw new NegocioException("El cliente no existe.");
            }
            
            if (clienteFrecuente.getTotalVisitas() == null) {
            clienteFrecuente.setTotalVisitas(0);
            }
            if (clienteFrecuente.getTotalGastado() == null) {
                clienteFrecuente.setTotalGastado(0.0);
            }
            
            clienteFrecuente.setTotalVisitas(clienteFrecuente.getTotalVisitas()+ 1);
            clienteFrecuente.setTotalGastado(clienteFrecuente.getTotalGastado() + totalVenta);
            double total = clienteFrecuente.getTotalGastado();
            int puntos = (int)(total / 20);
            clienteFrecuente.setPuntos(puntos);            
            return clienteFrecuenteDAO.actualizarVisita(clienteFrecuente);
        } catch (PersistenciaException e) {
            throw new NegocioException("NO SE PUDO ACTUALIZAR LOS PUNTOS, VISITAS Y TOTAL GASTADO DEL CLIENTE FRECUENTE.");
        }
    }

    @Override
    public List<ClienteFrecuenteReporteDTO> filtrarClientes(String nombre, Integer visitas) throws NegocioException {
        try {
            List<ClienteFrecuenteReporteDTO> resultado = clienteFrecuenteDAO.filtrarClientes(nombre, visitas);
            return resultado;
        } catch (PersistenciaException e) {
            throw new NegocioException("NO FUE POSIBLE REALIZAR FILTRADO DE CLIENTES FRECUENTES.");
        }        
    }

    /**
     * Metodo que genera el reporte de Jasper
     * @param nombre Parametro filtro
     * @param visitas Parametro de filtro
     * @return true si se pudo generar el reporte, false en caso contrario
     * @throws NegocioException Error al generar el reporte
     */
    @Override
    public boolean generarReporteClientesFrecuentes(String nombre, Integer visitas) throws NegocioException {
        try {
            //Se carga el reporte desde la carpeta resources (recursos)
            Connection conexion = ManejadorConexiones.crearConexionJDBC();
            InputStream reporte = getClass().getClassLoader().getResourceAsStream("reportes/ClienteFrecuente.jasper");
            if (reporte == null) {
                throw new NegocioException("No se encontro el reporte.");
            }            
            
            //Parametros de reporte
            Map<String, Object> parametros = new HashMap<>();
            
            String nombreParam = (nombre == null || nombre.trim().isEmpty()) ? null : nombre;
            Integer visitasParam = (visitas == null || visitas == 0) ? null : visitas;
            
            parametros.put("nombre", nombreParam);
            parametros.put("visitas", visitasParam);
            //LLenado del reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, conexion);
            
            //Se elige donde se guarda
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar reporte");
            
            //Filtro para mostrar solo archivos pdf
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos PDF (*.pdf)", "pdf");            
            fileChooser.addChoosableFileFilter(filtro);
            fileChooser.setFileFilter(filtro);
            
            int result = fileChooser.showSaveDialog(null);
            if (result != JFileChooser.APPROVE_OPTION) {
                return false;
            }
            
            File archivoGuardar = fileChooser.getSelectedFile();
            String archivoRuta = archivoGuardar.getAbsolutePath();
            if (!archivoRuta.toLowerCase().endsWith(".pdf") ) {
                archivoRuta += ".pdf";
            }
            JasperExportManager.exportReportToPdfFile(jasperPrint, archivoRuta);            
            return true;
        } catch (JRException e) {
            LOGGER.severe(e.getMessage());
            throw new NegocioException("Error al generar reporte.");
        } catch (SQLException ex) {
            LOGGER.severe(ex.getMessage());
            throw new NegocioException("Erro al realizar conexion con la base de datos.");
        }                
    }
}
