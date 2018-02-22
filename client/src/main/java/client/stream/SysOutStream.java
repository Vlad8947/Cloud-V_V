package client.stream;

import common.pkg.Pkg;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SysOutStream {

    private InitStream initStream;
    private ObjectOutputStream sysObjectOutputStream;
    private boolean isClosed;

    public SysOutStream(ObjectOutputStream sysObjectOutputStream) {
        this.sysObjectOutputStream = sysObjectOutputStream;
    }

    public void put(Pkg pkg) {
        try {
            sysObjectOutputStream.writeObject(pkg);
        } catch (IOException e) {
            System.out.println("!!!SysOutStream.put() IOEx");
            e.printStackTrace();
        }
    }

    static void end() {

    }

}
