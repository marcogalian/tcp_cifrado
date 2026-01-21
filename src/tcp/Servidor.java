package tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {

        // 1. Abrir la "Oficina" (Puerto)
        // Usamos try-with-resources para que el puerto 8080 se libere solo al acabar
        try (ServerSocket serverSocket = new ServerSocket(8080)) {

            System.out.println("Servidor 'Marco' activo. Esperando conexión persistente...");

            // 2. El Bucle de Vida (Servidor siempre encendido)
            // Esto permite que el servidor no se apague nunca
            while (true) {

                // 3. El Recepcionista (Esperar al cliente)
                // El programa se "congela" aquí hasta que alguien conecta
                Socket clientSocket = serverSocket.accept();
                System.out.println("¡Compañero conectado!");

                // 4. Preparar los "Cables de datos" (Streams)
                // 'in' para escuchar (oído) y 'out' para hablar (boca)
                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     // Habla con el cliente enviandole los mensajes automaticos, la boca del servidor
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    // Guardamos el texto cifrado que nos llega desde el cliente
                    String mensajeCifrado;

                    // 5. El Bucle de la Charla (Mantener la conexión viva)
                    // Leemos mensajes mientras el cliente no cuelgue (null)
                    while ((mensajeCifrado = in.readLine()) != null) {

                        // 6. Procesar el Mensaje (Desencriptar -> Mostrar -> Encriptar respuesta)
                        // Usamos nuestra clase Cifrado para entender lo que llega
                        String mensajeClaro = Cifrado.desencriptar(mensajeCifrado);
                        System.out.println("Mensaje cliente: " + mensajeClaro);

                        // El servidor responde para indicar que hay una conextion cliente-servidor y ha sido descifrado el mensaje del cliente
                        String respuesta = "Marco dice: He recibido '" + mensajeClaro + "'";

                        // Enviamos la respuesta protegida
                        out.println(Cifrado.encriptar(respuesta));
                    }
                    // 7. Controlar los Fallos
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
                // Una vez terminada la conversación, el bucle vuelve al PUNTO 3 a esperar a otro cliente
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}