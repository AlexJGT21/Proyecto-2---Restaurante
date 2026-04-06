
package restaurantedominio;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Alex García Trejo
 */
public class ProteccionDatos {

    private static final Logger LOGGER = Logger.getLogger(ProteccionDatos.class.getName());    
    
    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "1234567890123456";
    
    public static String encriptar(String dato) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                        
            byte[] cifrado = cipher.doFinal(dato.getBytes());
            return Base64.getEncoder().encodeToString(cifrado);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) { //Madre santa... Ocupo consultar al profe Carlos
            LOGGER.severe("Error al encriptar: " + e.getMessage());
            throw new RuntimeException("Error al encriptar datos, " , e);
        }
    }

    public static String desencriptar(String datoEncriptado) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            
            Cipher ciper = Cipher.getInstance(ALGORITHM);
            ciper.init(Cipher.DECRYPT_MODE, secretKey);
            
            byte[] decodificado = Base64.getDecoder().decode(datoEncriptado);
            byte[] descifrado = ciper.doFinal(decodificado);
            
            return new String(descifrado);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) { //Sigo sin sabes que hice.
            LOGGER.severe("Error al desencriptar: " + e.getMessage());
            throw new RuntimeException("Error al desencriptar datos, " , e);        
        }
    }    
}