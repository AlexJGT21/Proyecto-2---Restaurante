
package restaurantedominio;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Alex García Trejo
 */
@Converter(autoApply = false)
public class TelefonoConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String telefono) {
        if (telefono == null )  {
            return null;
        }
        return ProteccionDatos.encriptar(telefono);
    }

    @Override
    public String convertToEntityAttribute(String telefono) {
        if (telefono == null) {
            return null;
        }
        return ProteccionDatos.desencriptar(telefono);
    }    
}