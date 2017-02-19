package svivien.cg.arena.fx.view;

import svivien.cg.arena.PlayerStats;
import svivien.cg.arena.fx.Main;
import svivien.cg.arena.fx.TextAreaAppender;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ConfigurationLayoutController extends AbstractLayoutController {

    private static final Log LOG = LogFactory.getLog(ConfigurationLayoutController.class);

    @FXML
    private TextArea consoleTextArea;

    @FXML
    private ChoiceBox engineChoiceBox;

    @FXML
    private TextField nbGamesInput;

    @FXML
    private TextField nbThreadsInput;

    @FXML
    private TextField p1CommandLineInput;

    @FXML
    private TextField p2CommandLineInput;

    @FXML
    private TextField p3CommandLineInput;

    @FXML
    private TextFlow resultsTextFlow;

    private ChangeListener<Number> statTotalListener = (observable, oldValue, newValue) -> refreshStatsTextflow();

    @FXML
    private void initialize() {
        TextAreaAppender.setTextArea(consoleTextArea);

        Properties engines = new Properties();
        try {
            engines.load(getClass().getClassLoader().getResourceAsStream("svivien/cg/arena/engines.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry<Object, Object> entry : engines.entrySet()) {
            engineChoiceBox.getItems().add(entry.getValue() + "");
        }
    }

    @Override
    public void setMainApp(Main main) {
        super.setMainApp(main);
        main.getStats().addListener((ListChangeListener<PlayerStats>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    registerStatsListener(c.getAddedSubList());
                }
                if (c.wasRemoved()) {
                    resultsTextFlow.getChildren().clear();
                    unregisterStatsListener(c.getRemoved());
                }
            }
        });
    }

    @FXML
    private void startAction() {
        consoleTextArea.clear();
        List<String> commandList = new ArrayList<>();
        // TODO ajouter checks en fonction du nombre minimum de joueurs nécessaires
        if (!p1CommandLineInput.getText().isEmpty()) commandList.add(p1CommandLineInput.getText());
        if (!p2CommandLineInput.getText().isEmpty()) commandList.add(p2CommandLineInput.getText());
        if (!p3CommandLineInput.getText().isEmpty()) commandList.add(p3CommandLineInput.getText());
        try {
            main.launchArena(Integer.valueOf(nbGamesInput.getText()), Integer.valueOf(nbThreadsInput.getText()), engineChoiceBox.getValue() + "", commandList);
        } catch (NumberFormatException | NullPointerException e) {
            LOG.error("Error while launching Arena, wrong parameters", e);
        }
    }

    private void unregisterStatsListener(List<? extends PlayerStats> removed) {
        for (PlayerStats stat : removed) {
            stat.getTotal().removeListener(statTotalListener);
        }
    }

    private void registerStatsListener(List<? extends PlayerStats> added) {
        for (PlayerStats stat : added) {
            stat.getTotal().addListener(statTotalListener);
        }
    }

    private void refreshStatsTextflow() {
        synchronized (main.getStats()) {
            Platform.runLater(() -> {
                resultsTextFlow.getChildren().clear();

                Text blank = new Text("\n");
                blank.setFont(Font.font("Helvetica", FontPosture.REGULAR, 15));
                resultsTextFlow.getChildren().add(blank);

                for (int i = 0; i < main.getStats().size(); i++) {
                    Text playerText = new Text(main.getStats().get(i).toTextflowString(i, main.getStats().size()) + "\n");
                    playerText.setFill(Color.BLACK);
                    playerText.setFont(Font.font("Helvetica", FontPosture.REGULAR, 13));
                    resultsTextFlow.getChildren().add(playerText);
                }
            });
        }
    }

    @FXML
    private void stopAction() {
        main.stopArena();
    }

    @FXML
    private void resetAction() {
        consoleTextArea.clear();
        main.resetResults();
    }

}