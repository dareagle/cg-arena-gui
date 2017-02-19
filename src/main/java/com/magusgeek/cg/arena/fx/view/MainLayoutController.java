package com.magusgeek.cg.arena.fx.view;

import com.magusgeek.cg.arena.GameResult;
import com.magusgeek.cg.arena.fx.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MainLayoutController extends AbstractLayoutController {

    private static final Log LOG = LogFactory.getLog(MainLayoutController.class);

    @FXML
    private ListView<GameResult> resultsListView;

    @Override
    public void setMainApp(Main main) {
        super.setMainApp(main);
        resultsListView.setItems(main.getResults());
        resultsListView.setCellFactory(param -> new GameResultListCell());
    }

    static class GameResultListCell extends ListCell<GameResult> {
        @Override
        protected void updateItem(GameResult item, boolean empty) {
            super.updateItem(item, empty);
            // Ensures that the GUI update happens on the main thread
            Platform.runLater(() -> {
                setText(null);
                if (!empty && item != null) {
                    setText("[" + item.getId() + "] Player " + (item.getPositions().get(0) + 1) + " wins");
                }
            });
        }
    }
}
