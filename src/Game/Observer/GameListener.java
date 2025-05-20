package Game.Observer;

public interface GameListener {
    void onPlayerHealthChanged(int health);
    void onAmmoChanged(int currentAmmo, int reserveAmmo);
    void onScoreChanged(float score);
}