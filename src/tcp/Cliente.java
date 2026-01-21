package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        // 1. Para conectarse al servidor
        String hostname = "Marco.local";
        try (Socket socket = new Socket(hostname, 8080);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Conectado a Marco. Escribe mensajes (o 'salir' para terminar):");

            while (true) {
                System.out.print("> ");
                String texto = sc.nextLine();

                if (texto.equalsIgnoreCase("salir")) break;
                // 1. Enviar cifrado
                out.println(Cifrado.encriptar(texto));

                // 2. Recibir y descifrar respuesta
                String respuestaCifrada = in.readLine();
                if (respuestaCifrada != null) {
                    System.out.println("Respuesta: " + Cifrado.desencriptar(respuestaCifrada));


                }
            }

        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }

}
