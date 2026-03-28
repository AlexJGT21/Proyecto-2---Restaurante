package restaurantenegocio;

import java.time.LocalDate;
import java.util.List;
import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;
import restaurantepersistencia.ClienteFrecuenteDAO;
import restaurantepersistencia.IClienteFrecuenteDAO;
import restaurantepersistencia.PersistenciaException;

/**
 *
 * @author JAR
 */
public class ClienteFrecuenteBO implements IClienteFrecuenteBO {

    private final IClienteFrecuenteDAO clienteDAO;

    public ClienteFrecuenteBO() {
        this.clienteDAO = new ClienteFrecuenteDAO();
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
            ClienteFrecuente cliente = this.clienteDAO.crearCliente(clienteDTO);
            return cliente;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al Crear al Cliente", ex);
        }
    }
//    
//    @Override
//    public List<ClienteFrecuente> buscarNombre(String nombreCliente) throws NegocioException {
//       // Validaciones del puro nombre
//       if (nombreCliente == null || nombreCliente.trim().isEmpty()) {
//            throw new NegocioException("El nombre a buscar no puede estar vacío");
//        }
//         // Inserción en la BDs
//        try {
//            return clienteDAO.buscarNombre(nombreCliente);
//        } catch (PersistenciaException ex) {
//            throw new NegocioException("Error al buscar cliente por nombre: " + ex.getMessage(), ex);
//        }
//    }

    @Override
    public ClienteFrecuente buscarCliente(String nombre, String apellidoP, String apellidoM) throws NegocioException {
        // Validaciones del nombre completo (apellidos)
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new NegocioException("El nombre es obligatorio para la búsqueda");
        }
        if (apellidoP == null || apellidoP.trim().isEmpty()) {
            throw new NegocioException("El apellido paterno es obligatorio para la búsqueda");
        }
        if (apellidoM == null || apellidoM.trim().isEmpty()) {
            throw new NegocioException("El apellido materno es obligatorio para la búsqueda");
        }
        // Inserción en la BDs
        try {
            return clienteDAO.buscarCliente(nombre, apellidoP, apellidoM);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar el cliente completo: " + ex.getMessage(), ex);
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
            return clienteDAO.buscarPorTelefono(telefono);
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
            return clienteDAO.buscarPorCorreo(correo);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar cliente por correo: " + ex.getMessage(), ex);
        }
    }

  
        @Override
        public List<ClienteFrecuente> numeroCliente(String numeroCliente) throws NegocioException {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public List<ClienteFrecuente> emailCliente(String emailCliente) throws NegocioException {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public List<ClienteFrecuente> buscarNombre(String nombreCliente) throws NegocioException {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
                return clienteDAO.buscarClienteLista(nombre, apellidoP, apellidoM);
            } catch (PersistenciaException ex) {
                throw new NegocioException("Error al buscar la lista de clientes: " + ex.getMessage(), ex);
            }
        }
}
