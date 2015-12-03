package controllers.classes;

import controllers.interfaces.IController;
import controllers.interfaces.ILoginController;
import controllers.interfaces.ITaskController;
import journal.IJournalManager;
import model.IModel;
import model.Model;
import ns.CustomNotificationSystem;
import ns.NotificationSystem;
import observer.Observer;
import utils.RegistryUtils;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MainController implements IController, Observer {
    @Override
    public void start() {
        ILoginController lc = new LoginController();
        lc.registerObserver(this);
        lc.start();
    }

    @Override
    public void stop() {
        try {
            RegistryUtils.unregisterNSystem();
        } catch (RemoteException | NotBoundException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Cannot unregister, trying later again.");
        }
        finally {

            System.exit(1);
        }
    }

    @Override
    public void update(String login) {
        IJournalManager manager = null;
        try {
            manager = RegistryUtils.getJournalManagerInstance(login);
        } catch (RemoteException |NotBoundException e) {
            JOptionPane.showMessageDialog(new JFrame(),"Cannot get journal, trying later again.");
        }
        if (manager != null) {
            IModel model = new Model(manager);
            ITaskController tc = new TaskController(model,new CustomNotificationSystem());
            tc.start();
        }
    }
}
