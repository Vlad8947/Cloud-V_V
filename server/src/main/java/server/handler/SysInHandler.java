package server.handler;


import common.pkg.*;
import server.Server;
import server.data.DataStatement;
import server.stream.InitStream;

public class SysInHandler {

    private DataStatement dataStatement;
    private InitStream initStream;
    private SysOutHandler sysOutHandler;

    private AuthPkg authPkg;

    private int idClient = -1;

    public SysInHandler(InitStream initStream) {
        this.initStream = initStream;
        dataStatement = new DataStatement();
        sysOutHandler = initStream.getSysOutHandler();
    }

    public void handle (Pkg pkg) {

        if(idClient < 1) {
            if(pkg instanceof AuthPkg) {
                authorization((AuthPkg) pkg);
            }
            else if(pkg.getCommand() == PkgCommands.END){
                initStream.close();
            }
        }


    }


    public void authorization (AuthPkg authPkg) {

        idClient = dataStatement.isAuthorized(authPkg.getLogin(), authPkg.getPassword());

        if (idClient > 0){
            /** Вход после авторизации */
            sysOutHandler.sendCommand(PkgCommands.AUTH_OK);
        } else if (idClient < 0){
            sysOutHandler.sendCommand(PkgCommands.AUTH_WRONG);
        } else {
            sysOutHandler.sendCommand(PkgCommands.ERROR_ON_SERVER);
        }

    }
}
