package com.magusgeek.cg.arena.fx;

import com.magusgeek.cg.arena.fx.view.ConfigurationLayoutController;
import com.magusgeek.cg.arena.fx.view.PlayerLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class Main extends Application {

    private static final Log LOG = LogFactory.getLog(Main.class);

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
        showConfigurationLayout();

        LOG.debug("I'm debugging some shit right now");
        LOG.error("And now I'm writing an error log yo");
        LOG.info("Meh, just info log, don't worry bro ...");
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
            ConfigurationLayoutController configurationLayoutController = loader.getController();
            configurationLayoutController.setMain(this);
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
