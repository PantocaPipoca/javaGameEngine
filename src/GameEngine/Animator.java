package GameEngine;

import java.util.*;

/**
 * Class that manages sprite animations for game objects.
 * Handles switching, updating, and retrieving animation frames.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv Animator must have valid animation frame lists and frame duration > 0.
 */
public class Animator {

    private final Map<String, List<Shape>> animations = new HashMap<>();
    private String currentAnimation;
    private int currentFrame;
    private float frameDuration;
    private float timeSinceLastFrame;
    private Shape currentShape;
    private boolean isStopped = false;

    /////////////////////////////////////////////////// Constructors ///////////////////////////////////////////////////

    /**
     * Constructs an Animator with the specified frame duration.
     * @param frameDuration duration of each frame in seconds
     * @throws IllegalArgumentException if frameDuration is not positive
     */
    public Animator(float frameDuration) {
        if (frameDuration <= 0) {
            throw new IllegalArgumentException("Frame duration must be positive.");
        }
        this.frameDuration = frameDuration;
    }

    /////////////////////////////////////////////////// Logic ///////////////////////////////////////////////////

    /**
     * Adds an animation with the given name and list of frames.
     * @param name animation name
     * @param frames list of Shape frames
     * @throws IllegalArgumentException if frames is null or empty
     */
    public void addAnimation(String name, List<Shape> frames) {
        if (frames == null || frames.isEmpty()) {
            throw new IllegalArgumentException("Animation frames must not be null or empty.");
        }
        animations.put(name, frames);
    }

    /**
     * Plays the animation with the given name.
     * If the animation is already playing, does nothing.
     * @param name animation name
     * @throws IllegalArgumentException if the animation does not exist
     */
    public void play(String name) {
        if (!animations.containsKey(name)) {
            throw new IllegalArgumentException("Animation '" + name + "' does not exist.");
        }
        isStopped = false;
        if (!name.equals(currentAnimation)) {
            currentAnimation = name;
            currentFrame = 0;
            timeSinceLastFrame = 0;
            updateCurrentShape();
        }
    }

    /**
     * Updates the current animation frame based on elapsed time.
     * @param deltaTime time since last update (in seconds)
     */
    public void update(float deltaTime) {
        timeSinceLastFrame += deltaTime;
        if (animations.containsKey(currentAnimation)) {
            List<Shape> frames = animations.get(currentAnimation);
            if (timeSinceLastFrame >= frameDuration && frames != null && !frames.isEmpty()) {
                timeSinceLastFrame -= frameDuration;
                if (!isStopped) currentFrame = (currentFrame + 1) % frames.size();
                updateCurrentShape();
            }
        }
    }

    /**
     * Updates the current shape to match the current frame.
     */
    private void updateCurrentShape() {
        List<Shape> frames = animations.get(currentAnimation);
        if (frames != null && !frames.isEmpty()) {
            currentShape = frames.get(currentFrame);
        }
    }

    /////////////////////////////////////////////////// Getters ///////////////////////////////////////////////////

    /**
     * Gets the current Shape frame.
     * @return the current Shape
     */
    public Shape currentShape() {
        return currentShape;
    }

    /**
     * Gets the current animation name.
     * @return the current animation name
     */
    public String currentAnimation() {
        return currentAnimation;
    }

    /**
     * Gets the current frame index.
     * @return the current frame index
     */
    public int currentFrame() {
        return currentFrame;
    }

    /**
     * Gets the frame duration.
     * @return the frame duration
     */
    public float frameDuration() {
        return frameDuration;
    }

    /**
     * Checks if the animation is stopped.
     * @return true if stopped, false otherwise
     */
    public boolean isStopped() {
        return isStopped;
    }

    /**
     * Gets the list of frames for a given animation.
     * @param name animation name
     * @return list of Shape frames, or null if not found
     */
    public List<Shape> getAnimationFrames(String name) {
        return animations.get(name);
    }

    /////////////////////////////////////////////////// Setters ///////////////////////////////////////////////////

    /**
     * Sets the frame duration.
     * @param duration the frame duration to set
     */
    public void frameDuration(float duration) {
        if (duration <= 0) {
            throw new IllegalArgumentException("Frame duration must be positive.");
        }
        this.frameDuration = duration;
    }

    /**
     * Stops the current animation (freezes on current frame).
     */
    public void stopAnimation() {
        isStopped = true;
    }
}