package controllers.interfaces;

import journal.IJournalManager;
import model.IModel;

import java.util.Date;

/**
 * Interface of controllers.
 * TaskController provides methods to work with data for view.
 * It invokes methods of the Model and transfers the entered data to it.
 */
public interface ITaskController extends IController {
    /**
     * Invokes to display the list of the tasks.
     */
    void load();
    /**
     * Invokes to delay the task.
     * @param id identifier of the task that will be delayed.
     */
    void delay(int id, Date newDate);
    /**
     * Invokes to complete the task.
     * @param id identifier of the task that will be completed.
     */
    void complete(int id);

    /**
     * Update model.
     * @param manager new journal manager.
     */
    void updateModel(IJournalManager manager);

}
