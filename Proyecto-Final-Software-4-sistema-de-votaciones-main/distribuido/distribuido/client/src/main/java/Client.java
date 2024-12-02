import Demo.BrokerPrx;
import Demo.ClientI;
import Demo.ClientPrx;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Client {
    private static ExecutorService executorService;

    public static void main(String[] args) {
        Communicator communicator = null;
        try {
            communicator = Util.initialize(args, "config.client");
            ObjectPrx base = communicator.stringToProxy("Broker:default -p 10000");
            BrokerPrx broker = BrokerPrx.checkedCast(base);
            if (broker == null) throw new Error("Invalid proxy");

            ObjectAdapter adapter = communicator.createObjectAdapter("ClientAdapter");

            ClientI client = new ClientI();

            Identity identity = new Identity(UUID.randomUUID().toString(), "Client");

            adapter.add(client, identity);

            ObjectPrx clientProxy = adapter.createDirectProxy(identity).ice_twoway();

            adapter.activate();

            broker.registerClient(ClientPrx.uncheckedCast(clientProxy));

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the number of threads for the query pool:");
            int numThreads = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            executorService = Executors.newFixedThreadPool(numThreads);

            while (true) {
                System.out.println("Enter the file path with the IDs:");
                String filePath = scanner.nextLine();
                if (filePath.equals("exit")) {
                    break;
                }
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String id;
                    while ((id = reader.readLine()) != null) {
                        final String idFinal = id;
                        executorService.submit(() -> {
                            System.out.println("Querying ID: " + idFinal);
                            broker.handleClient(clientProxy.ice_getIdentity().name, idFinal);
                        });
                    }
                } catch (IOException e) {
                    System.out.println("Error reading the file: " + e.getMessage());
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
}