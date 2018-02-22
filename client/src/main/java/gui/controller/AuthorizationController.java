package gui.controller;

import client.handler.SysOutHandler;
import gui.Dialogs;
import gui.MainApp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class AuthorizationController implements Initializable {

    @FXML
    private AnchorPane authorizationPane;

    @FXML
    private Label statusLabel;
    private final String defaultStatusLabal = "Enter login/password";
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button connectButton;

    private static LinkedList<Node> interfaceList;

    private MainApp mainApp;
    private String login;
    private String password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        mainApp = MainApp.getMainApp();
        interfaceList = new LinkedList<>();
        fillInterfaceList();
    }

    /**ConnectButton onAction*/
    @FXML
    private void connect(){

        disableInterface();

        login = loginField.getText();
        password = passwordField.getText();

        System.out.println("login = " + login + ", pass = " + password);

        if (login.isEmpty() || password.isEmpty()){
            Dialogs.nullField();
            enableInterface();
        }
        else {
            MainApp.getInitStream().getSysOutHandler().sendAuthorization(login, password);
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
