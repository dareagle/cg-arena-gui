package com.magusgeek.cg.arena.fx.view;

import com.magusgeek.cg.arena.fx.TextAreaAppender;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class ConfigurationLayoutController extends AbstractLayoutController {

    private static final Log LOG = LogFactory.getLog(ConfigurationLayoutController.class);

    @FXML
    private TextArea consoleTextArea;

    @FXML
    private ChoiceBox engineChoiceBox;

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
    private void logStuff() {
        LOG.error("This is a very long shit sentence yo very long shit sentence yo very long shit sentence yo very long shit sentence yo very long shit sentence yo very long shit sentence yo very long shit sentence yo very long shit sentence yo very long shit sentence yo");
    }

}