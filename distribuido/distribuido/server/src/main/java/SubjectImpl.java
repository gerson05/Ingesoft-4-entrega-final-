import Demo.Observer;
import Demo.ObserverPrx;
import Demo.Subject;
import com.zeroc.Ice.Current;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SubjectImpl implements Subject {
    private List<ObserverPrx> observers = new CopyOnWriteArrayList<>();

    @Override
    public void registerObserver(ObserverPrx observer, Current current) {
        if (observer != null) {
            observers.add(observer);
            System.out.println("Nuevo Observador registrado: " + observer);
            System.out.println("Total observers: " + observers.size());
        } else {
            System.err.println("Attempted to register null observer");
        }
    }

    @Override
    public void removeObserver(ObserverPrx observer, Current current) {
        observers.remove(observer);
        System.out.println("Observador eliminado: " + observer);
    }

    @Override
    public void notifyObservers(String message, String eventType, Current current) {
        System.out.println("Notifying " + observers.size() + " observers");
        List<ObserverPrx> observersCopy = new ArrayList<>(observers);

        for (int i = observersCopy.size() - 1; i >= 0; i--) {
            ObserverPrx observer = observersCopy.get(i);
            try {
                if (observer != null) {
                    System.out.println("Attempting to notify observer: " + observer);

                    if (observer.ice_isA("::Demo::Observer")) {
                        observer.update(message, eventType);
                        System.out.println("Successfully notified observer");
                    } else {
                        System.err.println("Invalid observer proxy detected");
                        observers.remove(observer);
                    }
                } else {
                    System.err.println("Null observer detected at index " + i);
                    observers.remove(i);
                }
            } catch (Exception e) {
                System.err.println("Detailed error notifying observer: " + e);
                e.printStackTrace();

                observers.remove(i);
            }
        }
    }
}
