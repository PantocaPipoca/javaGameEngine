package Game.Observer;

/**
 * Observer interface for game events.
 * This interface defines methods to be implemented by classes that want to listen for game events.
 * Provides callbacks for player health, ammo, and score changes.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public interface GameListener {
    /**
     * Called when the player's health changes.
     * @param health the new health value
     */
    void onPlayerHealthChanged(int health);

    /**
     * Called when the player's ammo changes.
     * @param currentAmmo the current ammo count
     * @param reserveAmmo the reserve ammo count
     */
    void onAmmoChanged(int currentAmmo, int reserveAmmo);

    /**
     * Called when the player's score changes.
     * @param score the new score value
     */
    void onScoreChanged(float score);
}