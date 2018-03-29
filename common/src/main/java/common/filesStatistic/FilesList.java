package common.filesStatistic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс для хранения списка файлов на сервере
 */

public class FilesList {

    private LinkedList<FileProperties> list = new LinkedList<>();

    public FilesList (FileProperties ... filesProperties) {
        for(FileProperties file: filesProperties) {
            list.add(file);
        }
    }

    public List getList() {
        return list;
    }

    public void removeFile (FileProperties fileProperties) {
        list.remove(fileProperties);
    }

    public void addFile (FileProperties fileProperties) {
        list.add(fileProperties);
    }
}
