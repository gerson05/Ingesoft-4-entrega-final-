import com.example.broker.Broker;
import com.example.broker.BrokerImpl;
import Demo.Response;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.*;

public class Client {
    private Broker broker;
    private ExecutorService executor;

    public Client(Broker broker, int poolSize) {
        this.broker = broker;
        this.executor = Executors.newFixedThreadPool(poolSize);
    }

    public static void main(String[] args) {
        Communicator communicator = null;
        try {
            communicator = Util.initialize(args, "config.client");
            PrinterI server = new PrinterI();
            Broker broker = new BrokerImpl(server);
            Client client = new Client(broker, 10); // Create a thread pool with 10 threads

            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Ingresa la ruta del archivo con las cédulas:");
                String filePath = scanner.nextLine(); // Obtener la ruta del archivo desde el usuario
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String cedula;
                    while ((cedula = reader.readLine()) != null) {
                        // Enviar cada cédula al broker usando el thread pool
                        System.out.println("Consultando cédula: " + cedula);
                        Future<Response> future = client.executor.submit(new Task(client.broker, cedula));
                        Response response = future.get(); // Esperar a que la tarea termine y obtener la respuesta
                        System.out.println("Respuesta del servidor: " + response.getResults());
                        System.out.println("Tiempo de espera: " + response.getResponseTime() + "ms");
                    }
                } catch (IOException | InterruptedException | ExecutionException e) {
                    System.out.println("Error al procesar la tarea: " + e.getMessage());
                }
                if (filePath.equals("exit")) {
                    break;
                }
            }
            if (communicator != null) {
                communicator.destroy();
            }
            client.executor.shutdown(); // Cerrar el thread pool

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}