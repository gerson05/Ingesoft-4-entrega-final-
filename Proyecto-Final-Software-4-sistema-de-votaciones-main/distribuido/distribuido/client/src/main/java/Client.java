import Demo.BrokerPrx;
import Demo.ClientPrx;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Client implements Demo.Client {
    private static ExecutorService executorService;

    public static void main(String[] args) {
        Communicator communicator = null;
        try {
            communicator = Util.initialize(args, "config.client");
            ObjectPrx base = communicator.stringToProxy("Broker:default -p 10000");
            BrokerPrx broker = BrokerPrx.checkedCast(base);
            if (broker == null) throw new Error("Invalid proxy");

            ClientI client = new ClientI();
            ObjectPrx clientProxy = communicator.addWithUUID(client).ice_twoway();
            broker.registerClient(ClientPrx.uncheckedCast(clientProxy));

            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingresa el número de hilos para el pool de consultas:");
            int numThreads = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            executorService = Executors.newFixedThreadPool(numThreads);

            while (true) {
                System.out.println("Ingresa la ruta del archivo con las cédulas:");
                String filePath = scanner.nextLine();
                if (filePath.equals("exit")) {
                    break;
                }
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String cedula;
                    while ((cedula = reader.readLine()) != null) {
                        final String cedulaFinal = cedula;
                        executorService.submit(() -> {
                            System.out.println("Consultando cédula: " + cedulaFinal);
                            broker.handleClient(clientProxy.ice_getIdentity().name, cedulaFinal);
                        });
                    }
                } catch (IOException e) {
                    System.out.println("Error al leer el archivo: " + e.getMessage());
                }
            }

            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            if (communicator != null) {
                communicator.destroy();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveResponse(String responseData, com.zeroc.Ice.Current current) {
        System.out.println("Received response: " + responseData);
        // Process the response
    }
}