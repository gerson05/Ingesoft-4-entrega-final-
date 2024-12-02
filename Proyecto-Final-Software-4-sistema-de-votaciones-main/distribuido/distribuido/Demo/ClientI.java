package Demo;

import com.zeroc.Ice.Current;

public class ClientI implements Demo.Client {
    @Override
    public void receiveResponse(String responseData, Current current) {
        System.out.println("Received response: " + responseData);
    }
}