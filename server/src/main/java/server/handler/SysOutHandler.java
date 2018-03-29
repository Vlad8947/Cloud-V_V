package server.handler;

import common.pkg.Pkg;
import common.pkg.PkgCommands;
import server.stream.InitStreams;
import server.stream.OutStream;

import java.util.concurrent.Exchanger;

/**
 * Класс-посредник для упаковки команд в Pkg и последующей отправки в OutStream.
 */

public class SysOutHandler {

    private InitStreams initStreams;
    private OutStream outStream;

//    private Exchanger<Pkg> exchangerToSysOutStream;
//    private Exchanger<PkgCommands> exchangerToSysOutHandler;

    private PkgCommands pkgCommand;

    public SysOutHandler(InitStreams initStreams) {
        this.initStreams = initStreams;
        outStream = initStreams.getOutStream();
//        exchangerToSysOutStream = initStreams.getExchangerToSysOutStream();
//        exchangerToSysOutHandler = initStreams.getExchangerToSysOutHandler();
    }

    void sendPkgCommand (PkgCommands pkgCommand) {
        outStream.sendPkg(new Pkg(pkgCommand));
    }


//    @Override
//    public void run() {
//
//        try {
//            while (true) {
//
//                pkgCommand = exchangerToSysOutHandler.exchange(pkgCommand);
//                exchangerToSysOutStream.exchange(new Pkg(pkgCommand));
//
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            initStreams.close();
//        }
//
//    }

}
