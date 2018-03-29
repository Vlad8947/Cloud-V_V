package gui.controller;

import client.handler.DataOutHandler;
import gui.MainApp;
import common.filesStatistic.FileProperties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 *  Класс-контроллер, осуществляющий функционал AddFilesView.fxml .
 */

public class AddFilesController implements Initializable {

    @FXML
    private TableView<FileProperties> tableView;
    @FXML
    private TableColumn<FileProperties, String> nameColumn;
    @FXML
    private TableColumn<FileProperties, String> lengthColumn;

    private FileChooser fileChooser;

    private List<File> filesList;
    private ObservableList<FileProperties> observableList;

    private DataOutHandler dataOutHandler;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setEditable(true);
        fileChooser = MainApp.getFileChooser();
        observableList = FXCollections.observableArrayList();
        initColumn();
        tableView.setItems(observableList);
        dataOutHandler = MainApp.getInitStream().getDataOutHandler();
    }

    /**Button "Remove from list"
     * Remove one selected file from list for send*/
    @FXML
    private void deleteAction () {
        int index = tableView.getSelectionModel().getSelectedIndex();
        try {
            observableList.remove(index);
            filesList.remove(index);
        } catch (ArrayIndexOutOfBoundsException ae) {
//            ae.printStackTrace();
        }
    }

    /**Button "Send"
     * Send files on server*/
    @FXML
    private void sendAction () {
        dataOutHandler.sendFile(filesList);
        filesList.clear();
        observableList.clear();
    }

    /**Button "Add more files on list"*/
    @FXML
    private void addMoreAction () {
        List<File> list = null;
        try {
            list = new ArrayList<>(fileChooser.showOpenMultipleDialog(MainApp.getPrimaryStage()));
            listOnView(list);

            if (filesList == null) {
                filesList = list;
            } else {
                filesList.addAll(list);
            }

        } catch (RuntimeException e) {
//            e.printStackTrace();
        }

    }

    /** Add selected files on list */
    private void listOnView(List<File> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        else {
            for (File file: list) {
                observableList.add(new FileProperties(file.getName(), file.length()));
            }
        }
    }

    /** Initializing the table factory */
    private void initColumn () {
        nameColumn.setCellValueFactory(new PropertyValueFactory<FileProperties, String>("name"));
        lengthColumn.setCellValueFactory(new PropertyValueFactory<FileProperties, String>("length"));
    }

}
