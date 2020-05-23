package main.model;

/**
 * 比赛场地（一个小组有两块比赛场地）
 */
public class Field {

    /**
     * 场地名字
     */
    private String fieldName;


    public String getFieldName() {
        return fieldName;
    }


    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


    public Field(String name) {
        setFieldName(name);
    }

}
