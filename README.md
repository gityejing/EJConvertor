# Excel - JavaBean 转换

实现 excel 与 javaBean field 之间的相互转换，采用配置文件设置两者之间的映射



## 支持的数据类型

目前仅支持 java 的基本数据类型及其包装类，以及 Date 类型。具体如下：

* char
* boolean
* short
* byte
* int
* long
* float
* double
* java.lang.Character
* java.lang.Boolean
* java.lang.Short
* java.lang.Byte
* java.lang.Integer
* java.lang.Long
* java.lang.Float
* java.lang.Double
* java.util.Date
* java.sql.Date



## 配置

示例：

```
public class Supplier {

	private Integer id;// 供应商ID
	private String name;// 供应商名
	private String personInCharge;// 负责人
	private String tel;// 联系电话
	private String email;// 电子邮件
	private String address;// 供应商地址
	
	// setter and getter
}
```

对应的配置文件设置：

```
<?xml version="1.0" encoding="UTF-8"?>

<configuration xmlns="http://www.ken.com/schema/EJConvertor"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://www.ken.com/schema/EJConvertor EJConvertor.xsd">
	<entity class="com.ken.wms.domain.Supplier" sheetName="测试表">
		<property>
			<field>name</field>
			<value>供应商名</value>
		</property>
		<property>
			<field>personInCharge</field>
			<value>负责人</value>
		</property>
		<property>
			<field>tel</field>
			<value>联系电话</value>
		</property>
		<property>
			<field>email</field>
			<value>电子邮件</value>
		</property>
		<property>
			<field>address</field>
			<value>供应商地址</value>
		</property>
	</entity>
</configuration>
```

配置文件中主要包括：`entity`、`property`、`field`、`value` 四种标签。

* 每一个`entity`代表了一个 javaBean，也即在进行转换时，一个`entity`对应着一个 Excel 表格。`entity`标签的属性`class`配置 javaBean 的全称类名
* 每一个`property`代表 javaBean 的一个属性
* `property`标签的子元素`field`代表某一个属性字段在 javaBean 中的名称，而子元素 `value`  代表该属性字段属性在 Excel 表格所在列的表头名称



## 使用

1. 创建 `EJConvertor` 实例。

   1. 默认创建。若不指定配置文件的路径，程序寻找文件名为`EJConvertorConfig.xml`的配置文件

   2. 指定配置文件的路径

      ```
      EJConvertor ejConvertor = new EJConvertor("src/test/java/SimpleJavaBeanTest/EJConvertorConfig.xml");
      ```

2. 将 javaBean 转换为 Excel 文件

   ```
   /**
   * 讀取 Excel 文件中的内容 Excel 文件中的每一行代表了一个对象实例，而行中各列的属性值对应为对象中的各个属性值
   * 读取时，需要指定读取目标对象的类型以获得相关的映射信息，并且要求该对象已在配置文件中注册
   *
   * @param classType 目标对象的类型
   * @param file      数据来源的 Excel 文件
   * @return 包含若干个目标对象实例的 List
   */
   public <T> List<T> excelReader(Class<T> classType, File file);
   ```

3. 将 excel 转换为javaBean

   ```
   /**
   * 将 List 中的元素对象写入到 Excel 中，其中每一个对象的一行，每一列的内容为对象的属性
   *
   * @param classType 目标对象的类型
   * @param elems     数据来源的 List
   * @return 返回excel文件
   */
   public File excelWriter(Class<?> classType, List<?> elems);
   ```



## 测试

JavaBean：

```
public class JavaBean {

    private char charType;
    private boolean booleanType;
    private short shortType;
    private byte byteType;
    private int intType;
    private long longType;
    private float floatType;
    private double doubleType;

    private Character charBoxType;
    private Boolean booleanBoxType;
    private Short shortBoxType;
    private Byte byteBoxType;
    private Integer intBoxType;
    private Long longBoxType;
    private Float floatBoxType;
    private Double doubleBoxType;

    private java.util.Date utilDate;
    private java.sql.Date sqlDate;
    
    // setter and getter
}
```

配置文件：

```
<?xml version="1.0" encoding="UTF-8"?>

<configuration xmlns="http://www.ken.com/schema/EJConvertor"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://www.ken.com/schema/EJConvertor EJConvertor.xsd">

    <entity class="SimpleJavaBeanTest.JavaBean" sheetName="测试表" boldHeading="true">
        <property>
            <field>charType</field>
            <value>char原始类型</value>
        </property>
        <property>
            <field>booleanType</field>
            <value>boolean原始类型</value>
        </property>
        <property>
            <field>shortType</field>
            <value>short原始类型</value>
        </property>
        <property>
            <field>byteType</field>
            <value>byte原始类型</value>
        </property>
        <property>
            <field>intType</field>
            <value>int原始类型</value>
        </property>
        <property>
            <field>longType</field>
            <value>long原始类型</value>
        </property>
        <property>
            <field>floatType</field>
            <value>float原始类型</value>
        </property>
        <property>
            <field>doubleType</field>
            <value>double原始类型</value>
        </property>

        <property>
            <field>charBoxType</field>
            <value>char包装类型</value>
        </property>
        <property>
            <field>booleanBoxType</field>
            <value>boolean包装类型</value>
        </property>
        <property>
            <field>shortBoxType</field>
            <value>short包装类型</value>
        </property>
        <property>
            <field>byteBoxType</field>
            <value>byte包装类型</value>
        </property>
        <property>
            <field>intBoxType</field>
            <value>int包装类型</value>
        </property>
        <property>
            <field>longBoxType</field>
            <value>long包装类型</value>
        </property>
        <property>
            <field>floatBoxType</field>
            <value>float包装类型</value>
        </property>
        <property>
            <field>doubleBoxType</field>
            <value>double包装类型</value>
        </property>

        <property>
            <field>utilDate</field>
            <value>util类型的日期</value>
        </property>
        <property>
            <field>sqlDate</field>
            <value>sql类型的日期</value>
        </property>
    </entity>

</configuration>
```

### 测试一 ：将 javaBean 转换为 excel 文件：

测试代码：

```
	// 将创建的 javaBean 对象写到 Excel 文件中
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
        EJConvertor ejConvertor = new EJConvertor("src/test/java/SimpleJavaBeanTest/EJConvertorConfig.xml");

        File excel = ejConvertor.excelWriter(JavaBean.class, javaBeans);
        if (excel == null)
            System.out.println("null");
        else {
            File fileSave = new File("src/test/java/SimpleJavaBeanTest/excel.xlsx");
            Files.copy(excel.toPath(), fileSave.toPath());
        }
    }
```

### 测试二： 将 Excel 转换为 javaBean

测试代码：

```
	// 将刚才写到 Excel 文件的内容读取进来
	@Test
    public void convertExcelToJavaBean(){
        // create javaBean - Excel convertor
        EJConvertor ejConvertor = new EJConvertor("src/test/java/SimpleJavaBeanTest/EJConvertorConfig.xml");

        // prepare the excel file
        File excel = new File("src/test/java/SimpleJavaBeanTest/excel.xlsx");

        // read the content of excel
        List<JavaBean> javaBeans = ejConvertor.excelReader(JavaBean.class, excel);

        // output the content
        javaBeans.forEach(System.out::println);
    }
```

输出：

```
JavaBean{
charType=a
, booleanType=false
, shortType=123
, byteType=117
, intType=123456
, longType=123456789
, floatType=14.69
, doubleType=123.456
, charBoxType=b
, booleanBoxType=true
, shortBoxType=456
, byteBoxType=118
, intBoxType=789456
, longBoxType=987654321
, floatBoxType=123.45
, doubleBoxType=456.123
, utilDate=Wed Apr 19 09:28:48 CST 2017
, sqlDate=2017-04-19}
```