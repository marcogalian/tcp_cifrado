package tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        // Try-with-resources (Haciendolo asi java se encarga automaticamente de cerrar el recurso incluso si hay errores, "serverSocket")
        try (ServerSocket serverSocket = new ServerSocket(8080)) {

            System.out.println("Servidor 'Marco' activo. Esperando conexión persistente...");

            // Bucle infinito para no cerrar el servidor a no se que el cliente
            while (true) {
                // Modo de espera hasta que el cliente entra en funcionamiento (clic en play)
                // Una vez hace play el cliente se crea el "cable virtual" (clientSocket)
                Socket clientSocket = serverSocket.accept();
                System.out.println("¡Compañero conectado!");

                // Mantenemos la conexión abierta para este cliente
                // Escuchamos al cliente, el oido del servidor
                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     // Habla con el cliente enviandole los mensajes automaticos, la boca del servidor
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    // Guardamos el texto cifrado que nos llega desde el cliente
                    String mensajeCifrado;

                    /** Bucle más importante, para que la conexion sea persistente
                     / in.readLine el servidor intenta leer la línea que se envía desde el cliente, si no es nulo...
                     / si el mensaje es nulo la conexion se cierra con el cliente actual y volvemos
                     / al accept() de mas arriba
                     **/
                    while ((mensajeCifrado = in.readLine()) != null) {
                        // Usamos la llave secreta y convertimos el mensaje encriptado en mensaje legible
                        String mensajeClaro = Cifrado.desencriptar(mensajeCifrado);
                        System.out.println("Mensaje cliente: " + mensajeClaro);

                        // El servidor responde para indicar que hay una conextion cliente-servidor y ha sido descifrado el mensaje del cliente
                        String respuesta = "Marco dice: He recibido '" + mensajeClaro + "'";
                        // El servidor encripta el mensaje para que solo lo descifre el cliente con la misma llave que
                        // tiene el servidor, ubicado en la clase Cifrado, clase que debe estar tengo en cliente como en servidor
                        out.println(Cifrado.encriptar(respuesta));
                    }
                } catch (IOException e) {
                    // Excepcion que captura fallos de red o de lectura/escritura
                    System.err.println("Error de red: El cliente se ha desconectado o el puerto falló.");
                } catch (javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException e) {
                    // Excepcion que captura si la llave del cliente es distinta a la mia
                    System.err.println("Error de seguridad: Las llaves de cifrado no coinciden.");
                }catch (Exception e) {
                    // Excepción comodín para cualquier otro tipo de fallo
                    System.err.println("Error inesperado: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}