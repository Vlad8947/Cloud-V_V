package server.stream;

import common.pkg.Pkg;
import server.handler.InHandler;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Класс-поток для принятия объектов типа Pkg и их передачей в класс InHandler для обработки.
 * Создается в классе-потоке InitStreams как демон. При намерянном завершении программы,
 * Для корректного завершения вызывается метод close().
 */

public class InStream extends Thread {

    private InitStreams initStreams;
    private ObjectInputStream objectInputStream;
//    private Exchanger<Pkg> exchangerToSysInHandler;
    private InHandler inHandler;
    private Boolean isClosed = false;

    public InStream(InitStreams initStreams) {
        super();
        this.objectInputStream = initStreams.getObjectInputStream();
        this.inHandler = initStreams.getInHandler();
//        this.exchangerToSysInHandler = initStreams.getExchangerToSysInHandler();
    }

    @Override
    public void run() {

        System.out.println("InStream run");

        try {

            /** Принятие файла в обработчик */
            while(true){
                inHandler.handlePkg((Pkg) objectInputStream.readObject());
//                exchangerToSysInHandler.exchange((Pkg) objectInputStream.readObject());
            }

        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        catch (IOException e) {

            /** Если закрытие не преднамеренное */
            if(!isClosed) {
//                e.printStackTrace();
                initStreams.close();
                System.out.println("!!!InStream.run() IOEx");
            }

        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        System.out.println("InStream close");

    }

    public void close(){
        isClosed = true;
    }

}
