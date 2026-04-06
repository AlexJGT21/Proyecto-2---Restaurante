package restaurantedtos;

import java.util.List;

/**
 *
 * @author Jaime
 */
public class ProductoDTO {

    // --- ATRIBUTOS ORIGINALES (Intactos para no romper código) ---
    private Long id;
    private String nombre;
    private Float precio;
    private byte[] imagen;

    // --- NUEVOS ATRIBUTOS 
    private String descripcion;
    private String tipo;
    private boolean activo;
    private List<ProductoIngredientesDTO> receta;

    /**
     * Constructor por defecto.
     */
    public ProductoDTO() {
        this.activo = true; // Por defecto lo hacemos activo
    }

    /**
     * Constructor ORIGINAL (No tocar, se mantiene para compatibilidad con
     * código viejo)
     */
    public ProductoDTO(String nombre, Float precio, byte[] imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.activo = true; // Asumimos que los productos creados a la antigua también nacen activos
    }

    /**
     * Constructor NUEVO completo (Para enviar desde la base de datos hacia la
     * vista)
     */
    public ProductoDTO(Long id, String nombre, String descripcion, String tipo, Float precio, boolean activo, byte[] imagen, List<ProductoIngredientesDTO> receta) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
        this.activo = activo;
        this.imagen = imagen;
        this.receta = receta;
    }

    /**
     * Constructor NUEVO sin ID (Para guardar un producto con receta completa
     * desde la vista)
     */
    public ProductoDTO(String nombre, String descripcion, String tipo, Float precio, boolean activo, byte[] imagen, List<ProductoIngredientesDTO> receta) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
        this.activo = activo;
        this.imagen = imagen;
        this.receta = receta;
    }

    // --- GETTERS Y SETTERS ORIGINALES ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    // --- NUEVOS GETTERS Y SETTERS ---
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<ProductoIngredientesDTO> getReceta() {
        return receta;
    }

    public void setReceta(List<ProductoIngredientesDTO> receta) {
        this.receta = receta;
    }
}
