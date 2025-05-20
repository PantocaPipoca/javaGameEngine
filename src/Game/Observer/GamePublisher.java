package Game.Observer;

public interface GamePublisher {
    void subscribe(GameListener listener);
    void unsubscribe(GameListener listener);
}