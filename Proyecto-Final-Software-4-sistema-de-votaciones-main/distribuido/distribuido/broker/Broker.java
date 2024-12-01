package com.example.broker;

public interface Broker {
    Response sendRequest(String message);
    void registerWorker(String workerId, WorkerPrx workerProxy);
    void receivePartialResult(String taskId, String partialResult);
    void notifyTaskCompletion(String taskId);
}