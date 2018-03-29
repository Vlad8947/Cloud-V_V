package common.pkg;

/**
 * Пакет аутентификации/регистрации.
 */

public class AuthPkg extends Pkg {

    private String login, password;

    public AuthPkg (boolean registation, String login, String password) {
        super(registation ? PkgCommands.REGISTRATION : PkgCommands.AUTHORIZATION);
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
