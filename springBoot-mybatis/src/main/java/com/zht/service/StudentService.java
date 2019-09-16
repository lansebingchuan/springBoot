package com.zht.service;

import com.zht.bean.Student;
import org.apache.ibatis.annotations.Param;

/**
 * @author zht
 * @create 2019-08-30 9:12
 */
public interface StudentService {


    Student getStudent();

    int updateStudent(String newName ,Integer id);

    Student selectStudent(String id);
}
