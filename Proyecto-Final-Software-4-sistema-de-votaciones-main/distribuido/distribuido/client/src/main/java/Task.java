import java.util.concurrent.Callable;

public class Task implements Callable<Response> {
    private Broker broker;
    private String message;

    public Task(Broker broker, String message) {
        this.broker = broker;
        this.message = message;
    }

    @Override
    public Response call() throws Exception {
        return broker.sendRequest(message);
    }
}