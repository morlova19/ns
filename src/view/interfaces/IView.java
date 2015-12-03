package view.interfaces;

/**
 * Basic interface of view
 */
public interface IView  {
    /**
     * Opens view.
     */
    void open();
    /**
     * Closes view.
     */
    void close();
    /**
     * Shows error message in the specified component.
     * @param error error message.
     * @param component constant that defines in which component error will be displayed.
     */
    void showError(String error, int component);
    /**
     * Sets default view of the specified component.
     * @param component constant that defines which component.
     */
    void resetError(int component);
    /**
     * Creates view.
     */
    void createView();

    /**
     * Clears view.
     */
    void clearView();
}
