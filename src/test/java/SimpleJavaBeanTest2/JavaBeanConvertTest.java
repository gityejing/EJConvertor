package SimpleJavaBeanTest2;

import com.ken.EJConvertor.EJConvertor;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试多条记录的写入和读出
 * Created by Ken on 2017/4/19.
 */
public class JavaBeanConvertTest {

    @Test
    public void testConvertMultipleJavaBeanToExcel() throws Exception{
        // user javaBean list
        List<User> users = new ArrayList<>();

        // create multiple user javaBean
        User user1 = new User();
        user1.setUserID(101);
        user1.setUserName("Rachel");
        user1.setAge(32);
        user1.setGender("Female");
        user1.setWeight(45.6);

        User user2 = new User();
        user2.setUserID(102);
        user2.setUserName("Ross");
        user2.setAge(34);
        user2.setGender("Male");
        user2.setWeight(75.8);

        User user3 = new User();
        user3.setUserID(103);
        user3.setUserName("Monica");
        user3.setAge(33);
        user3.setGender("Female");
        user3.setWeight(50);

        User user4 = new User();
        user4.setUserID(104);
        user4.setUserName("Chandler");
        user4.setAge(33);
        user4.setGender("Male");
        user4.setWeight(60);

        // add user javaBean to user list
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        // create javaBean - excel convertor
        EJConvertor ejConvertor = new EJConvertor("src/test/java/SimpleJavaBeanTest2/UserMappingConfig.xml");

        // write excel
        File excel = ejConvertor.excelWriter(User.class, users);
        if (excel == null)
            System.out.println("null");
        else {
            File fileSave = new File("src/test/java/SimpleJavaBeanTest2/excel.xlsx");
            Files.copy(excel.toPath(), fileSave.toPath());
        }
    }

    @Test
    public void testConvertMultipleJavaBeanFromExcel(){
        // create javaBean - excel convertor
        EJConvertor ejConvertor = new EJConvertor("src/test/java/SimpleJavaBeanTest2/UserMappingConfig.xml");

        // read excel
        File excel = new File("src/test/java/SimpleJavaBeanTest2/excel.xlsx");
        List<User> users = ejConvertor.excelReader(User.class, excel);

        // output the content of user javaBean
        users.forEach(System.out::println);
    }
}
