package com.magusgeek.cg.arena.fx;

import com.magusgeek.cg.arena.fx.view.PlayerLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;

    private BorderPane mainLayout;
    private Pane playerLayout;
    private Pane configurationLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("cg-arena gui");

        initRootLayout();
        initLayouts();
        showPlayerLayout();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MainLayout.fxml"));
            mainLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initLayouts() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/PlayerLayout.fxml"));
            playerLayout = loader.load();
            PlayerLayoutController playerLayoutController = loader.getController();
            playerLayoutController.setMain(this);

            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ConfigurationLayout.fxml"));
            configurationLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showConfigurationLayout() {
        mainLayout.setLeft(configurationLayout);
    }

    public void showPlayerLayout() {
        mainLayout.setLeft(playerLayout);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
