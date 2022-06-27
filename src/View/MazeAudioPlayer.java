//package View;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

//public class MazeAudioPlayer {


//    private static MazeAudioPlayer mediaPlayer = null;

//    private AudioClip backgroundAudio, startAudio, wallAudio, solutionAudio, propertiesAudio;
//    private MediaPlayer mediaPlayer;
//    protected MazeAudioPlayer() {
//        this.playBackground("background.mp3");
//    }
//
//    public static void getInstance(Media media) {
//        if (mediaPlayer == null) {
//            mediaPlayer = new MediaPlayer(media);
//        }
//    }

//    private void playBackground(String fileName)
//    {
//        String path = getClass().getResource(fileName).getPath();
//        Media myMedia = new Media(new File(path).toURI().toString());
//        mediaPlayer = new MediaPlayer(myMedia);
//        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//        mediaPlayer.play();
//    }
//    private void loadSound() {
//        String path;
//        try {
//            path = getClass().getResource("/Sounds/background.mp3").toString();
//            this.backgroundAudio = new AudioClip(path);
//        } catch (Exception e) {
//            System.out.println("Start sound not found");
//        }
//        try {
//            path = getClass().getResource("/Sounds/start.mp3").toString();
//            this.startAudio = new AudioClip(path);
//        } catch (Exception e) {
//            System.out.println("Start sound not found");
//        }
//        try {
//            path = getClass().getResource("/Sounds/solution.mp3").toString();
//            this.solutionAudio = new AudioClip(path);
//
//        } catch (Exception e) {
//            System.out.println("solution sound not found");
//        }
//        try {
//            path = getClass().getResource("/Sounds/properties.mp3").toString();
//            this.propertiesAudio = new AudioClip(path);
//
//        } catch (Exception e) {
//            System.out.println("properties sound not found");
//        }
//    }
//
//    public void play(MazeSound soundName) {
//        switch (soundName) {
//            case BackGround -> playBackground("resources/Sounds/start.mp3");
////            case Start -> playThis(this.startAudio);
////            case Solution -> playBack(this.solutionAudio);
////            case Properties -> playBack(this.propertiesAudio);
//        }
//    }
//
//    private void playThis(AudioClip audioClip) {
//        if (audioClip != null && !PlayerConfig.getInstance().isMute()) {
//            audioClip.setVolume(PlayerConfig.getInstance().getFxVolume());
//            audioClip.play();
//        }
//    }
//
//    private void playBack(AudioClip audioClip) {
//        this.stopAll();
//        if (audioClip != null && !PlayerConfig.getInstance().isMute()) {
//            audioClip.setVolume(PlayerConfig.getInstance().getBackVolume());
//            audioClip.play();
//        }
//    }
//
//    public void stopAll() {
//        if (backgroundAudio != null)
//            backgroundAudio.stop();
//        if (startAudio != null)
//            startAudio.stop();
//        if (wallAudio != null)
//            wallAudio.stop();
//        if (solutionAudio != null)
//            solutionAudio.stop();
//        if (propertiesAudio != null)
//            propertiesAudio.stop();
//    }
//
//    public enum MazeSound {
//        BackGround,
//        Start,
//        Solution,
//        Properties
//    }
//}