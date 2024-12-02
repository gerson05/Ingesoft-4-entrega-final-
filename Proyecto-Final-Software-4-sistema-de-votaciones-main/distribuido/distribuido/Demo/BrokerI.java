package Demo;
import Demo.ClientPrx;
import Demo.ServerPrx;
import com.zeroc.Ice.Current;
import java.util.HashMap;
import java.util.Map;

    private Map<String, ClientPrx> clients = new HashMap<>();
    private Map<String, ServerPrx> servers = new HashMap<>();

    @Override
    public void registerClient(ClientPrx client, Current current) {
        String clientId = current.id.name;
        clients.put(clientId, client);
        System.out.println("Client registered: " + clientId);
    }

    @Override
    public void registerServer(ServerPrx server, Current current) {
        String serverId = current.id.name;
        servers.put(serverId, server);
        System.out.println("Server registered: " + serverId);
    }

    @Override
    public void handleClient(String clientId, String requestData, Current current) {
        if (!servers.isEmpty()) {
            ServerPrx server = servers.values().iterator().next();
            server.processRequest(clientId, requestData);
        } else {
            System.out.println("No servers available to handle client request.");
        }
    }

    @Override
    public void handleServer(String serverId, String responseData, Current current) {
        String clientId = responseData.split(",")[0];
        if (clients.containsKey(clientId)) {
            ClientPrx client = clients.get(clientId);
            client.receiveResponse(responseData);
        } else {
            System.out.println("Client not found for response.");
        }
    }
}