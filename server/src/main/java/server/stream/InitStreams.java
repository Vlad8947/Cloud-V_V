package server.stream;

import server.dataBase.DataBaseStatement;
import server.handler.DataInHandler;
import server.handler.DataOutHandler;
import server.handler.InHandler;
import server.handler.SysOutHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Класс-поток для инициализации потоков приёма/передачи пакетов и их обработчиков.
 * В методе run() открывается блок try-with-resources, где в ресурсах прописаны инициализации соккета и
 * потоков ввода/вывода.
 * Создавшийся поток, по окончанию инициализаций, ожидает уведомления (метод close()) для своего завершения.
 * Метод close() вызывается при намеренном завершении программы (либо, если дочерний поток поймал Exception) и
 * осуществляет корректное закрытие всех дочерних потоков, включая себя.
 */

public class InitStreams extends Thread {

    private Socket systemSocket;

//    private Exchanger<Pkg> exchangerToSysOutStream;
//    private Exchanger<Pkg> exchangerToSysInHandler;
//    private Exchanger<PkgCommands> exchangerToSysOutHandler;

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private InStream inStream;
    private OutStream outStream;

    private InHandler inHandler;
    private SysOutHandler sysOutHandler;

    private DataInHandler dataInHandler;
    private DataOutHandler dataOutHandler;

//    private DataBaseStatement dataBaseStatement;

    public InitStreams(Socket systemSocket) {
        super();
        this.systemSocket = systemSocket;
//        dataBaseStatement = new DataBaseStatement();

    }

    @Override
    public void run() {

        try (   /** Streams initialization */
            ObjectOutputStream sysObjectOutputStream = new ObjectOutputStream(systemSocket.getOutputStream());
            ObjectInputStream sysObjectInputStream = new ObjectInputStream(systemSocket.getInputStream());
        )
        {
            System.out.println("StrInit start");

            this.objectOutputStream = sysObjectOutputStream;
            this.objectInputStream = sysObjectInputStream;

            dataInHandler = new DataInHandler();
            dataOutHandler = new DataOutHandler();

            outStream = new OutStream(this);
            sysOutHandler = new SysOutHandler(this);
            inHandler = new InHandler(this);
            inStream = new InStream(this);

            inStream.setDaemon(true);
            inStream.start();

            synchronized (this) {
                this.wait();
            }

        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                systemSocket.close();
                System.out.println("Socket closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /** Корректное завершение сокета */
    public void close () {
        inStream.close();
//        outStream.close();
//        inHandler.close();
        synchronized (this) {
            this.notify();
        }
    }

    ObjectInputStream getObjectInputStream(){
        return objectInputStream;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public InStream getInStream() {
        return inStream;
    }

    public OutStream getOutStream() {
        return outStream;
    }

    public InHandler getInHandler() {
        return inHandler;
    }

    public SysOutHandler getSysOutHandler() {
        return sysOutHandler;
    }

    public DataInHandler getDataInHandler() {
        return dataInHandler;
    }

    public DataOutHandler getDataOutHandler() {
        return dataOutHandler;
    }


    //    private <T extends Thread> void goThread (T thread) {
//        thread.setDaemon(true);
//        thread.start();
//    }

    //    public DataBaseStatement getDataBaseStatement() {
//        return dataBaseStatement;
//    }

//    public Exchanger<Pkg> getExchangerToSysOutStream() {
//        return exchangerToSysOutStream;
//    }
//    public Exchanger<Pkg> getExchangerToSysInHandler() {
//        return exchangerToSysInHandler;
//    }
//    public Exchanger<PkgCommands> getExchangerToSysOutHandler() {
//        return exchangerToSysOutHandler;
//    }
}
