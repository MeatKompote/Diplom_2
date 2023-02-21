package pojo;

public class UserLoginJson extends AbstractPojo {

    private String email;
    private String password;

    public UserLoginJson(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserLoginJson() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
