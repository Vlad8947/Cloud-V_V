package common.pkg;

import java.io.Serializable;

public class Pkg implements Serializable{

    private PkgCommands command;
    private byte[] data;

    public Pkg (PkgCommands command) {
        this.command = command;
    }

    public PkgCommands getCommand() {
        return command;
    }
}

