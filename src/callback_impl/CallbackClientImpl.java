package callback_impl;

import callback.ICallbackClient;
import controllers.classes.TaskController;
import journal.IJournalManager;
import utils.RegistryUtils;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CallbackClientImpl extends UnicastRemoteObject implements ICallbackClient, Serializable {
    private String login;
    private String pass;
    public CallbackClientImpl(String login, String pass) throws RemoteException {
        super();
        this.login = login;
        this.pass = pass;
    }

    public void update() {
        IJournalManager manager = null;
        manager = RegistryUtils.getJournalManagerInstance(login);
        if (manager != null) {
            TaskController.getInstance().updateModel(manager);
        }
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return pass;
    }

}
