package sample;

/**
 * An interface containing methods that updates information about observers.
 */
public interface Observable {
    /**
     * Add new observer for a thread.
     * @param o An object of Observer class which we want to create/add to list.
     */
    void addObserver(Observer o);

    /**
     * Remove an observer.
     * @param o An object of Observer class which we want to remove from list.
     */
    void removeObserver(Observer o);

    /**
     * Update list of observers.
     */
    void updateObserver();

}