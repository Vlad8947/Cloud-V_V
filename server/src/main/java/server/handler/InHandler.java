package server.handler;

import common.pkg.*;
import server.dataBase.DataBaseStatement;
import server.stream.InitStreams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

/**
 *  Класс для обработки принимаемых пакетов по типу команды.
 *  Пакеты принимаются из InStream.
 *  Если пользователь ещё не авторизирован, то комманды на обработку данных не рассматриваются.
 *  При авторизации/регистрации подается запрос базе данных с логином и паролем.
 *
 */

public class InHandler {

    private InitStreams initStreams;
    private SysOutHandler sysOutHandler;
    private DataInHandler dataInHandler;
    private DataOutHandler dataOutHandler;
    private DataBaseStatement dataBaseStatement;

//    private Exchanger<Pkg> exchangerToSysInHandler;
//    private Exchanger<PkgCommands> exchangerToSysOutHandler;

    private Pkg pkg;
    private PkgCommands pkgCommand;

    private boolean isClosed = false;

    private int idClient = -1;

    private final String rootPath = "D:/data/cloudV/";
    private String clientPath;

    public InHandler(InitStreams initStreams) {
        this.initStreams = initStreams;
        sysOutHandler = initStreams.getSysOutHandler();
        dataBaseStatement = DataBaseStatement.getDataBaseStatement();
        dataInHandler = initStreams.getDataInHandler();
        dataOutHandler = initStreams.getDataOutHandler();
//        exchangerToSysInHandler = initStreams.getExchangerToSysInHandler();
//        exchangerToSysOutHandler = initStreams.getExchangerToSysOutHandler();
    }


    /** Принятие пакета из InStream */
    public void handlePkg(Pkg pkg) {

        pkgCommand = pkg.getCommand();

        if(idClient < 1) {

            // TODO: default case
            switch (pkgCommand) {

                case AUTHORIZATION:
//                System.out.println("Auth");
                    authorization((AuthPkg) pkg);
                    break;

                case REGISTRATION:
                    registration((AuthPkg) pkg);
                    break;

                case END:
                    initStreams.close();
                    break;
            }

        }
        else {

            switch (pkg.getCommand()) {

                case DATA:
                    if (idClient > 0) {
                        dataInHandler.receive((DataPkg) pkg);
                    } else {
                        sysOutHandler.sendPkgCommand(PkgCommands.AUTH_WRONG);
//                            exchangerToSysOutHandler.exchange(PkgCommands.AUTH_WRONG);
                    }
                    break;

                case END:
                    initStreams.close();
                    break;
            }
        }

    }

    /** Авторизация */
    private void authorization(AuthPkg authPkg) /*throws InterruptedException*/ {

        idClient = DataBaseStatement.isAuthorized(authPkg.getLogin(), authPkg.getPassword());

        if (idClient > 0) {
            /** Вход после авторизации */
            sysOutHandler.sendPkgCommand(PkgCommands.AUTH_OK);
            setClientPath(authPkg.getLogin());
//            exchangerToSysOutHandler.exchange(PkgCommands.AUTH_OK);
        } else if (idClient < 0) {
            sysOutHandler.sendPkgCommand(PkgCommands.AUTH_WRONG);
//            exchangerToSysOutHandler.exchange(PkgCommands.AUTH_WRONG);
        } else {
            sysOutHandler.sendPkgCommand(PkgCommands.ERROR_ON_SERVER);
//            exchangerToSysOutHandler.exchange(PkgCommands.ERROR_ON_SERVER);
        }

    }

    /** Регистрация */
    // TODO: check this login in DB
    private void registration(AuthPkg authPkg) {

        try {
            DataBaseStatement.registration(authPkg.getLogin(), authPkg.getPassword());
            Files.createDirectory(Paths.get(rootPath + authPkg.getLogin() + "/"));
            sysOutHandler.sendPkgCommand(PkgCommands.REG_OK);
        } catch (SQLException e) {
            System.out.println("!!!SQLEx in InHandler.authorization()");
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /** Установка пути расположения файлов */
    public void setClientPath(String login) {
        this.clientPath = rootPath + login + "/";

        if (Files.notExists(Paths.get(clientPath))) {
            try {
                Files.createDirectory(Paths.get(clientPath));
            } catch (IOException e) {
                System.out.println("!!!IOEx in InHandler.setClientPath()");
//                e.printStackTrace();
            }
        }

        dataInHandler.setClientPath(clientPath);
        dataOutHandler.setClientPath(clientPath);
    }

    //    public void run () {
//
//        while(!isClosed) {
//
//            try {
//
//                pkg = exchangerToSysInHandler.exchange(pkg);
//
//                pkgCommand = pkg.getCommand();
//
//                switch (pkgCommand) {
//
//                    case END:
//                        initStreams.close();
//                        break;
//
//                    case AUTHORIZATION:
//                        System.out.println("Auth");
//                        authorization((AuthPkg) pkg);
//                        break;
//
//                    case DATA:
//                        if (idClient > 0) {
//                            dataInHandler.receive((DataPkg) pkg);
//                        } else {
////                            exchangerToSysOutHandler.exchange(PkgCommands.AUTH_WRONG);
//                        }
//                        break;
//                }
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            pkg = null;
//        }
//
//    }
    //    public void close () {
//        isClosed = true;
//    }
}
