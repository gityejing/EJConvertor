package SimpleJavaBeanTest3;

import com.ken.EJConvertor.annotation.EJConvertorColumn;
import com.ken.EJConvertor.annotation.EJConvertorTable;

/**
 * 测试用的 user javaBean
 * Created by Ken on 2017/4/19.
 */
@EJConvertorTable(sheetName = "用户信息", boldHeading = true)
public class User {

    @EJConvertorColumn(columnTitle = "用户ID annotation")
    private Integer userID;

    @EJConvertorColumn(columnTitle = "用户名 annotation")
    private String userName;

    @EJConvertorColumn(columnTitle = "年龄 annotation")
    private int age;

    @EJConvertorColumn(columnTitle = "性别 annotation")
    private String gender;

    @EJConvertorColumn(columnTitle = "体重 annotation")
    private double weight;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", weight=" + weight +
                '}';
    }
}
