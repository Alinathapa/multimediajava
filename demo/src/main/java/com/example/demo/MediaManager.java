package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class MediaManager {
    private MediaPlayer mediaPlayer;
    private final Stage stage;
    private final Controller controller;

    public MediaManager(Stage stage, Controller controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public void loadMedia(File file, Scene mediaScene, Slider seekBar, Slider volumeSlider) {
        // Dispose of the previous media player if any
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        // Load new media
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        controller.getMediaView().setMediaPlayer(mediaPlayer);

        // Update stage and title
        stage.setTitle("Now Playing: " + file.getName());
        stage.setScene(mediaScene);

        // Bind media controls
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            seekBar.setValue(newTime.toSeconds());
        });

        seekBar.setOnMousePressed(event -> mediaPlayer.seek(Duration.seconds(seekBar.getValue())));
        seekBar.setOnMouseDragged(event -> mediaPlayer.seek(Duration.seconds(seekBar.getValue())));

        mediaPlayer.setOnReady(() -> {
            seekBar.setMax(mediaPlayer.getMedia().getDuration().toSeconds());
            mediaPlayer.play();
        });

        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> mediaPlayer.setVolume(newVal.doubleValue()));

        // Stop playback when media ends
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.stop());
    }

    public void play() {
        if (mediaPlayer != null) mediaPlayer.play();
    }

    public void pause() {
        if (mediaPlayer != null) mediaPlayer.pause();
    }

    public void stop() {
        if (mediaPlayer != null) mediaPlayer.stop();
    }
}