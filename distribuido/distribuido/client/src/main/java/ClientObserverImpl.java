import Demo.Observer;
import com.zeroc.Ice.Current;
import java.util.ArrayList;
import java.util.List;

public class ClientObserverImpl implements Observer {
    @Override
    public void update(String message, String eventType, Current current) {
        System.out.println("Notificacion del server: [" + eventType + "] " + message);
    }
}
