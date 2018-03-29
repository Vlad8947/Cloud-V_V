package client.handler;

import client.stream.InitStream;
import client.stream.OutStream;
import common.pkg.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Класс для обработки передаваемых серверу файлов с отправкой по частям в OutStream.
 */

public class DataOutHandler {

    private OutStream outStream;
    private final int ARRAY_LENGTH = 512;
    private byte[] data = new byte[ARRAY_LENGTH];

    public DataOutHandler(InitStream initStream) {
        outStream = initStream.getOutStream();
    }

    //TODO: check file name in DB!
    //TODO: On thread and with statistic
    public void sendFile (List<File> files) {

        int length = 0;
        String fileName ;

        for (File file: files) {

//            name = file.getName();
//            System.out.println(name);
//
//            try {
//                pkg = new DataPkg(name, Files.readAllBytes(Paths.get( file.getAbsolutePath())));
//                outStream.sendPkg(pkg);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            try (FileInputStream fis = new FileInputStream(file);
                ){

                fileName = file.getName();

                while ( (length = fis.read(data)) != -1) {

                    if (length < ARRAY_LENGTH) {
                        OutStream.sendPkg(new DataPkg(fileName, Arrays.copyOf(data, length)));
                    } else {
                        OutStream.sendPkg(new DataPkg(fileName, data));
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
