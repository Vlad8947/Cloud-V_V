package client.stream;

import client.handler.SysInHandler;
import client.handler.SysOutHandler;

import java.io.*;
import java.net.Socket;

public class InitStream extends Thread {

    private final String HOST = "localhost";
    private final int SYS_PORT = 8189;
    private final int DATA_PORT = 8190;

    private ObjectOutputStream sysObjectOutputStream;
    private ObjectInputStream sysObjectInputStream;

    private SysInStream sysInStream;
    private DataInStream dataInStream;
    private SysOutStream sysOutStream;
    private DataOutStream dataOutStream;

    private SysInHandler sysInHandler;
    private SysOutHandler sysOutHandler;


    public InitStream() {
        super();
    }


    @Override
    public void run() {

        try
        (   /** Streams initialization */
            Socket socket = new Socket(HOST, SYS_PORT);
            ObjectOutputStream sysObjectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream sysObjectInputStream = new ObjectInputStream(socket.getInputStream());
        )
        {
            System.out.println("StrInit start");

            this.sysObjectOutputStream = sysObjectOutputStream;
            this.sysObjectInputStream = sysObjectInputStream;

            sysOutStream = new SysOutStream(sysObjectOutputStream);
            sysOutHandler = new SysOutHandler(sysOutStream);
            sysInHandler = new SysInHandler(sysOutHandler);

            sysInStream = new SysInStream(sysObjectInputStream, sysInHandler);
            sysInStream.setDaemon(true);
            sysInStream.start();

            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("InitStream final");
        }

    }


    /** Корректное завершение сокета */
    public void close () {

        sysInStream.close();

        synchronized (this) {
            this.notify();
        }
    }


    public ObjectOutputStream getSysObjectOutputStream() {
        return sysObjectOutputStream;
    }

    public ObjectInputStream getSysObjectInputStream() {
        return sysObjectInputStream;
    }

    public SysInStream getSysInStream() {
        return sysInStream;
    }

    public DataInStream getDataInStream() {
        return dataInStream;
    }

    public SysOutStream getSysOutStream() {
        return sysOutStream;
    }

    public DataOutStream getDataOutStream() {
        return dataOutStream;
    }

    public SysInHandler getSysInHandler() {
        return sysInHandler;
    }

    public SysOutHandler getSysOutHandler() {
        return sysOutHandler;
    }

}
