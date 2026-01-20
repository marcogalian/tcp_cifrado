package tcp;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Cifrado {
    private static final String ALGORITMO = "AES";
    private static final String CLAVE = "ABC_123_SECRET_K"; // 16 caracteres

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