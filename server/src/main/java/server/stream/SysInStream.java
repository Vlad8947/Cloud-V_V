package server.stream;


import common.pkg.Pkg;
import server.handler.SysInHandler;

import java.io.IOException;
import java.io.ObjectInputStream;

public class SysInStream extends Thread {

    private InitStream initStream;
    private ObjectInputStream sysObjectInputStream;
    private SysInHandler sysInHandler;
    private Boolean isClosed = false;

    public SysInStream(ObjectInputStream sysObjectInputStream, SysInHandler sysInHandler) {
        super();
        this.sysObjectInputStream = sysObjectInputStream;
        this.sysInHandler = sysInHandler;
    }

    @Override
    public void run() {

        System.out.println("SysInStream run");

        try {

            /** Принятие файла в обработчик */
            while(true){
                sysInHandler.handle((Pkg) sysObjectInputStream.readObject());
            }

        } catch (IOException e) {

            /** Если закрытие не преднамеренное */
            if(!isClosed) {
//            e.printStackTrace();
                System.out.println("SysInStream.run() IOEx");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("SysInStream close");

    }

    public void close(){
        isClosed = true;
    }

}
