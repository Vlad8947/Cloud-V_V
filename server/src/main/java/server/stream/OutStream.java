package server.stream;

import common.pkg.Pkg;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Класс, осуществляющий отправку объектов типа Pkg.
 */

public class OutStream {

    private InitStreams initStreams;
    private ObjectOutputStream objectOutputStream;
    private Pkg pkg;
//    private Exchanger<Pkg> exchangerToSysOutStream;
    private boolean isClosed = false;

    public OutStream(InitStreams initStreams) {
        this.initStreams = initStreams;
        this.objectOutputStream = initStreams.getObjectOutputStream();
    }

    public synchronized void sendPkg (Pkg pkg) {
        try {
            objectOutputStream.writeObject(pkg);
        } catch (IOException e) {
            System.out.println("!!!Ex in OutStream.sendPkg");
//            e.printStackTrace();
        }
    }

//    public void run () {
//        try {
//
//            while(!isClosed) {
//                pkg = exchangerToSysOutStream.exchange(pkg);
//                System.out.println("outStream take");
//                objectOutputStream.writeObject(pkg);
//                pkg = null;
//                System.out.println("common.handler put");
//            }
//
//        } catch (IOException e) {
//            if(!isClosed) {
//                System.out.println("!!!OutStream.put() IOEx");
//            }
////            e.printStackTrace();
//            initStreams.close();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

//    public void close () {
//        isClosed = true;
//    }

}
