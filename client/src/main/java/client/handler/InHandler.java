package client.handler;

import client.stream.InitStream;
import common.pkg.Pkg;
import gui.Dialogs;
import gui.MainApp;
import javafx.application.Platform;

/**
 * Класс для обработки принимаемых пакетов по типу команды.
 * Пакеты принимаются из InStream.
 */

public class InHandler {

    private InitStream initStream;
    private SysOutHandler sysOutHandler;

    public InHandler(SysOutHandler sysOutHandler) {
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

            case REG_OK:
                Dialogs.regOk();
                MainApp.getAuthorizationController().enableInterface();
                break;

            case ERROR_ON_SERVER:
                Dialogs.errorOnServer();
                MainApp.getAuthorizationController().enableInterface();
                break;
        }

    }

}