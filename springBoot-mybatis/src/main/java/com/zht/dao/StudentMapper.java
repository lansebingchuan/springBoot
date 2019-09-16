package com.zht.dao;

import com.zht.bean.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper {

    Student selectByPrimaryKey(Integer id);

    @Update("update student set name = #{newName} where id=#{id}")
    int updateStudent(@Param("newName") String newName , @Param("id") Integer id);

    @Select("select * from student where id=#{id}")
    Student selectStudent(String id);
}