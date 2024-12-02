import Demo.BrokerI;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

public class Broker {
    public static void main(String[] args) {
        int status = 0;
        Communicator communicator = null;
        try {
            // Initialize the communicator with the broker configuration
            communicator = Util.initialize(args, "config.broker");

            // Create an object adapter with the name "Broker"
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints(
                    "Broker",
                    "tcp -p 10000"
            );

            // Create and add the Broker implementation
            BrokerI brokerImpl = new BrokerI();
            adapter.add(brokerImpl, Util.stringToIdentity("Broker"));

            // Activate the adapter
            adapter.activate();

            System.out.println("Broker started...");

            // Wait for shutdown
            communicator.waitForShutdown();

        } catch (Exception e) {
            e.printStackTrace();
            status = 1;
        } finally {
            if (communicator != null) {
                communicator.destroy();
            }
            System.exit(status);
        }
    }
}
