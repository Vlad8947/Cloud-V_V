package client.stream;

import client.handler.InHandler;
import common.pkg.Pkg;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Класс-поток для принятия объектов типа Pkg и их передачей в класс InHandler для обработки.
 * Создается в классе-потоке InitStreams как демон. При намерянном завершении программы,
 * Для корректного завершения вызывается метод close().
 */

public class InStream extends Thread {

    private InitStream initStream;
    private ObjectInputStream objectInputStream;
    private InHandler inHandler;
    private Boolean isClosed = false;

    public InStream(ObjectInputStream objectInputStream, InHandler inHandler) {
        super();
        this.objectInputStream = objectInputStream;
        this.inHandler = inHandler;
    }

    @Override
    public void run() {

        System.out.println("InStream run");

        try {

            while(!isClosed){
                inHandler.handle((Pkg) objectInputStream.readObject());
            }

        } catch (IOException e) {
            if(!isClosed) {
//            e.printStackTrace();
                System.out.println("!!!InStream.run() IOEx");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("InStream close");

    }

    public void close() {
        isClosed = true;
    }

}
