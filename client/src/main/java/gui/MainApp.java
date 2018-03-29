package gui;

import java.io.IOException;

import gui.controller.AuthorizationController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import client.stream.InitStream;

/**
 * Класс, запускающий инициализацию подключения к серверу (класс InitStreams) и графический интерфейс.
 */

public class MainApp extends Application {

    private static Stage primaryStage;
    private static Stage authorizationStage;
    private static AuthorizationController authorizationController;
    private static BorderPane rootLayout, addFilesView, listView;

    private static final FileChooser FILE_CHOOSER = new FileChooser();

    private static InitStream initStream;

    private static boolean isClosed = false;

    public static void main(String[] args) {
        launch(args);
        if(!isClosed) {
            System.out.println("close");
            initStream.close();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        MainApp.primaryStage = primaryStage;
        MainApp.primaryStage.setTitle("Cloud VV");

        streamsStart();

        autorizationView();
    }

    private static void streamsStart(){
        initStream = new InitStream();
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
        setCloseAction(authorizationStage);
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

            rootLayout = fxmlLoader.load();
            primaryStage.setScene(new Scene(rootLayout));
            setCloseAction(primaryStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**Запуск рабочего пространства*/
    public static void startApp() {
        try {
            initRootLayout();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/FilesListView.fxml"));
            listView = loader.load();

            rootLayout.setCenter(listView);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Установка корректного завершения потока InitStreams при закрытии приложения */
    private static void setCloseAction(Stage stage) {
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            try {
                System.out.println("close-1");
                isClosed = true;
                initStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    /** Инициализация модульного окна AddFilesView */
    private static void initAddFilesView() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainApp.class.getResource("/view/AddFilesView.fxml"));
        try {
            addFilesView = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("!!!RootController.addFileAction IOEx");
        }
    }

    /** Установка модульного окна AddFilesView в корневую панель */
    public static void setAddFilesViewOnRoot(){
        Platform.runLater(() ->{
            if(addFilesView == null) {
                initAddFilesView();
            }
            MainApp.getRootLayout().setCenter(addFilesView);
        });
    }

    /** Установка модульного окна FilesListView в корневую панель */
    public static void setListViewOnRoot() {
        Platform.runLater(() ->{
            MainApp.getRootLayout().setCenter(listView);
        });
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
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

    public static FileChooser getFileChooser() {
        return FILE_CHOOSER;
    }


}
