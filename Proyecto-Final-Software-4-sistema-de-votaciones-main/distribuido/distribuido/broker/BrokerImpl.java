import com.zeroc.Ice.Current;

public class BrokerImpl implements Broker {
    private PrinterI server;

    public BrokerImpl(PrinterI server) {
        this.server = server;
    }

    @Override
    public Response sendRequest(String message) {
        return server.printString(message, null);
    }

    @Override
    public void registerWorker(String workerId, WorkerPrx workerProxy) {
        server.registerWorker(workerId, workerProxy, null);
    }

    @Override
    public void receivePartialResult(String taskId, String partialResult) {
        server.receivePartialResult(taskId, partialResult, null);
    }

    @Override
    public void notifyTaskCompletion(String taskId) {
        server.notifyTaskCompletion(taskId, null);
    }
}