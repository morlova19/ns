package model;

import journal.IJournalManager;
import journal.Task;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Part of taskmgr.
 */
public class Model implements IModel {

    private IJournalManager manager;

    public Model(IJournalManager manager) {
        this.manager = manager;
    }

    @Override
    public CopyOnWriteArrayList<Task> getCurrentTasks() {
        try {

            return manager.getCurrentTasks();

        }
        catch (RemoteException e)
        {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Task get(int id) {
        try {
            return manager.get(id);
        } catch (RemoteException e) {
            return null;
        }

    }


    @Override
    public void delay(int id, Date newDate) {
        try {
            manager.delay(id, newDate);
        } catch (RemoteException e) {

        }
    }

    @Override
    public void complete(int id) {
        try {
            manager.complete(id);
        } catch (RemoteException e) {

        }
    }

}
