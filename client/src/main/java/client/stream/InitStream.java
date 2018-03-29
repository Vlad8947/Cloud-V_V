package client.stream;

import client.handler.DataOutHandler;
import client.handler.InHandler;
import client.handler.SysOutHandler;
import common.pkg.PkgCommands;

import java.io.*;
import java.net.Socket;

/**
 * Класс-поток для инициализации потоков приёма/передачи пакетов и их обработчиков.
 * В методе run() открывается блок try-with-resources, где в ресурсах прописаны инициализации соккета и
 * потоков ввода/вывода.
 * Создавшийся поток, по окончанию инициализаций, ожидает уведомления (метод close()) для своего завершения.
 * Метод close() вызывается при намеренном завершении программы (либо, если дочерний поток поймал Exception) и
 * осуществляет корректное закрытие всех дочерних потоков, включая себя.
 */

public class InitStream extends Thread {

    private final String HOST = "localhost";
    private final int SYS_PORT = 8189;

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private InStream inStream;
    private OutStream outStream;

    private DataOutHandler dataOutHandler;

    private InHandler inHandler;
    private SysOutHandler sysOutHandler;

    private boolean isClosed = false;


    public InitStream() {
        super();
    }


    @Override
    public void run() {

        try
        (   /** Streams initialization */
            Socket socket = new Socket(HOST, SYS_PORT);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        )
        {
            System.out.println("StrInit start");

            this.objectOutputStream = objectOutputStream;
            this.objectInputStream = objectInputStream;

            OutStream.init(this.objectOutputStream);
            dataOutHandler = new DataOutHandler(this);
            inHandler = new InHandler(sysOutHandler);

            inStream = new InStream(objectInputStream, inHandler);
            inStream.setDaemon(true);
            inStream.start();

            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        catch (IOException e) {
            if(!isClosed) {
                e.printStackTrace();
            }
        }
        finally {
            System.out.println("InitStream finally");
        }

    }

    /** Корректное завершение сокета */
    public void close() {
        inStream.close();
        SysOutHandler.sendCommand(PkgCommands.END);
        synchronized (this) {
            this.notify();
        }
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

    public DataOutHandler getDataOutHandler() {
        return dataOutHandler;
    }
}
