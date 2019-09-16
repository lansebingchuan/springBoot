package com.zht.serviceImpl;

import com.zht.bean.Student;
import com.zht.dao.StudentMapper;
import com.zht.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zht
 * @create 2019-08-30 9:00
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;


    @Override
    public Student getStudent() {
        return  studentMapper.selectByPrimaryKey(1);
    }

    @Override
    public int updateStudent(String newName, Integer id) {
        return studentMapper.updateStudent(newName, id);
    }

    @Override
    public Student selectStudent(String id) {
        return  studentMapper.selectStudent(id);
    }


}
