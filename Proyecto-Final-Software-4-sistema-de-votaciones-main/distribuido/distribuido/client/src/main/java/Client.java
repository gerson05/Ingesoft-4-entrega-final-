import Demo.BrokerPrx;
import Demo.ClientPrx;
import Demo.PrinterPrx;
import Demo.Response;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Client implements Demo.Client
{
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

            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Ingresa la ruta del archivo con las cédulas:");
                String filePath = scanner.nextLine();
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String cedula;
                    while ((cedula = reader.readLine()) != null) {
                        System.out.println("Consultando cédula: " + cedula);
                        broker.handleClient(clientProxy.ice_getIdentity().name, cedula);
                    }
                } catch (IOException e) {
                    System.out.println("Error al leer el archivo: " + e.getMessage());
                }
                if (filePath.equals("exit")) {
                    break;
                }
            }
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

    private void logToClientAuditFile(String cedula, String mesa, boolean isPrime, long responseTime) {
        try (FileWriter writer = new FileWriter("client_audit_log.csv", true)) {
            writer.write(cedula + "," + mesa + "," + (isPrime ? 1 : 0) + "," + responseTime + "\n");
        } catch (IOException e) {
            System.out.println("Error escribiendo en el archivo de auditoría del cliente: " + e.getMessage());
        }
    }
}