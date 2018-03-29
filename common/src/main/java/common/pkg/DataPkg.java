package common.pkg;

/**
 * Пакет с частью передаваемого файла
 */

public class DataPkg extends Pkg {

    private byte[] data;
    private String name;

    public DataPkg (String name, byte[] data) {
        super(PkgCommands.DATA);
        this.data = data;
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public String getName() {
        return name;
    }

}
