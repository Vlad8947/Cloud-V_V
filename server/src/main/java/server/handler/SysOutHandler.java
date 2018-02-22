package server.handler;

import common.pkg.Pkg;
import common.pkg.PkgCommands;
import server.stream.SysOutStream;

public class SysOutHandler {

    private SysOutStream sysOutStream;

    public SysOutHandler(SysOutStream sysOutStream) {
        this.sysOutStream = sysOutStream;
    }

    void sendCommand(PkgCommands command){
        sysOutStream.put(new Pkg(command));
    }

}
