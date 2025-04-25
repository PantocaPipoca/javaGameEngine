package Game;

public class Health {
    
    private int maxHealth;
    private int currentHealth;
    private boolean isAlive;

    public Health(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.isAlive = true;
    }

    public void takeDamage(int damage) {
        if (isAlive) {
            currentHealth -= damage;
            if (currentHealth <= 0) {
                currentHealth = 0;
                isAlive = false;
            }
        }
    }

    public void heal(int amount) {
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
