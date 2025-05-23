package Game.UI;

import Game.Observer.GameListener;

public class GameUI implements GameListener {

    private static GameUI instance;
    private GameUI() {}
    @Override
    public void onPlayerHealthChanged(int health) {
        System.out.println("UI: Player health = " + health);
    }
    @Override
    public void onAmmoChanged(int currentAmmo, int reserveAmmo) {
        //System.out.println("UI: Ammo = " + currentAmmo + " / " + reserveAmmo);
    }
    @Override
    public void onScoreChanged(float score) {
        //System.out.println("UI: Score = " + score);
    }

    public static GameUI getInstance() {
        if (instance == null) {
            instance = new GameUI();
        }
        return instance;
    }
}