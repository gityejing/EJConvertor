package SimpleJavaBeanTest;

/**
 * 测试JavaBean User
 *
 * Created by Ken on 2017/3/28.
 */
public class User {

    private Integer id;
    private String userName;
    private String gender;

    public Integer getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public String getUserName() {
        return userName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
