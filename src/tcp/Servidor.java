package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        try {
            // 1. Crear un ServerSocket en el puerto 12345
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("servidor.Servidor escuchando en el puerto 12345...");

            // 2. Esperar y aceptar conexiones de clientes
            Socket clientSocket = serverSocket.accept(); // Aquí se usa accept()
            System.out.println("cliente.Cliente conectado: " + clientSocket.getInetAddress());

            // 3. Crear flujos para la comunicación
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // 4. Leer un mensaje del cliente
            String mensajeCliente = in.readLine();
            System.out.println("Mensaje del cliente: " + mensajeCliente);

            // 5. Enviar una respuesta al cliente
            out.println("Hola, cliente. Recibí tu mensaje: " + mensajeCliente);

            // 6. Cerrar los recursos
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
