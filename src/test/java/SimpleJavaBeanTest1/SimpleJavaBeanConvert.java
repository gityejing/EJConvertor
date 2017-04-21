package SimpleJavaBeanTest1;

import com.ken.EJConvertor.EJConvertor;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据类型测试
 * Created by Ken on 2017/4/15.
 */
public class SimpleJavaBeanConvert {

    /**
     * 测试各种数据类型的 field 转换到 excel 的 cell 中的值
     * @throws Exception Exception
     */
    @Test
    public void convertJavaBeanToExcel() throws Exception{

        // create javaBean
        JavaBean javaBean = new JavaBean();
        javaBean.setCharType('a');
        javaBean.setBooleanType(false);
        javaBean.setShortType((short) 123);
        javaBean.setByteType((byte) 117);
        javaBean.setIntType(123456);
        javaBean.setLongType(123456789);
        javaBean.setDoubleType(123.456);
        javaBean.setFloatType((float) 14.69);

        javaBean.setCharBoxType('b');
        javaBean.setBooleanBoxType(true);
        javaBean.setShortBoxType((short) 456);
        javaBean.setByteBoxType((byte)118);
        javaBean.setIntBoxType(789456);
        javaBean.setLongBoxType(987654321L);
        javaBean.setDoubleBoxType(456.123D);
        javaBean.setFloatBoxType((float) 123.45);

        javaBean.setSqlDate(new java.sql.Date(new java.util.Date().getTime()));
        javaBean.setUtilDate(new java.util.Date());

        List<JavaBean> javaBeans = new ArrayList<>();
        javaBeans.add(javaBean);

        // create javaBean - Excel convertor
        EJConvertor ejConvertor = new EJConvertor("src/test/java/SimpleJavaBeanTest1/EJConvertorConfig.xml");

        File excel = ejConvertor.excelWriter(JavaBean.class, javaBeans);
        if (excel == null)
            System.out.println("null");
        else {
            File fileSave = new File("src/test/java/SimpleJavaBeanTest1/excel.xlsx");
            Files.copy(excel.toPath(), fileSave.toPath());
        }
    }

    /**
     * 测试读取 cell 中的值设置到不同数据类型的 field 中
     */
    @Test
    public void convertExcelToJavaBean(){
        // create javaBean - Excel convertor
        EJConvertor ejConvertor = new EJConvertor("src/test/java/SimpleJavaBeanTest1/EJConvertorConfig.xml");

        // prepare the excel file
        File excel = new File("src/test/java/SimpleJavaBeanTest1/excel.xlsx");

        // read the content of excel
        List<JavaBean> javaBeans = ejConvertor.excelReader(JavaBean.class, excel);

        // output the content
        javaBeans.forEach(System.out::println);
    }
}
