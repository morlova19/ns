package controllers.classes;

import controllers.interfaces.ITaskController;
import journal.IJournalManager;
import model.Model;
import utils.Constants;
import utils.DateUtil;
import utils.RegistryUtils;
import view.interfaces.IMessageView;
import view.forms.MessageDialog;
import journal.Task;
import model.IModel;
import ns.INotificationSystem;
import observer.TaskObserver;

import javax.swing.*;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

public class TaskController implements ITaskController, TaskObserver {
    /**
     * Model that works with data.
     */
    private IModel model;
    /**
     * Notification system.
     */
    private INotificationSystem nSystem;
    /**
     * Instance of this controller.
     */
    private static ITaskController controller;

    /**
     * View to display notification.
     */
    private IMessageView view;
    /**
     * Constructs new controllers.
     * Creates and displays GUI.
     * @param model model.
     */
    public TaskController(IModel model, INotificationSystem nSystem)  {
        if(model != null)
        {
            this.model = model;
        }
        controller = this;
        if(nSystem != null) {
            this.nSystem = nSystem;
        }
    }

    private void createView() {
        view = new MessageDialog(this);
        view.createView();
        if(SystemTray.isSupported())
        {
            SystemTray tray = SystemTray.getSystemTray();
            Image img = utils.Icon.getIcon();
            PopupMenu popupMenu = new PopupMenu();
            MenuItem exitItem = new MenuItem(Constants.EXIT);
            popupMenu.add(exitItem);
            exitItem.addActionListener(e -> {
                try {
                    RegistryUtils.unregisterNSystem();
                } catch (RemoteException | NotBoundException e1) {
                    JOptionPane.showMessageDialog(new JFrame(), "Server are not available");
                }
                finally {
                    System.exit(1);
                }
            });

            TrayIcon trayIcon = new TrayIcon(img, Constants.APP_TITLE,popupMenu);
            trayIcon.setImageAutoSize(true);
            try {
                tray.add(trayIcon);
                trayIcon.displayMessage( Constants.APP_TITLE, Constants.TRAY_MESSAGE, TrayIcon.MessageType.INFO);
            } catch (AWTException e) {

            }
        }
    }
    @Override
    public void load() {
        CopyOnWriteArrayList<Task> currentTasksID = model.getCurrentTasks();
        for(Task i: currentTasksID) {
            this.nSystem.restartTask(i.getID(), i.getDate());
        }
    }

    @Override
    public void delay(int id, Date newDate) {
        boolean isCorrectDate = DateUtil.isCorrect(newDate);
        if(!isCorrectDate)
        {
            view.showError(Constants.INCORRECT_DATE, Constants.DATE);
        }
        else
        {
            view.resetError(Constants.DATE);
            model.delay(id, newDate);
            Task t = model.get(id);
            nSystem.delayTask(t.getID(), t.getDate());
            view.close();
        }


    }
    @Override
    public void complete(int id) {
        model.complete(id);
        nSystem.cancelTask(id);
        view.close();
    }

    @Override
    public void updateModel(IJournalManager manager) {
        this.model = new Model(manager);
        load();
    }

    @Override
    public void update(int id) {
        Task t = model.get(id);
        //System.out.println(t.getDate());
        if(t != null) {
            view.displayTaskName(t.getName());
            view.displayTaskDesc(t.getDescription());
            view.displayTaskDate(DateUtil.format(t.getDate()));
            view.displayTaskContacts(t.getContacts());
            view.setId(id);
            view.open();
        }
    }

    /**
     * Gets instance og this controllers.
     * @return controllers instance.
     */
    public static ITaskController getInstance() {
        return controller;
    }

    @Override
    public void start() {
        createView();
        this.nSystem.registerObserver(this);
        CopyOnWriteArrayList<Task> currentTasksID = model.getCurrentTasks();
        for(Task i: currentTasksID) {
            this.nSystem.startTask(i.getID(),i.getDate());
        }
    }

    @Override
    public void stop() {
        try {
            RegistryUtils.unregisterNSystem();
        } catch (RemoteException | NotBoundException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Server are not available");
        }
        finally {
            System.exit(1);
        }
    }
}
