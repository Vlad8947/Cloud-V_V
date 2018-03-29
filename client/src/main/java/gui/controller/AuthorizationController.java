package gui.controller;

import client.handler.SysOutHandler;
import client.stream.OutStream;
import common.pkg.AuthPkg;
import gui.Dialogs;
import gui.MainApp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * Класс-контроллер, осуществляющий функционал AuthorizationAnchorPane.fxml .
 */

public class AuthorizationController implements Initializable {

    @FXML
    private Label statusLabel;
    private final String defaultStatusLabal = "Enter login/password";
    @FXML
    private ToggleButton toggleAuth;
    @FXML
    private ToggleButton toggleReg;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button connectButton;

    private static LinkedList<Node> interfaceList;
    private ToggleGroup toggleAuthGroup;

    private String login;
    private String password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        mainApp = MainApp.getMainApp();
        interfaceList = new LinkedList<>();
        fillInterfaceList();
        toggleAuthGroup = new ToggleGroup();
        toggleAuth.setToggleGroup(toggleAuthGroup);
        toggleReg.setToggleGroup(toggleAuthGroup);
    }

    /**Button "Connect"
     * Send login and pass for*/
    @FXML
    private void connect(){

        disableInterface();

        login = loginField.getText();
        password = passwordField.getText();

        if (login.isEmpty() || password.isEmpty()){
            Dialogs.nullField();
            enableInterface();
        }
        else {
            SysOutHandler.sendAuthorization(toggleReg.isSelected(), login, password);
        }

    }

    public void disableInterface () {
        Platform.runLater(()->{
            statusLabel.setText("Downloading. Please wait.");
            for(Node node: interfaceList){
                node.setDisable(true);
            }
        });
    }

    public void enableInterface () {
        Platform.runLater(() -> {
            statusLabel.setText(defaultStatusLabal);
            for(Node node: interfaceList){
                node.setDisable(false);
            }
        });
    }

    private void fillInterfaceList() {
        interfaceList.add(connectButton);
        interfaceList.add(passwordField);
        interfaceList.add(loginField);
    }

}
