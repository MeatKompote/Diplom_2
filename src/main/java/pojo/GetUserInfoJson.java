package pojo;

public class GetUserInfoJson extends AbstractPojo{

    private boolean success;
    private UserInfoJson user;

    public GetUserInfoJson(boolean success, UserInfoJson user) {
        this.success = success;
        this.user = user;
    }

    public GetUserInfoJson() {
    }

    public boolean isSuccess() {
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
}
