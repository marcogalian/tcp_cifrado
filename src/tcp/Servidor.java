package tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Servidor 'Marco' activo. Esperando conexión persistente...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("¡Compañero conectado!");

                // Mantenemos la conexión abierta para este cliente
                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String mensajeCifrado;
                    // Bucle que mantiene la charla abierta
                    while ((mensajeCifrado = in.readLine()) != null) {
                        String mensajeClaro = Cifrado.desencriptar(mensajeCifrado);
                        System.out.println("Compañero: " + mensajeClaro);

                        // Responder algo para confirmar
                        String respuesta = "Marco dice: He recibido '" + mensajeClaro + "'";
                        out.println(Cifrado.encriptar(respuesta));
                    }
                } catch (Exception e) {
                    System.out.println("Conexión finalizada con el cliente.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}