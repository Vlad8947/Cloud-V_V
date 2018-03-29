package server;

import server.dataBase.DataBaseConnector;
import server.stream.InitStreams;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 */

public class Server {

    private boolean closed = false;

    private final int SYS_PORT = 8189;
    private Socket sysSocket = null;

    private InitStreams initStreams;

    private DataBaseConnector dataBaseConnector;

    Server() {
//        dataBaseConnector = new DataBaseConnector();
    }

    void start() {

        try(ServerSocket systemServerSocket = new ServerSocket(SYS_PORT);

        ){

            System.out.println("server started");

            while (!closed) {
                try {

                    sysSocket = systemServerSocket.accept();
                    initStreams = new InitStreams(sysSocket);
                    initStreams.setDaemon(true);
                    initStreams.start();

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
                finally {

                }
            }

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
