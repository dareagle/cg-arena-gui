package com.magusgeek.cg.arena.fx.view;

import com.magusgeek.cg.arena.fx.Main;
import javafx.fxml.FXML;

public class PlayerLayoutController {

    private Main main;

    @FXML
    private void goConfiguration() {
        main.showConfigurationLayout();
    }

    public void setMain(Main main) {
        this.main = main;
    }

}
