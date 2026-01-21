package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            // 1. Para conectarse al servidor usando la configuración
            Socket socket = new Socket(Config.SERVER_HOSTNAME, Config.SERVER_PORT);
            System.out.println("Conectado al servidor");

            // 2. Crear flujos para enviar y recibir datos
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Scanner para leer mensajes del usuario
            Scanner scanner = new Scanner(System.in);

            // 3. Bucle para enviar múltiples mensajes
            while (true) {
                System.out.print("\nEscribe un mensaje (o 'salir' para terminar): ");
                String mensajeOriginal = scanner.nextLine();

                if (mensajeOriginal.equalsIgnoreCase("salir")) {
                    break;
                }

                // Cifrar y enviar
                String mensajeCifrado = Cifrado.encriptar(mensajeOriginal);
                System.out.println("Mensaje cifrado enviado: " + mensajeCifrado);
                out.println(mensajeCifrado);

                // Recibir respuesta del servidor
                String respuestaCifrada = in.readLine();
                String respuesta = Cifrado.desencriptar(respuestaCifrada);
                System.out.println("Respuesta del servidor: " + respuesta);
            }

            // 4. Cerrar los recursos
            scanner.close();
            in.close();
            out.close();
            socket.close();
            System.out.println("\nConexión cerrada.");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error de cifrado: " + e.getMessage());
        }
    }

}
