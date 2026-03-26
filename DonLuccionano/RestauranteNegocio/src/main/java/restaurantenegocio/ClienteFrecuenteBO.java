package restaurantenegocio;

import java.time.LocalDate;
import restaurantedominio.ClienteFrecuente;
import restaurantedtos.ClienteFrecuenteDTO;
import restaurantepersistencia.ClienteFrecuenteDAO;
import restaurantepersistencia.IClienteFrecuente;
import restaurantepersistencia.PersistenciaException;

/**
 *
 * @author Jaime
 */
public class ClienteFrecuenteBO implements IClienteFrecuenteBO {

    private final IClienteFrecuente clienteDAO;

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

    @Override
    public ClienteFrecuente buscarCliente(String nombre, String apellidoP, String apellidoM) throws NegocioException {
        try {
            return this.clienteDAO.buscarCliente(nombre, apellidoP, apellidoM);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar el cliente");
        }
    }

    @Override
    public ClienteFrecuente buscarPorTelefono(String telefono) throws NegocioException {
        try {
            return this.clienteDAO.buscarPorTelefono(telefono);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar cliente por teléfono");
        }
    }

    public ClienteFrecuente buscarPorCorreo(String correo) throws NegocioException {
        try {
            return this.clienteDAO.buscarPorCorreo(correo);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar cliente por correo");
        }
    }
}
