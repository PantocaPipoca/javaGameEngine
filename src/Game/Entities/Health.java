package Game.Entities;

/**
 * Manages the health of an entity, including damage, healing, and alive status.
 * @author Daniel Pantyukhov
 * @version 1.0
 */
public class Health {
    
    private int maxHealth;
    private int currentHealth;
    private boolean isAlive;

    /**
     * Constructs a Health manager with the specified maximum health.
     * @param maxHealth the maximum health value (must be > 0)
     */
    public Health(int maxHealth) {
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("Max health must be greater than 0.");
        }
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.isAlive = true;
    }

    /**
     * Applies damage to the entity.
     * @param damage the amount of damage (must be >= 0)
     */
    public void takeDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage must be non-negative.");
        }
        if (isAlive) {
            currentHealth -= damage;
            if (currentHealth <= 0) {
                currentHealth = 0;
                isAlive = false;
            }
        }
    }

    /**
     * Heals the entity by the specified amount.
     * @param amount the amount to heal (must be >= 0)
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
        }
    }

    /////////////////////////////////////////////////// Getters ///////////////////////////////////////////////////

    public int getMaxHealth() {
        return maxHealth;
    }
    public int getCurrentHealth() {
        return currentHealth;
    }
    public boolean isAlive() {
        return isAlive;
    }
}