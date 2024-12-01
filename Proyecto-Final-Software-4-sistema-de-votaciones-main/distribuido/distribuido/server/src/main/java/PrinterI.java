import Demo.Response;
import Demo.WorkerPrx;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Value;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PrinterI implements Demo.Printer {

    private Map<String, WorkerPrx> workers = new HashMap<>();
    private List<String> allResults = new ArrayList<>();
    private int response = 0;

    @Override
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

    private void assignTasksToWorkers(String cedulaBatch, Current current) {
        // Dividimos el mensaje (un conjunto de cédulas) en varias tareas pequeñas
        String[] cedulas = cedulaBatch.split(",");
        int taskSize = cedulas.length / workers.size();

        // Dividimos las cédulas en tareas y las asignamos a los workers
        for (int i = 0; i < workers.size(); i++) {
            int start = i * taskSize;
            int end = (i + 1) * taskSize;
            if (i == workers.size() - 1) {
                end = cedulas.length;  // Aseguramos que la última tarea cubra todo el rango
            }

            String taskId = "task" + i;
            String taskData = String.join(",", Arrays.copyOfRange(cedulas, start, end));
            assignTaskToWorker(taskId, taskData,current );
        }
    }

    @Override
    public void registerWorker(String workerId, WorkerPrx workerProxy, Current current) {
        System.out.println("Worker registrado: " + workerId);
        System.out.println("Worker registrado: " + workerProxy.toString());
        workers.put(workerId, workerProxy);}

    @Override
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

    @Override
    public void receivePartialResult(String taskId, String partialResult, Current current) { allResults.add(partialResult);}

    @Override
    public void notifyTaskCompletion(String taskId, Current current) {
        System.out.println("Tarea " + taskId + " completada.");
    }

    // Método para registrar los resultados en un archivo de auditoría
    private void logToServerAuditFile(String clientId, String cedula, long responseTime) {
        try (FileWriter writer = new FileWriter("server_audit_log.csv", true)) {
            writer.write(clientId + "," + cedula + "," + responseTime + "\n");
        } catch (IOException e) {
            System.out.println("Error escribiendo en el archivo de auditoría del servidor: " + e.getMessage());
        }
    }

}