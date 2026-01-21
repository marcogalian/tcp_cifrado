package tcp;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Cifrado {
    // 1. Elegimos el tipo de cerradura a utilizar (Algoritmo)
    private static final String ALGORITMO = "AES"; // AES es el estándar de seguridad más usado

    // 2. Creamos la llave fisica
    // Para AES, debe tener exactamente 16, 24 o 32 caracteres (bytes)
    private static final String CLAVE = "ABC_123_SECRET_K";

    public static String encriptar(String datos) throws Exception {
        SecretKeySpec sk = new SecretKeySpec(CLAVE.getBytes(), ALGORITMO);
        Cipher c = Cipher.getInstance(ALGORITMO);
        c.init(Cipher.ENCRYPT_MODE, sk);
        byte[] enc = c.doFinal(datos.getBytes());
        return Base64.getEncoder().encodeToString(enc);
    }

    public static String desencriptar(String datosCifrados) throws Exception {
        SecretKeySpec sk = new SecretKeySpec(CLAVE.getBytes(), ALGORITMO);
        Cipher c = Cipher.getInstance(ALGORITMO);
        c.init(Cipher.DECRYPT_MODE, sk);
        byte[] dec = Base64.getDecoder().decode(datosCifrados);
        return new String(c.doFinal(dec));
    }
}