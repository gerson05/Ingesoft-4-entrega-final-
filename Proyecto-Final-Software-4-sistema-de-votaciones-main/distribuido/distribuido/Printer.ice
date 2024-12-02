module Demo
{
    // Interfaz del Worker
    interface Worker
    {
        void processTask(string s);
        void shutdown();
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

    // Interfaz del Broker
    interface Broker
    {
        void registerClient(Client* clientProxy);
        void registerServer(Server* serverProxy);
        void handleClient(string clientId, string requestData);
        void handleServer(string serverId, string responseData);
    }

    // Interfaz del Client
    interface Client
    {
        void receiveResponse(string responseData);
    }

    // Interfaz del Server
    interface Server
    {
        void processRequest(string clientId, string requestData);
    }
}