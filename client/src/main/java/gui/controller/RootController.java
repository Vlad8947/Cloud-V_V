package gui.controller;

import gui.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Класс-контроллер, осуществляющий функционал RootBorderPane.fxml .
 * RootBorderPane.fxml - корневое окно приложения, в котором можно переключаться между модульными окнами,
 * и в которое помещаются модульные окна.
 */

public class RootController implements Initializable {

    @FXML
    private Toggle toggleFilesOnCloud, toggleAddFilesToCloud;
    private ToggleGroup viewsGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewsGroup = new ToggleGroup();
        toggleAddFilesToCloud.setToggleGroup(viewsGroup);
        toggleFilesOnCloud.setToggleGroup(viewsGroup);
    }

    @FXML
    private void addFilesOnRoot () {
        MainApp.setAddFilesViewOnRoot();
    }

    @FXML
    private void listViewOnRoot () {
        MainApp.setListViewOnRoot();
    }
}
