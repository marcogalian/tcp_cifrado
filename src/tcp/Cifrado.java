package tcp;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Cifrado {

    // MÉTODO PARA ENCRIPTAR (Cerrar la caja fuerte)
    public static String encriptar(String datos) throws Exception {
        // A. Preparamos la llave: Convertimos nuestro texto en una "llave real" que Java entienda
        SecretKeySpec sk = new SecretKeySpec(Config.CLAVE.getBytes(), Config.ALGORITMO);
        // B. Pedimos la herramienta: Obtenemos una instancia del motor de cifrado AES
        Cipher c = Cipher.getInstance(Config.ALGORITMO);
        // C. Configuramos el modo: Le decimos al motor que su trabajo ahora es ENCRIPTAR (Cerrar)
        c.init(Cipher.ENCRYPT_MODE, sk);
        // D. Ejecutamos la acción: El texto se convierte en un montón de bytes ilegibles
        byte[] enc = c.doFinal(datos.getBytes());

        // E. Envoltorio de seguridad (Base64): Los bytes raros se pasan a texto normal (A-Z, 0-9)
        // para que viajen por el cable sin romperse
        return Base64.getEncoder().encodeToString(enc);
    }

    // MÉTODO PARA DESENCRIPTAR (abrir la caja fuerte)
    public static String desencriptar(String datosCifrados) throws Exception {
        // A. Igual que antes, preparamos la llave (debe ser la misma)
        SecretKeySpec sk = new SecretKeySpec(Config.CLAVE.getBytes(), Config.ALGORITMO);
        // B. Pedimos la misma herramienta (AES)
        Cipher c = Cipher.getInstance(Config.ALGORITMO);
        // C. Configuramos el modo: Ahora le decimos que su trabajo es DESENCRIPTAR (Abrir)
        c.init(Cipher.DECRYPT_MODE, sk);
        // D. Se le quita el envoltorio: Pasamos de texto Base64 a los bytes encriptados originales
        byte[] dec = Base64.getDecoder().decode(datosCifrados);

        // E. Abrimos la caja: El motor usa la llave para recuperar el texto original en bytes
        // y lo transformamos de nuevo en un String legible
        return new String(c.doFinal(dec));
    }
}