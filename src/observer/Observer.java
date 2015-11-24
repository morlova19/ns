package observer;
/**
 * Interface of observer.
 * Notifications come to observer from observable object
 * when some changes were done with the data.
 */
public interface Observer {
    /**
     * Invoked when changes occurred and
     * need to update the data on the form.
     */
    void update(String login);
}
