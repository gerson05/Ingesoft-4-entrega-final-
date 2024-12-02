import Demo.BrokerPrx;
import Demo.ServerPrx;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

public class Server implements Demo.Server
{
    public static void main(String[] args) {
        int status = 0;
        Communicator communicator = null;
        try {
            communicator = Util.initialize(args, "config.server");
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("ServerAdapter", "default -p 9099");
            ServerI server = new ServerI();
            ObjectPrx serverProxy = adapter.addWithUUID(server).ice_twoway();
            adapter.activate();

            ObjectPrx base = communicator.stringToProxy("Broker:default -p 10000");
            BrokerPrx broker = BrokerPrx.checkedCast(base);
            if (broker == null) throw new Error("Invalid proxy");
            broker.registerServer(ServerPrx.uncheckedCast(serverProxy));

            System.out.println("Server started...");
            communicator.waitForShutdown();
        } catch (Exception e) {
            e.printStackTrace();
            status = 1;
        }
        if (communicator != null) {
            communicator.destroy();
        }
        System.exit(status);
    }

    @Override
    public void processRequest(String clientId, String requestData, com.zeroc.Ice.Current current) {
        System.out.println("Processing request from client: " + clientId);
        // Process the request and generate response
        String responseData = clientId + ",response data";
        BrokerPrx broker = BrokerPrx.checkedCast(current.con.createProxy(current.id));
        broker.handleServer(current.id.name, responseData);
    }
}