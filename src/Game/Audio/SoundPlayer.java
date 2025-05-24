package Game.Audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {

    private static Clip backgroundClip;
    private static Clip stateClip;

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

    public static void stopBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.stop();
            backgroundClip.close();
        }
    }

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

    public static void stopStateSound() {
        if (stateClip != null) {
            stateClip.stop();
            stateClip.close();
        }
    }
}