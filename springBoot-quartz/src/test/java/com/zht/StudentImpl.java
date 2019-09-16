package com.zht;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zht
 * @create 2019-09-10 9:29
 */
public class StudentImpl implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        DateDate dateDate = DateManiger.map.get(method.getName());
        if (dateDate == null)
            return  null;
        Object chuli = chuli(dateDate);
        return chuli;
    }

    private Object chuli(DateDate dateDate){///处理sql语句，连接数据库
        return  dateDate.getSql();
    }
}
