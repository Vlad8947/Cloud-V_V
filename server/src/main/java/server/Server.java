package server;

import server.data.DataBaseConnector;
import server.data.DataStatement;
import server.stream.InitStream;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private boolean closed = false;

    private final int SYS_PORT = 8189;
    private final int DATA_PORT = 8190;
    private Socket sysSocket = null;

    private InitStream initStream;

    private DataBaseConnector dataBaseConnector;

    Server() {
        dataBaseConnector = new DataBaseConnector();
    }

    void start() {

        try(ServerSocket systemServerSocket = new ServerSocket(SYS_PORT)){

            System.out.println("server started");

//            Thread socketAccepter = new Thread(() -> {

                while(!closed) {
                    try {
                        sysSocket = systemServerSocket.accept();
                        initStream = new InitStream(sysSocket);
                        initStream.setDaemon(true);
                        initStream.start();

                        System.out.println("A new sysSocket connected");
                    }
                    catch (IOException e) {
                        System.out.println("Error in socketAccepter");
                        e.printStackTrace();
                        try {
                            sysSocket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    finally{

                    }
                }
//
//            });
//
//            socketAccepter.setDaemon(true);
//            socketAccepter.start();
//
//            socketAccepter.join();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            System.out.println("server closed");
            close();
        }
    }

    private void close(){
        closed = true;
        dataBaseConnector.close();
    }

}
