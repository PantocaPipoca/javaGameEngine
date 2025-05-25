package Game.UI;

import Game.Camera;
import Game.Game;
import GameEngine.*;
import Figures.Point;
import java.util.List;

public class UIBehaviour implements IBehaviour {
    private IGameObject owner;
    private double timer = 0;

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        if (owner == null) return;

        String name = owner.name();

        // HUD UI
        if (name.equals("ui_health") || name.equals("ui_score") || name.equals("ui_ammo")) {
            Point cam = Camera.getInstance(null).position();
            if (name.equals("ui_health"))
                owner.transform().position(new Point(cam.x() - 830, cam.y() - 490));
            if (name.equals("ui_score"))
                owner.transform().position(new Point(cam.x() + 730, cam.y() - 490));
            if (name.equals("ui_ammo"))
                owner.transform().position(new Point(cam.x() + 730, cam.y() + 400));
        }

        // GameOver UI
        if (name.equals("ui_gameover_sprite") || name.equals("ui_blackout") ||
            name.equals("ui_yes_button") || name.equals("ui_no_button")) {
            Point cam = Camera.getInstance(null).position();
            if (name.equals("ui_gameover_sprite") || name.equals("ui_blackout")) {
                owner.transform().position(cam);
            }
            if (name.equals("ui_yes_button")) {
                owner.transform().position(new Point(cam.x() - 160, cam.y() + 140));
            }
            if (name.equals("ui_no_button")) {
                owner.transform().position(new Point(cam.x() + 20, cam.y() + 145));
            }
        }

        // Victory UI
        if (name.equals("ui_victory")) {
            timer += dT;
            if (timer >= 10.0) {
                GameEngine.getInstance().destroy(owner);
                // Optionally: return to main menu, etc.
            }
            owner.transform().position(Camera.getInstance(null).position());
        }
    }

    public void onUIClick(int x, int y) {
        String name = owner.name();
        Point cam = Camera.getInstance(null).position();
        if (name.equals("ui_yes_button")) {
            int bx = (int) (cam.x() - 160);
            int by = (int) (cam.y() + 140);
            if (x >= bx && x <= bx + 120 && y >= by && y <= by + 50) {
                GameOverUI.getInstance().reset();
                Game.getInstance().previousScore(0);
                Game.getInstance().restart();
            }
        }
        if (name.equals("ui_no_button")) {
            int bx = (int) (cam.x() + 20);
            int by = (int) (cam.y() + 145);
            if (x >= bx && x <= bx + 120 && y >= by && y <= by + 50) {
                System.exit(0);
            }
        }
    }

    @Override public void onInit() {}
    @Override public void onEnabled() {}
    @Override public void onDisabled() {}
    @Override public void onDestroy() {}
    @Override public void onCollision(List<IGameObject> gol) {}
    @Override public IGameObject gameObject() { return owner; }
    @Override public void gameObject(IGameObject go) { this.owner = go; }
}