package client_impl;

import client.IClient;
import controllers.classes.TaskController;
import journal.IJournalManager;
import utils.RegistryUtils;

import javax.swing.*;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements IClient, Serializable {
    private String login;
    private String pass;
    public Client(String login, String pass) throws RemoteException {
        super();
        this.login = login;
        this.pass = pass;
    }

    public void update() {
        IJournalManager manager = null;
        try {
            manager = RegistryUtils.getJournalManagerInstance(login);
        } catch (RemoteException | NotBoundException e) {
            JOptionPane.showMessageDialog(new JFrame(),"Cannot get journal, try later again.");
        }
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
