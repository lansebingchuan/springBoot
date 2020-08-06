package com.zht.enumManager;

import com.zht.enumConfig.ConvertibleEnum;

/**
 * 老师enum
 *
 * @author ZHT
 */

public enum TeacherEnum implements ConvertibleEnum {
    /**
     * 大学老师
     */
    UNIVERSITY_TEACHER("大学老师", "university_teacher"),
    /**
     * 大学老师
     */
    HIGH_SCHOOL_TEACHER("中学老师", "high_school_teacher");

    /**
     * 名称
     */
    private String value;

    /**
     * 类型
     */
    private String type;

    TeacherEnum(String value, String type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public Object getId() {
        return this.type;
    }

    @Override
    public String toString() {
        return "TeacherEnum{" +
                "value='" + value + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
