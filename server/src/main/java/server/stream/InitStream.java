package server.stream;

import server.handler.SysInHandler;
import server.handler.SysOutHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class InitStream extends Thread {

    private Socket systemSocket, dataSocket;

    private InitStream thisInitStream;

    private ObjectOutputStream sysObjectOutputStream;
    private ObjectInputStream sysObjectInputStream;

    private SysInStream sysInStream;
    private DataInStream dataInStream;
    private SysOutStream sysOutStream;
    private DataOutStream dataOutStream;

    private SysInHandler sysInHandler;
    private SysOutHandler sysOutHandler;

    public InitStream(Socket systemSocket) {
        super();
        this.systemSocket = systemSocket;
        thisInitStream = this;
    }

    @Override
    public void run() {

        try (   /** Streams initialization */
            ObjectOutputStream sysObjectOutputStream = new ObjectOutputStream(systemSocket.getOutputStream());
            ObjectInputStream sysObjectInputStream = new ObjectInputStream(systemSocket.getInputStream());
        )
        {
            System.out.println("StrInit start");

            this.sysObjectOutputStream = sysObjectOutputStream;
            this.sysObjectInputStream = sysObjectInputStream;

            sysOutStream = new SysOutStream(sysObjectOutputStream);
            sysOutHandler = new SysOutHandler(sysOutStream);
            sysInHandler = new SysInHandler(this);

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

            System.out.println("server.stream close");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
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

        sysInStream.close();

        synchronized (this) {
            this.notify();
        }
    }


    ObjectInputStream getSysObjectInputStream(){
        return sysObjectInputStream;
    }

    public ObjectOutputStream getSysObjectOutputStream() {
        return sysObjectOutputStream;
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
