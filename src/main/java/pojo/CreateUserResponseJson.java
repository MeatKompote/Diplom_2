package pojo;


public class CreateUserResponseJson extends AbstractPojo {

    private boolean success;
    private UserInfoJson user;
    private String accessToken;
    private String refreshToken;

    public CreateUserResponseJson(boolean success, UserInfoJson user, String accessToken, String refreshToken) {
        this.success = success;
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public CreateUserResponseJson() {
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserInfoJson getUser() {
        return user;
    }

    public void setUser(UserInfoJson user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
