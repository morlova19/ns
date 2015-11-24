package controllers.classes;

import controllers.interfaces.IController;
import controllers.interfaces.ILoginController;
import controllers.interfaces.ITaskController;
import journal.IJournalManager;
import model.IModel;
import model.Model;
import ns.NotificationSystem;
import observer.Observer;
import utils.RegistryUtils;

public class MainController implements IController, Observer {
    @Override
    public void start() {
        ILoginController lc = new LoginController();
        lc.registerObserver(this);
        lc.start();
    }

    @Override
    public void close() {
        RegistryUtils.unregisterNSystem();
        System.exit(1);
    }

    @Override
    public void update(String login) {
        IJournalManager manager = RegistryUtils.getJournalManagerInstance(login);
        if (manager != null) {
            IModel model = new Model(manager);
            ITaskController tc = new TaskController(model,new NotificationSystem());
            tc.start();
        }
    }
}
