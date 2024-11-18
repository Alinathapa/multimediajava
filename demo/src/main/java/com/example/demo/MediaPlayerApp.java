package com.example.demo;


import javafx.application.Application;
import javafx.stage.Stage;

public class MediaPlayerApp extends Application {
    private Controller controller;
    private MediaManager mediaManager;

    @Override
    public void start(Stage primaryStage) {
        controller = new Controller(primaryStage);
        mediaManager = new MediaManager(primaryStage, controller);

        controller.initializeUI(mediaManager);

        primaryStage.setScene(controller.getStartScene());
        primaryStage.setTitle("Media Player");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
