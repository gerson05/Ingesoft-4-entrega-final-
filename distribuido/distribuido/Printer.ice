module Demo
{
    // Interfaz del Worker
    interface Worker
    {
        void processTask(string s);
        void shutdown();
    }

    interface Observer {
            void update(string message, string eventType);
    }

    interface Subject {
            void registerObserver(Observer* observer);
            void removeObserver(Observer* observer);
            void notifyObservers(string message, string eventType);
    }

    // Clase de Respuesta
    class Response {
        long responseTime;
        string value;
    }

    // Interfaz del Printer
    interface Printer
    {
        Response printString(string s);
        void registerWorker(string workerId, Worker* workerProxy);
        void assignTaskToWorker(string taskId, string taskData);
        void receivePartialResult(string taskId, string partialResult);
        void notifyTaskCompletion(string taskId);
    }
}
