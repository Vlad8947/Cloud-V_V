package common.pkg;

public class AuthPkg extends Pkg {

    private String login, password;

    public AuthPkg (String login, String password) {
        super(PkgCommands.AUTHORIZATION);
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
