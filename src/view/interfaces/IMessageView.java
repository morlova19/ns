package view.interfaces;

import journal.Task;

/**
 * Interface of message view.
 */
public interface IMessageView extends IView{
    /**
     * Sets identifier of task.
     * @param id identifier.
     */
    void setId(int id);

    /**
     * Displays task's name.
     * @param name name.
     */
    void displayTaskName(String name);

    /**
     * Displays task's description.
     * @param desc description.
     */
    void displayTaskDesc(String desc);

    /**
     * Displays task's date.
     * @param date date.
     */
    void displayTaskDate(String date);

    /**
     * Displays task's contacts.
     * @param contacts contacts.
     */
    void displayTaskContacts(String contacts);
}
