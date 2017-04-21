package SimpleJavaBeanTest1;

import java.util.Date;

/**
 * Created by Ken on 2017/4/15.
 */
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

    public char getCharType() {
        return charType;
    }

    public void setCharType(char charType) {
        this.charType = charType;
    }

    public boolean getBooleanType() {
        return booleanType;
    }

    public void setBooleanType(boolean booleanType) {
        this.booleanType = booleanType;
    }

    public short getShortType() {
        return shortType;
    }

    public void setShortType(short shortType) {
        this.shortType = shortType;
    }

    public byte getByteType() {
        return byteType;
    }

    public void setByteType(byte byteType) {
        this.byteType = byteType;
    }

    public int getIntType() {
        return intType;
    }

    public void setIntType(int intType) {
        this.intType = intType;
    }

    public long getLongType() {
        return longType;
    }

    public void setLongType(long longType) {
        this.longType = longType;
    }

    public float getFloatType() {
        return floatType;
    }

    public void setFloatType(float floatType) {
        this.floatType = floatType;
    }

    public double getDoubleType() {
        return doubleType;
    }

    public void setDoubleType(double doubleType) {
        this.doubleType = doubleType;
    }

    public Character getCharBoxType() {
        return charBoxType;
    }

    public void setCharBoxType(Character charBoxType) {
        this.charBoxType = charBoxType;
    }

    public Boolean getBooleanBoxType() {
        return booleanBoxType;
    }

    public void setBooleanBoxType(Boolean booleanBoxType) {
        this.booleanBoxType = booleanBoxType;
    }

    public Short getShortBoxType() {
        return shortBoxType;
    }

    public void setShortBoxType(Short shortBoxType) {
        this.shortBoxType = shortBoxType;
    }

    public Byte getByteBoxType() {
        return byteBoxType;
    }

    public void setByteBoxType(Byte byteBoxType) {
        this.byteBoxType = byteBoxType;
    }

    public Integer getIntBoxType() {
        return intBoxType;
    }

    public void setIntBoxType(Integer intBoxType) {
        this.intBoxType = intBoxType;
    }

    public Long getLongBoxType() {
        return longBoxType;
    }

    public void setLongBoxType(Long longBoxType) {
        this.longBoxType = longBoxType;
    }

    public Float getFloatBoxType() {
        return floatBoxType;
    }

    public void setFloatBoxType(Float floatBoxType) {
        this.floatBoxType = floatBoxType;
    }

    public Double getDoubleBoxType() {
        return doubleBoxType;
    }

    public void setDoubleBoxType(Double doubleBoxType) {
        this.doubleBoxType = doubleBoxType;
    }

    public Date getUtilDate() {
        return utilDate;
    }

    public void setUtilDate(Date utilDate) {
        this.utilDate = utilDate;
    }

    public java.sql.Date getSqlDate() {
        return sqlDate;
    }

    public void setSqlDate(java.sql.Date sqlDate) {
        this.sqlDate = sqlDate;
    }

    @Override
    public String toString() {
        return "JavaBean{" +
                "\ncharType=" + charType +
                "\n, booleanType=" + booleanType +
                "\n, shortType=" + shortType +
                "\n, byteType=" + byteType +
                "\n, intType=" + intType +
                "\n, longType=" + longType +
                "\n, floatType=" + floatType +
                "\n, doubleType=" + doubleType +
                "\n, charBoxType=" + charBoxType +
                "\n, booleanBoxType=" + booleanBoxType +
                "\n, shortBoxType=" + shortBoxType +
                "\n, byteBoxType=" + byteBoxType +
                "\n, intBoxType=" + intBoxType +
                "\n, longBoxType=" + longBoxType +
                "\n, floatBoxType=" + floatBoxType +
                "\n, doubleBoxType=" + doubleBoxType +
                "\n, utilDate=" + utilDate +
                "\n, sqlDate=" + sqlDate +
                '}';
    }
}
