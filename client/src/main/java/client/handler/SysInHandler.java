package client.handler;

import client.stream.InitStream;
import common.pkg.Pkg;
import gui.Dialogs;
import gui.MainApp;
import javafx.application.Platform;

public class SysInHandler {

    private InitStream initStream;
    private SysOutHandler sysOutHandler;

    public SysInHandler(SysOutHandler sysOutHandler) {
        this.sysOutHandler = sysOutHandler;
    }

    /** Обработчик пакетов */
    public void handle (Pkg pkg) {

        switch (pkg.getCommand()) {

            case AUTH_WRONG:
                Dialogs.authWrong();
                MainApp.getAuthorizationController().enableInterface();
                break;

            case AUTH_OK:
                Platform.runLater(() ->{
                    MainApp.getAuthorizationStage().close();
                    MainApp.startApp();
                });
                break;

            case ERROR_ON_SERVER:
                Dialogs.errorOnServer();
                MainApp.getAuthorizationController().enableInterface();
                break;
        }

    }

}