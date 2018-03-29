package gui;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * Класс для вывода окон оповещения.
 */

public class Dialogs {

    Dialogs () {

    }

    /**Пустые поля*/
    public static void nullField(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("The field(s) is empty!");
            alert.showAndWait();
        });
    }

    /**Invalid login/password*/
    public static void authWrong(){
        Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Invalid login/password");
        alert.showAndWait();
        });
    }

    /**Registration was ok*/
    public static void regOk(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Reg. was OK");
            alert.setContentText("Now you can pass authorization.");
            alert.showAndWait();
        });
    }

    /**Error on the server!*/
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

