package Game.Entities.Commons;

import java.util.ArrayList;
import java.util.List;

import Game.Observer.GameListener;
import Game.Observer.GamePublisher;

/**
 * Class that manages the health of an entity, including damage, healing, and alive status.
 * Provides methods for taking damage, healing, and notifying listeners of health changes.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv Health must always be positive and cannot exceed maxHealth.
 */
public class Health implements GamePublisher {

    private final List<GameListener> listeners = new ArrayList<>();
    private int maxHealth;
    private int currentHealth;
    private boolean isAlive;
    private boolean immune = false;

    /////////////////////////////////////////////////// Constructor ///////////////////////////////////////////////////

    /**
     * Constructs a Health manager with the specified maximum health.
     * @param maxHealth the maximum health value (must be > 0)
     * @throws IllegalArgumentException if maxHealth is not positive
     */
    public Health(int maxHealth) {
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("Max health must be greater than 0.");
        }
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.isAlive = true;
        publishHealthChanged();
    }

    /////////////////////////////////////////////////// Health Methods ///////////////////////////////////////////////////

    /**
     * Applies damage to the entity.
     * @param damage the amount of damage (must be >= 0)
     * @throws IllegalArgumentException if damage is negative
     */
    public void takeDamage(int damage) {
        if (immune) return;
        if (damage < 0) {
            throw new IllegalArgumentException("Damage must be non-negative.");
        }
        if (isAlive) {
            currentHealth -= damage;
            if (currentHealth <= 0) {
                currentHealth = 0;
                isAlive = false;
            }
            publishHealthChanged();
        }
    }

    /**
     * Heals the entity by the specified amount.
     * @param amount the amount to heal (must be >= 0)
     * @throws IllegalArgumentException if amount is negative
     */
    public void heal(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Heal amount must be non-negative.");
        }
        if (isAlive) {
            currentHealth += amount;
            if (currentHealth > maxHealth) {
                currentHealth = maxHealth;
            }
            publishHealthChanged();
        }
    }

    /////////////////////////////////////////////////// Getters and Setters ///////////////////////////////////////////////////

    /**
     * Gets the maximum health value.
     * @return the maximum health
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Gets the current health value.
     * @return the current health
     */
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Checks if the entity is alive.
     * @return true if alive, false otherwise
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the immune state of the entity.
     * @param immune true to make the entity immune to damage, false otherwise
     */
    public void setImmune(boolean immune) {
        this.immune = immune;
    }

    /**
     * Checks if the entity is immune to damage.
     * @return true if immune, false otherwise
     */
    public boolean isImmune() {
        return immune;
    }

    /////////////////////////////////////////////////// Observer Methods ///////////////////////////////////////////////////

    /**
     * Subscribes a listener to health changes.
     * @param listener the listener to subscribe
     */
    @Override
    public void subscribe(GameListener listener) {
        listeners.add(listener);
    }

    /**
     * Unsubscribes a listener from health changes.
     * @param listener the listener to unsubscribe
     */
    @Override
    public void unsubscribe(GameListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all listeners of a health change.
     */
    private void publishHealthChanged() {
        int health = getCurrentHealth();
        for (GameListener l : listeners) l.onPlayerHealthChanged(health);
    }
}