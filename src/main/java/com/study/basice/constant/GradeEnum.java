package com.study.basice.constant;

/**
 * xxxxx此处为类的描述信息
 *
 * @author zhaoyk
 * @date 2022/7/6
 **/
public enum GradeEnum {

    PERIOD_UPPER("1", "上学期"),
    PERIOD_LOWER("2", "下学期");

    private Integer value;
    private String code;
    private String desc;


    private GradeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private GradeEnum(String code, int value, String desc) {
        this.code = code;
        this.value = value;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getCode() {
        return code;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


}
