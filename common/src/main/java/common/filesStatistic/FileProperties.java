package common.filesStatistic;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *  Класс, хранящий основные свойства файла клиента.
 */

public class FileProperties {

    private final String name;
    private final String length;

    public FileProperties(String name, long length) {
        this.name = name;
        this.length = createLength(length);
    }


    public String createLength(long length) {

        Double num = new Double(length);
        int i = 0;
        length /= 1024;

        while (length != 0) {
            num /= 1024;
            length /= 1024;
            i++;
        }

        String pref;
        switch (i) {
            case 0:
                pref = "Byte";
                break;

            case 1:
                pref = "kByte";
                break;

            case 2:
                pref = "mByte";
                break;

            default:
                pref = "gByte";
                break;
        }

        return new BigDecimal(num).setScale(0, RoundingMode.UP) + " " + pref;
    }

    public String getName() {
        return name;
    }

    public String getLength() {
        return length;
    }
}
