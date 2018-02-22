package server.stream;

import common.pkg.Pkg;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SysOutStream {

    private InitStream initStream;
    private ObjectOutputStream sysObjectOutputStream;

    public SysOutStream(ObjectOutputStream sysObjectOutputStream) {
        this.sysObjectOutputStream = sysObjectOutputStream;
    }

    public void put (Pkg pkg) {
        try {
            sysObjectOutputStream.writeObject(pkg);
            System.out.println("common.handler put");
        } catch (IOException e) {
            System.out.println("!!!SysOutStream.put() IOEx");
//            e.printStackTrace();
            initStream.close();
        }
    }

}
