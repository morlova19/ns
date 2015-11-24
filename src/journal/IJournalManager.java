package journal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

public interface IJournalManager extends Remote {
    void add(Task task) throws RemoteException;
    void delete(int id) throws RemoteException;
    CopyOnWriteArrayList<Task> getCurrentTasks()throws RemoteException;
    CopyOnWriteArrayList<Task> getCompletedTasks()throws RemoteException;
    void complete(int id)throws RemoteException;
    void delay(int id, Date newDate)throws RemoteException;
    Task get(int id)throws RemoteException;
}
