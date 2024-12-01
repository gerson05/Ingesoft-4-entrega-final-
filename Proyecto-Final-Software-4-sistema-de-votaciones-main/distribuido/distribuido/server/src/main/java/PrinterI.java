import com.zeroc.Ice.Current;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrinterI {
    private List<String> allResults = new ArrayList<>();
    private int response = 0;

    public Response printString(String message, Current __current) {
        allResults.clear();
        String id = String.valueOf(response);
        long startTime = System.currentTimeMillis();
        System.out.println("Mensaje recibido: " + message);
        assignTasksToWorkers(message, __current);

        long endTime = System.currentTimeMillis();
        response++;
        logToServerAuditFile(id, message, endTime - startTime);
        return new Response(endTime - startTime, String.join("\n", allResults));
    }

    public void registerWorker(String workerId, WorkerPrx workerProxy, Current current) {
        System.out.println("Worker registrado: " + workerId);
        System.out.println("Worker registrado: " + workerProxy.toString());
        workers.put(workerId, workerProxy);
    }

    public void assignTaskToWorker(String taskId, String taskData, Current current) {
        System.out.println("llegamos hasta aqui");
        if (!workers.isEmpty()) {
            System.out.println("Asignando tarea " + taskId + " a un worker...");
            WorkerPrx worker = workers.values().iterator().next();  // Seleccionamos un worker disponible
            worker.processTask(taskData);
        } else {
            System.out.println("No hay workers disponibles para asignar tareas.");
        }
    }

    public void receivePartialResult(String taskId, String partialResult, Current current) {
        allResults.add(partialResult);
    }

    public void notifyTaskCompletion(String taskId, Current current) {
        System.out.println("Tarea " + taskId + " completada.");
    }

    private void logToServerAuditFile(String clientId, String cedula, long responseTime) {
        try (FileWriter writer = new FileWriter("server_audit_log.csv", true)) {
            writer.write(clientId + "," + cedula + "," + responseTime + "\n");
        } catch (IOException e) {
            System.out.println("Error escribiendo en el archivo de auditoría del servidor: " + e.getMessage());
        }
    }
}