package gui;

import java.io.IOException;

import common.pkg.PkgCommands;
import gui.controller.AuthorizationController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import client.stream.InitStream;

public class MainApp extends Application {

    private static Stage primaryStage;
    private static Stage authorizationStage;
    private static AuthorizationController authorizationController;
    private static Scene scene;
    private static BorderPane rootLayout;

    private static MainApp mainApp;
    private static InitStream initStream;

    private boolean authorized;

    public static void main(String[] args) {
        launch(args);
        initStream.close();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Cloud VV");

        streamStart();

        autorizationView();

    }

    private static void streamStart(){
        initStream = new InitStream();
        initStream.setDaemon(true);
        initStream.start();
    }

    /**
     * Open the sendAuthorization window
     */
    private void autorizationView(){

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainApp.class.getResource("/view/AutorisationAnchorPane.fxml"));

        authorizationStage = new Stage();
        try {
            authorizationStage.setScene(new Scene(fxmlLoader.load()));
            authorizationController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        authorizationStage.setResizable(false);
        authorizationStage.setOnCloseRequest(event -> {
            Platform.exit();
            initStream.getSysOutHandler().sendCommand(PkgCommands.END);
        });
        authorizationStage.showAndWait();

    }

    /**
     * Инициализирует корневой макет.
     */
    private static void initRootLayout() {
        try {
            // Загружаем корневой макет из view файла.
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("/view/RootBorderPane.fxml"));

            rootLayout = (BorderPane) fxmlLoader.load();
            scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает в корневом макете сведения об адресатах.
     */
    public static void startApp() {
        try {
            initRootLayout();

            // Загружаем сведения об адресатах.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/FilesListView.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Помещаем сведения об адресатах в центр корневого макета.
            rootLayout.setCenter(personOverview);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MainApp getMainApp() {
        return mainApp;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public static Stage getAuthorizationStage() {
        return authorizationStage;
    }

    public static AuthorizationController getAuthorizationController() {
        return authorizationController;
    }

    public static InitStream getInitStream() {
        return initStream;
    }

    public static BorderPane getRootLayout() {
        return rootLayout;
    }
}
