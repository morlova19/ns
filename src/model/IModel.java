package model;

import journal.Task;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The Model provides methods for working with data, such as
 * adding, deleting and changing data.
 */
public interface IModel {
    /**
     * Gets list of current tasks.
     * @return list of current tasks.
     */
    CopyOnWriteArrayList<Task> getCurrentTasks();
    /**
     * Gets the task by identifier.
     * Returns null if the task was not found.
     * @param id identifier of the task.
     * @return task that was found.
     */
    Task get(int id);
    /**
     * Invokes for delaying the task.
     * @param id identifier of the task that will be delayed.
     * @param newDate new date of execution of the task.
     */
    void delay(int id, Date newDate);
    /**
     * Invokes to complete the task.
     */
    void complete(int id);

}
