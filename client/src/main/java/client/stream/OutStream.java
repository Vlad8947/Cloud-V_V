package client.stream;

import common.pkg.Pkg;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Класс-синглтон, осуществляющий отправку объектов типа Pkg.
 */

public class OutStream {

    private static ObjectOutputStream objectOutputStream;

    private OutStream() {
    }

    public static synchronized void sendPkg(Pkg pkg) {
        try {
            objectOutputStream.writeObject(pkg);
            objectOutputStream.reset();
            objectOutputStream.flush();
        } catch (IOException e) {
            System.out.println("!!!OutStream.sendPkg() IOEx");
            e.printStackTrace();
        }
    }

    static void init (ObjectOutputStream objectOutputStream) {
        OutStream.objectOutputStream = objectOutputStream;
    }

}
