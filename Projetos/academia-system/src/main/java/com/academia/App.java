package com.academia;

import com.academia.database.CriarTabelas;
import com.academia.view.MainView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        CriarTabelas.criar();

        MainView mainView
                = new MainView();

        Scene scene
                = new Scene(
                        mainView.getRoot(),
                        1200,
                        700
                );

        scene.getStylesheets().add(
                getClass()
                        .getResource("/css/style.css")
                        .toExternalForm()
        );

        stage.setTitle(
                "Sistema Academia"
        );

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
