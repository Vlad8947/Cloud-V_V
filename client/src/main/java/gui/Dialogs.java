package gui;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class Dialogs {

    Dialogs () {

    }

    public static void nullField(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("The field(s) is empty!");
            alert.showAndWait();
        });
    }

    public static void authWrong(){
        Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Invalid login/password");
        alert.showAndWait();
        });
    }

    public static void errorOnServer() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Error on the server!");
            alert.show();
        });

    }

}

