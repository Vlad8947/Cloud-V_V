package client.handler;

import client.stream.OutStream;
import common.pkg.AuthPkg;
import common.pkg.Pkg;
import common.pkg.PkgCommands;

/**
 * Класс-синглтон посредник для упаковки команд в Pkg и последующей отправки в OutStream.
 */

public class SysOutHandler {

//    private static OutStream outStream;

    private SysOutHandler() {
//        this.outStream = outStream;
    }

    public static void init(){
        new SysOutHandler();
    }

    public static void sendCommand(PkgCommands command){
        OutStream.sendPkg(new Pkg(command));
    }

    public static void sendAuthorization(boolean registration, String login, String password) {
        OutStream.sendPkg(new AuthPkg(registration, login, password));
    }
}
