package Demo;

import com.zeroc.Ice.Current;

public class ServerI implements Demo.Server {
    @Override
    public void processRequest(String clientId, String requestData, Current current) {
        System.out.println("Processing request from client: " + clientId);
        // Process the request and generate response
        String responseData = clientId + ",response data";
        BrokerPrx broker = BrokerPrx.checkedCast(current.con.createProxy(current.id));
        broker.handleServer(current.id.name, responseData);
    }
}