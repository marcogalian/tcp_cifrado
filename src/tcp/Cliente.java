package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        try {
            String hostname ="Marco.local";
            Socket socket = new Socket(hostname, 8080);
            System.out.println("Conectado al servidor");

            // 2. Crear flujos para enviar y recibir datos
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // 3. Enviar un mensaje al servidor
            out.println("Hola, servidor. Soy el cliente.");

            // 4. Leer la respuesta del servidor
            String respuestaServidor = in.readLine();
            System.out.println("Respuesta del servidor: " + respuestaServidor);

            // 5. Cerrar los recursos
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
