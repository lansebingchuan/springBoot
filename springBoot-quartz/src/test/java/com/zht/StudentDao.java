package com.zht;

import java.util.List;

/**
 * @author zht
 * @create 2019-09-10 9:01
 */
public interface StudentDao {

    @StudentName(sql = "张海涛")
    String getName();

    String getZhangsan();

}
