package client.handler;

import client.stream.SysOutStream;
import common.pkg.AuthPkg;
import common.pkg.Pkg;
import common.pkg.PkgCommands;

public class SysOutHandler {

    private SysOutStream sysOutStream;

    public SysOutHandler(SysOutStream sysOutStream) {
        this.sysOutStream = sysOutStream;
    }

    public void sendCommand(PkgCommands command){
        sysOutStream.put(new Pkg(command));
    }

    public void sendAuthorization(String login, String password) {
        sysOutStream.put(new AuthPkg(login, password));
    }
}
