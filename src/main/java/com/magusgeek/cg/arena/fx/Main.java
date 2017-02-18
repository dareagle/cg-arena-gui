package com.magusgeek.cg.arena.fx;

import com.magusgeek.cg.arena.Arena;
import com.magusgeek.cg.arena.GameResult;
import com.magusgeek.cg.arena.PlayerStats;
import com.magusgeek.cg.arena.fx.view.ConfigurationLayoutController;
import com.magusgeek.cg.arena.fx.view.MainLayoutController;
import com.magusgeek.cg.arena.fx.view.PlayerLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    private static final Log LOG = LogFactory.getLog(Main.class);

    /**
     * fxml shit
     */
    private Stage primaryStage;
    private BorderPane mainLayout;
    private Pane playerLayout;
    private Pane configurationLayout;

    /**
     * synchronised shit
     */
    private ObservableList<PlayerStats> stats = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
    private ObservableList<GameResult> results = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("cg-arena gui");

        initRootLayout();
        initLayouts();
        showConfigurationLayout();
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MainLayout.fxml"));
            mainLayout = loader.load();
            MainLayoutController mainLayoutController = loader.getController();
            mainLayoutController.setMainApp(this);

            // Show the scene containing the root layout.
            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initLayouts() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/PlayerLayout.fxml"));
            playerLayout = loader.load();
            PlayerLayoutController playerLayoutController = loader.getController();
            playerLayoutController.setMainApp(this);

            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ConfigurationLayout.fxml"));
            configurationLayout = loader.load();
            ConfigurationLayoutController configurationLayoutController = loader.getController();
            configurationLayoutController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchArena(int gamesNumber, int threadsNumber, String engineClassName, List<String> commandLines) {
        stats.clear();
        for (int i = 0; i < commandLines.size(); i++) {
            stats.add(new PlayerStats());
        }
        results.clear();
        Arena.launch(gamesNumber, threadsNumber, engineClassName, commandLines, stats, results);
    }

    public void stopArena() {
        Arena.stop();
    }

    public void resetResults() {
        stats.clear();
        results.clear();
    }

    public void showConfigurationLayout() {
        mainLayout.setLeft(configurationLayout);
    }

    public void addListElement() {
        GameResult result = new GameResult();
        result.getPositions().add((int) Math.random() * 4);

        results.add(result);
    }

    public void showPlayerLayout() {
        mainLayout.setLeft(playerLayout);
    }

    public static void main(String[] args) {
        launch(args);
    }

    //    }
    public ObservableList<GameResult> getResults() {
        return results;
    }
}
