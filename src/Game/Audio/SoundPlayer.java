package Game.Audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Utility class for playing sound effects and background music in the game.
 * Supports playing, looping, and stopping audio clips for effects and music.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class SoundPlayer {

    private static Clip backgroundClip;
    private static Clip stateClip;

    /**
     * Plays a one-shot sound effect from the specified file.
     * @param filePath the path to the audio file
     */
    public static void playSound(String filePath) {
        try {
            File file = new File(filePath);
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f); // volume ajustável (0 = máx, -80 = silêncio)

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Erro ao tocar som: " + filePath + " → " + e.getMessage());
        }
    }

    /**
     * Plays background music from the specified file, looping continuously.
     * Stops any currently playing background music.
     * @param filePath the path to the music file
     */
    public static void playBackgroundMusic(String filePath) {
        try {
            if (backgroundClip != null && backgroundClip.isRunning()) {
                backgroundClip.stop();
                backgroundClip.close();
            }

            File file = new File(filePath);
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audio);

            FloatControl gainControl = (FloatControl) backgroundClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-45.0f); // música mais baixa

            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundClip.start();
        } catch (Exception e) {
            System.err.println("Erro na música de fundo: " + e.getMessage());
        }
    }

    /**
     * Stops and closes the currently playing background music.
     */
    public static void stopBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.stop();
            backgroundClip.close();
        }
    }

    /**
     * Plays a looping state sound (e.g., for game states) from the specified file.
     * Stops any currently playing state sound.
     * @param filePath the path to the audio file
     */
    public static void playStateSound(String filePath) {
        try {
            if (stateClip != null && stateClip.isRunning()) {
                stateClip.stop();
                stateClip.close();
            }

            File file = new File(filePath);
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            stateClip = AudioSystem.getClip();
            stateClip.open(audio);

            FloatControl gainControl = (FloatControl) stateClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f); // volume do estado

            stateClip.loop(Clip.LOOP_CONTINUOUSLY);
            stateClip.start();
        } catch (Exception e) {
            System.err.println("Erro no som de estado: " + e.getMessage());
        }
    }

    /**
     * Stops and closes the currently playing state sound.
     */
    public static void stopStateSound() {
        if (stateClip != null) {
            stateClip.stop();
            stateClip.close();
        }
    }
}