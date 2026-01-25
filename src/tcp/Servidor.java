package tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {

        // 1. Abrir la "Oficina" (Puerto)
        try (ServerSocket serverSocket = new ServerSocket(Config.SERVER_PORT)) {

            System.out.println("Servidor 'Marco' activo. Esperando conexión persistente...");

            // 2. El Bucle de Vida (Servidor siempre encendido)
            while (true) {

                // 3. El Recepcionista (Esperar al cliente)
                Socket clientSocket = serverSocket.accept();
                System.out.println("¡Compañero conectado!");

                // 4. Preparar los "Cables de datos" (Streams)
                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String mensajeCifrado;

                    // 5. El Bucle de la Charla
                    while ((mensajeCifrado = in.readLine()) != null) {

                        // Mostrar mensaje cifrado
                        System.out.println("\nMensaje cifrado: " + mensajeCifrado);

                        // Desencriptar y mostrar
                        String mensajeClaro = Cifrado.desencriptar(mensajeCifrado);
                        System.out.println("Mensaje del cliente: " + mensajeClaro);

                        // Responder: "OK, mensaje [texto] recibido"
                        String respuesta = "OK, mensaje " + mensajeClaro + " recibido";
                        out.println(Cifrado.encriptar(respuesta));
                    }
                } catch (IOException e) {
                    System.err.println("Error de red: El cliente se ha desconectado o el puerto falló.");
                } catch (javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException e) {
                    System.err.println("Error de seguridad: Las llaves de cifrado no coinciden.");
                } catch (Exception e) {
                    System.err.println("Error inesperado: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
