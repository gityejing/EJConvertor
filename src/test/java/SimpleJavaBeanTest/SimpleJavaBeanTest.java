package SimpleJavaBeanTest;

import com.ken.EJConvertor.EJConvertor;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * 简单JavaBean 转换测试
 *
 * Created by Ken on 2017/3/28.
 */
public class SimpleJavaBeanTest {

    @Test
    public void initTest(){
        EJConvertor ejConvertor = new EJConvertor("src/test/java/SimpleJavaBeanTest/EJConvertorConfig.xml");
    }

    @Test
    public void javaBeanConvertToExcel() throws IOException {
        // create Convertor Object
        EJConvertor ejConvertor = new EJConvertor("src/test/java/SimpleJavaBeanTest/EJConvertorConfig.xml");

        // create Users
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1);
        user1.setUserName("ken");
        user1.setGender("male");
        User user2 = new User();
        user2.setId(2);
        user2.setUserName("Rose");
        user2.setGender("female");
        userList.add(user1);
        userList.add(user2);

        File excel = ejConvertor.excelWriter(User.class, userList);
        if (excel == null)
            System.out.println("null");
        else {
            File fileSave = new File("./temp/excel.xlsx");
            Files.copy(excel.toPath(), fileSave.toPath());
        }
    }

    @Test
    public void excelConvertToJavaBean(){
        // Create Convertor Object
        EJConvertor ejConvertor = new EJConvertor("src/test/java/SimpleJavaBeanTest/EJConvertorConfig.xml");

        // create a file object represent the excel file
        File excel = new File("./temp/excel.xlsx");

        // read the content of the excel file
        List<Object> users = ejConvertor.excelReader(User.class, excel);

        if (users == null) {
            System.out.println("Fail to read the excel file");
            return;
        }

        // print the user info
        User user;
        for (Object elem : users){
            user = (User) elem;
            System.out.println(user);
        }

    }

}
