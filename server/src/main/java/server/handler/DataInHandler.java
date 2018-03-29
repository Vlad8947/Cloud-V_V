package server.handler;

import common.pkg.DataPkg;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;

/**
 * Класс для обработки принимаемых пакетов с частями файлов.
 */

public class DataInHandler {

    private String clientPath;

    public DataInHandler() {

    }

    // TODO: check file name
    public void receive (DataPkg dataPkg) {

        try {
            Files.write(Paths.get(clientPath + dataPkg.getName()), dataPkg.getData(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setClientPath(String clientPath) {
        this.clientPath = clientPath;
    }
}
