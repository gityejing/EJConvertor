package com.ken.EJConvertor;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Ken
 * @since 2017/3/27.
 */
public class EJConvertor {

    /**
     * 默认配置文件名
     */
    private static final String DEFAULT_CONFIG_FILE_NAME = "EJConvertorConfig.xml";

    private static final String ENTITY_NODE = "entity";

    private static final String PROPERTY_NODE = "property";

    private static final String FIELD_NODE = "field";

    private static final String VALUE_NODE = "value";

    private static final String CLASS_ATTRIBUTE = "class";

    /**
     * JavaBean的映射信息
     */
    private Map<String, MappingInfo> excelJavaBeanMap;

    public EJConvertor() {
        init(DEFAULT_CONFIG_FILE_NAME);
    }

    public EJConvertor(String filePath) {
        init(filePath);
    }

    /**
     * 初始化映射信息
     *
     * @param fileLocation 配置文件路径
     */
    private void init(String fileLocation) {
        try {
            // 读取配置文件并创建DOC对象
            File configFile = new File(fileLocation);
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = documentBuilder.parse(configFile);

            // 读取所有的 Entity 节点
            NodeList entities = doc.getElementsByTagName(ENTITY_NODE);
            int entities_num = entities.getLength();

            // 创建excelJavaBeanMap
            this.excelJavaBeanMap = new HashMap<>(entities_num);

            Node entityNode;
            Node propertyNode;
            Node infoNode;
            Element entityElement;
            // 解析 Entity 节点
            for (int entity_index = 0; entity_index < entities_num; entity_index++) {
                entityNode = entities.item(entity_index);
                if (entityNode.getNodeType() == Node.ELEMENT_NODE) {
                    entityElement = (Element) entityNode;

                    String field = null;
                    String value = null;

                    //读取JavaBean的全限定类名
                    String className = entityElement.getAttribute(CLASS_ATTRIBUTE);
                    // 创建映射信息实体
                    MappingInfo mappingInfo = new MappingInfo();
                    mappingInfo.setClassName(className);

                    // 读取 Property 节点
                    NodeList properties = entityElement.getElementsByTagName(PROPERTY_NODE);
                    int properties_num = properties.getLength();

                    // 解析 Property 节点
                    for (int property_index = 0; property_index < properties_num; property_index++) {
                        propertyNode = properties.item(property_index);
                        if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
                            // 读取 Field 和 Value 字段
                            NodeList infoNodes = propertyNode.getChildNodes();
                            int infoNode_num = infoNodes.getLength();
                            for (int infoNode_index = 0; infoNode_index < infoNode_num; infoNode_index++) {
                                infoNode = infoNodes.item(infoNode_index);
                                if (infoNode.getNodeName().equals(FIELD_NODE))
                                    field = infoNode.getTextContent();
                                if (infoNode.getNodeName().equals(VALUE_NODE))
                                    value = infoNode.getTextContent();
                            }
                            if (field != null && value != null) {
                                // 添加到映射信息中
                                mappingInfo.addFieldValueMapping(field, value);
                                mappingInfo.addValueFieldMapping(value, field);
                            }
                        }
                    }
                    // 保存 Entity 映射信息
                    excelJavaBeanMap.put(className, mappingInfo);
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 讀取 Excel 文件中的内容 Excel 文件中的每一行代表了一个对象实例，而行中各列的属性值对应为对象中的各个属性值
     * 读取时，需要指定读取目标对象的类型以获得相关的映射信息，并且要求该对象已在配置文件中注册
     *
     * @param classType 目标对象的类型
     * @param file      数据来源的 Excel 文件
     * @return 包含若干个目标对象实例的 List
     */
    public List<Object> excelReader(Class<?> classType, File file) {
        if (file == null)
            return null;

        // 初始化存放读取结果的 List
        List<Object> content = new ArrayList<>();

        // 获取类名和映射信息
        String className = classType.getName();
        MappingInfo mappingInfo = excelJavaBeanMap.get(className);
        if (mappingInfo == null)
            return null;

        // 读取 Excel 文件
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(file))) {
            Sheet dataSheet = workbook.getSheetAt(0);
            Row row;
            Cell cell;
            Iterator<Row> rowIterator = dataSheet.iterator();
            Iterator<Cell> cellIterator;

            // 读取第一行表头信息
            if (!rowIterator.hasNext())
                return null;
            List<String> methodList = new ArrayList<>();// setter 方法列表
            List<Class<?>> fieldTypeList = new ArrayList<>();// 目标对象属性类型列表
            row = rowIterator.next();
            cellIterator = row.iterator();
            String field;
            while (cellIterator.hasNext()) {
                cell = cellIterator.next();
                field = mappingInfo.getValueFieldMapping(cell.getStringCellValue());
                Class<?> fieldType = classType.getDeclaredField(field).getType();

                fieldTypeList.add(cell.getColumnIndex(), fieldType);
                methodList.add(cell.getColumnIndex(), getSetterMethodName(field));
            }

            // 逐行读取表格内容，创建对象赋值并导入
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                cellIterator = row.iterator();
                Object elem = classType.newInstance();

                // 读取单元格
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();

                    Class<?> fieldType = fieldTypeList.get(columnIndex);
                    String methodName = methodList.get(columnIndex);

                    // 获取单元格的值，并设置对象中对应的属性
                    Object value = getCellValue(fieldType, cell);
                    if (value == null) continue;
                    setField(elem, methodName, value);
                }
                // 放入结果
                content.add(elem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

    /**
     * 将 List 中的元素对象写入到 Excel 中，其中每一个对象的一行，每一列的内容为对象的属性
     *
     * @param classType 目标对象的类型
     * @param elems     数据来源的 List
     * @return 返回excel文件
     */
    public File excelWriter(Class<?> classType, List<?> elems) {

        if (classType == null || elems == null)
            return null;

        // 获取类名和映射信息
        String className = classType.getName();
        MappingInfo mappingInfo = excelJavaBeanMap.get(className);
        if (mappingInfo == null)
            return null;

        File excel = null;
        try {
            // 创建临时文件
            excel = File.createTempFile("excel", ".xlsx");

            // 获取该 class 中定义的 field, 并将对应的信息保存到 List 中
            List<String> fieldList = new ArrayList<>();
            List<String> methodList = new ArrayList<>();
            List<String> valuesList = new ArrayList<>();
            Set<String> fields = mappingInfo.getFieldValueMapping().keySet();
            for (String field : fields) {
                fieldList.add(field);
                methodList.add(getGetterMethodName(field));
                valuesList.add(mappingInfo.getFieldValueMapping(field));
            }

            // 创建 workBook 对象
            Workbook workbook = new XSSFWorkbook();
            // 创建 sheet 对象
            Sheet sheet = workbook.createSheet();

            int rowCount = 0;
            int cellCount;
            Row row;
            Cell cell;

            // 写入第一行表头
            row = sheet.createRow(rowCount++);
            cellCount = 0;
            for (String value : valuesList) {
                cell = row.createCell(cellCount);
                cell.setCellValue(value);
                cellCount++;
            }

            // 写入内容数据
            for (Object elem : elems) {
                row = sheet.createRow(rowCount++);
                cellCount = 0;
                for (String methodName : methodList) {
                    Object value = getField(elem, methodName);
                    cell = row.createCell(cellCount++);
                    setCellValue(value, workbook, cell);
                }
            }

            // 将 workBook 写入到 tempFile 中
            FileOutputStream outputStream = new FileOutputStream(excel);
            workbook.write(outputStream);

            outputStream.flush();
            outputStream.close();
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return excel;
    }

    /**
     * 该方法用于获取单元格 cell 中的值
     *
     * @param fieldType 指定获取的值的类型
     * @param cell      单元格
     * @return 单元格中的值
     */
    private Object getCellValue(Class<?> fieldType, Cell cell) {
        if (cell == null)
            return null;

        int cellType = cell.getCellType();
        Object value = null;
        if (cellType == Cell.CELL_TYPE_STRING) {
            if (fieldType.equals(String.class)) {
                value = cell.getStringCellValue();
            }
        } else if (cellType == Cell.CELL_TYPE_NUMERIC) {
            if (fieldType.equals(String.class)) {
                value = new DecimalFormat("0").format(cell.getNumericCellValue());
            } else if (fieldType.equals(java.sql.Date.class)) {// && HSSFDateUtil.isCellDateFormatted(cell)
                value = new java.sql.Date(cell.getDateCellValue().getTime());
            } else if (fieldType.equals(Long.class)) {
                Double v = cell.getNumericCellValue();
                value = v.longValue();
            } else if (fieldType.equals(Integer.class)) {
                Double v = cell.getNumericCellValue();
                value = v.intValue();
            } else {
                value = cell.getNumericCellValue();
            }
        } else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
            if (fieldType.equals(Boolean.class)) {
                value = cell.getBooleanCellValue();
            }
        } else if (cellType == Cell.CELL_TYPE_FORMULA) {

        } else if (cellType == Cell.CELL_TYPE_ERROR) {

        } else if (cellType == Cell.CELL_TYPE_BLANK) {

        }
        return value;
    }

    /**
     * 设置 Excel 单元格的值
     *
     * @param value 值
     * @param cell  单元格
     */
    private void setCellValue(Object value, Workbook workbook, Cell cell) {
        if (cell == null || value == null)
            return;

        Class<?> valueClassType = value.getClass();
        if (valueClassType.equals(String.class)) {
            String v = (String) value;
            cell.setCellValue(v);
        } else if (valueClassType.equals(Integer.class)) {
            Integer v = (Integer) value;
            cell.setCellValue(v);
        } else if (valueClassType.equals(Long.class)) {
            Long v = (Long) value;
            cell.setCellValue(v);
        } else if (valueClassType.equals(Double.class)) {
            Double v = (Double) value;
            cell.setCellValue(v);
        } else if (valueClassType.equals(Boolean.class)) {
            Boolean v = (Boolean) value;
            cell.setCellValue(v);
        } else if (valueClassType.equals(java.sql.Date.class)) {
            java.sql.Date v = (java.sql.Date) value;
            CellStyle cellStyle = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy/mm/dd"));
            cell.setCellValue(v);
            cell.setCellStyle(cellStyle);
        }
    }

    /**
     * 该方法用于设置对象中属性的值 通过调用目标对象属性对应的 setter 方法，因而要求目标对象必须设置 setter对象，否则赋值不成功
     *
     * @param targetObject 目标对象
     * @param methodName   setter 方法名
     * @param field        方法参数的值
     * @throws Exception Exception
     */
    private void setField(Object targetObject, String methodName, Object field) throws Exception {
        // 获得 setter 方法实例
        Class<?> targetObjectType = targetObject.getClass();
        Class<?> fieldType = field.getClass();
        Method setterMethod = targetObjectType.getMethod(methodName, fieldType);

        // 调用方法
        setterMethod.invoke(targetObject, field);
    }

    /**
     * 获取目标对象中某个属性的值，通过调用目标对象属性对应的 getter 方法，因而要求目标对象必须设置 getter 对象，否则赋值不成功
     *
     * @param targetObject 目标对象
     * @param methodName   getter 方法名
     * @return 返回该属性的值
     * @throws Exception Exception
     */
    private Object getField(Object targetObject, String methodName) throws Exception {
        // 获得 getter 方法实例
        Class<?> targetObjectType = targetObject.getClass();
        Method getterMethod = targetObjectType.getMethod(methodName);

        // 调用方法
        return getterMethod.invoke(targetObject);
    }

    /**
     * 构造 setter 方法的方法名
     *
     * @param field 字段名
     * @return field对应的Setter方法名
     */
    private String getSetterMethodName(String field) {
        // 转换为首字母大写
        String name = field.replaceFirst(field.substring(0, 1), field.substring(0, 1).toUpperCase());
        // 拼接 set 并返回
        return "set" + name;
    }

    /**
     * 构造 getter 方法的方法名
     *
     * @param field 字段名
     * @return field对应的Getter方法名
     */
    private String getGetterMethodName(String field) {
        // 转换为首字母大写
        String name = field.replaceFirst(field.substring(0, 1), field.substring(0, 1).toUpperCase());
        // 拼接 get 并返回
        return "get" + name;
    }

    /**
     * Excel-JavaBean映射信息
     */
    private class MappingInfo {
        /**
         * 映射的JavaBean的全限定类名
         */
        private String className;

        /**
         * Field - Value 映射
         */
        private Map<String, String> fieldValueMapping = new HashMap<>();

        /**
         * Value - Field 映射
         */
        private Map<String, String> valueFieldMapping = new HashMap<>();

        /**
         * 设置映射信息的JavaBean的全称类名
         *
         * @param className JavaBean全称类名
         */
        void setClassName(String className) {
            this.className = className;
        }

        /**
         * 添加 Field - Value 映射
         *
         * @param field Field域
         * @param value Value域
         */
        void addFieldValueMapping(String field, String value) {
            fieldValueMapping.put(field, value);
        }

        /**
         * 返回指定Field映射的Value
         *
         * @param field Field域
         * @return 返回映射的Value
         */
        String getFieldValueMapping(String field) {
            return fieldValueMapping.get(field);
        }

        /**
         * 添加 Value - Field 映射
         *
         * @param value Value域
         * @param field Field域
         */
        void addValueFieldMapping(String value, String field) {
            valueFieldMapping.put(value, field);
        }

        /**
         * 返回指定的Value 映射的 Field
         *
         * @param value Value域
         * @return 返回映射的Field
         */
        String getValueFieldMapping(String value) {
            return valueFieldMapping.get(value);
        }

        /**
         * 获得 Field - Value 映射
         *
         * @return 返回Field - Value 映射
         */
        Map<String, String> getFieldValueMapping() {
            return fieldValueMapping;
        }

        /**
         * 获取 Value - Field 映射
         *
         * @return 返回Value - Field 映射
         */
        Map<String, String> getValueFieldMapping() {
            return valueFieldMapping;
        }
    }
}
