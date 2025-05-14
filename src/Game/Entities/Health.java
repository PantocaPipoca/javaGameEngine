package Game.Entities;

public class Health {
    
    private int maxHealth;
    private int currentHealth;
    private boolean isAlive;

    public Health(int maxHealth) {
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("Max health must be greater than 0.");
        }
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.isAlive = true;
    }

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
