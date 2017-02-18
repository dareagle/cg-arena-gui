package com.magusgeek.cg.arena.fx.view;

import com.magusgeek.cg.arena.fx.TextAreaAppender;
import com.sun.xml.internal.ws.util.StringUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private void initialize() {
        TextAreaAppender.setTextArea(consoleTextArea);

        Properties engines = new Properties();
        try {
            engines.load(getClass().getClassLoader().getResourceAsStream("com/magusgeek/cg/arena/engines.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry<Object, Object> entry : engines.entrySet()) {
            engineChoiceBox.getItems().add(entry.getValue() + "");
        }
    }

    @FXML
    private void startAction() {
        consoleTextArea.clear();
        List<String> commandList = new ArrayList<>();
        // TODO ajouter checks en fonction du nombre minimum de joueurs nécessaires
        if (!p1CommandLineInput.getText().isEmpty()) commandList.add(p1CommandLineInput.getText());
        if (!p2CommandLineInput.getText().isEmpty()) commandList.add(p2CommandLineInput.getText());
        if (!p3CommandLineInput.getText().isEmpty()) commandList.add(p3CommandLineInput.getText());
        main.launchArena(Integer.valueOf(nbGamesInput.getText()), Integer.valueOf(nbThreadsInput.getText()), engineChoiceBox.getValue() + "", commandList);
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

    @FXML
    private void logStuff() {
        main.addListElement();
        LOG.error("This is a very long shit sentence yo very long shit sentence yo very long shit sentence yo very long shit sentence yo very long shit sentence yo very long shit sentence yo very long shit sentence yo very long shit sentence yo very long shit sentence yo");
    }

}