package GameEngine;

import java.util.*;

public class Animator {
    private final Map<String, List<Shape>> animations = new HashMap<>();
    private String currentAnimation;
    private int currentFrame;
    private final float frameDuration;
    private float timeSinceLastFrame;
    private Shape currentShape;

    private boolean isStopped = false;

    public Animator(float frameDuration) {
        this.frameDuration = frameDuration;
    }

    public void addAnimation(String name, List<Shape> frames) {
        animations.put(name, frames);
    }

    public void play(String name) {
        isStopped = false;
        if (!name.equals(currentAnimation)) {
            currentAnimation = name;
            currentFrame = 0;
            timeSinceLastFrame = 0;
            updateCurrentShape();
        }
    }

    public void update(float deltaTime) {
        timeSinceLastFrame += deltaTime;
        if (animations.containsKey(currentAnimation)) {
            List<Shape> frames = animations.get(currentAnimation);
            if (timeSinceLastFrame >= frameDuration && frames != null && !frames.isEmpty()) {
                timeSinceLastFrame -= frameDuration;
                if(!isStopped) currentFrame = (currentFrame + 1) % frames.size();
                updateCurrentShape();
            }
        }
    }

    private void updateCurrentShape() {
        List<Shape> frames = animations.get(currentAnimation);
        if (frames != null && !frames.isEmpty()) {
            currentShape = frames.get(currentFrame);
        }
    }

    public Shape getCurrentShape() {
        return currentShape;
    }

    public void stopAnimation() {
        isStopped = true;
    }
}

