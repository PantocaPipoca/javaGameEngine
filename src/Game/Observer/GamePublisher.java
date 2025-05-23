package Game.Observer;

/**
 * Publisher interface for the Observer pattern in the game.
 * This interface defines methods to be implemented by classes that want to listen for game events.
 * 
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public interface GamePublisher {
    /**
     * Subscribes a listener to receive game event notifications.
     * @param listener the GameListener to subscribe
     */
    void subscribe(GameListener listener);

    /**
     * Unsubscribes a listener from receiving game event notifications.
     * @param listener the GameListener to unsubscribe
     */
    void unsubscribe(GameListener listener);
}