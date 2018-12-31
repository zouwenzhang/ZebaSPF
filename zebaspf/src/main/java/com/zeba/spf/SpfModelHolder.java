package com.zeba.spf;

import java.lang.reflect.Field;

import com.zeba.spf.annotation.SpfColumn;

public class SpfModelHolder {
    private Field field;
    private String columnName;
    private String defString;
    private Integer defInt;
    private Long defLong;
    private Float defFloat;
    private Boolean defBoolean;

    public SpfModelHolder(Field f,SpfColumn spfColumn){
        field=f;
        field.setAccessible(true);
        String name=null;
        if(spfColumn!=null&&spfColumn.value().length()>0){
            name=spfColumn.value();
        }else if(spfColumn!=null&&spfColumn.name().length()>0){
            name=spfColumn.name();
        }
        if(name==null){
            columnName=f.getName();
        }else{
            columnName=name;
        }
        if(spfColumn!=null){
            defString=spfColumn.defString();
            defInt=spfColumn.defInt();
            defLong=spfColumn.defLong();
            defFloat=spfColumn.defFloat();
            defBoolean=spfColumn.defBoolean();
        }
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDefString() {
        if(defString==null){
            return "";
        }
        return defString;
    }

    public void setDefString(String defString) {
        this.defString = defString;
    }

    public Integer getDefInt() {
        if(defInt==null){
            return 0;
        }
        return defInt;
    }

    public void setDefInt(Integer defInt) {
        this.defInt = defInt;
    }

    public Long getDefLong() {
        if(defLong==null){
            return 0L;
        }
        return defLong;
    }

    public void setDefLong(Long defLong) {
        this.defLong = defLong;
    }

    public Float getDefFloat() {
        if(defFloat==null){
            return 0f;
        }
        return defFloat;
    }

    public void setDefFloat(Float defFloat) {
        this.defFloat = defFloat;
    }

    public Boolean getDefBoolean() {
        if(defBoolean==null){
            return false;
        }
        return defBoolean;
    }

    public void setDefBoolean(Boolean defBoolean) {

        this.defBoolean = defBoolean;
    }
}
