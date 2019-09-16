package com.zht;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Unit test for simple App.
 * 接口反射 ， 获取数据，首先会有静态数据
 * 通过 JDK 动态代理生成数据
 */
public class AppTest
{
    private static StudentDao studentDao;

    public static void setStudentDao(StudentDao studentDao) {
        AppTest.studentDao = studentDao;
    }

    public static void main(String[] args) throws Exception{
        //xml();//基于xml方式的mybatis数据提取
        zhujie();//基于注解方式的数据提取
    }

    private static void zhujie() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<AppTest> appTestClass = AppTest.class;
        AppTest appTest = appTestClass.newInstance();
        Field[] declaredFields = appTestClass.getDeclaredFields();//获取所有的字段
        for (int i= 0 ; i < declaredFields.length ; i++){
            Field daoField = declaredFields[i];//获取一个 DAO field
            Class<?> daoType = daoField.getType();//DAO 的 class

            StudentImpl student = new StudentImpl();//创建代理对象
            Object proxyInstance = Proxy.newProxyInstance(daoType.getClassLoader(), new Class[]{daoType}, student);
            String fieldName = daoField.getName();//DAO  的名称
            String setFieldName = Snippet.altToSet(fieldName);//获取 set 注入方法名称
            Method method = appTestClass.getMethod(setFieldName, StudentDao.class);
            method.invoke(appTest, proxyInstance);//注入 代理对象

            Method[] daoDeclaredMethods = daoType.getDeclaredMethods();//DAO 的所有的方法
            for (int f= 0 ; f < daoDeclaredMethods.length ; f++){//DAO 的所有的方法
                Method daoMethod = daoDeclaredMethods[f]; //DAO 的方法
                String daoMethodName = daoMethod.getName();//获取方法名称
                if (daoMethod.isAnnotationPresent(StudentName.class)){//是否是 StudentName 注解
                    //获取注解对象
                    StudentName studentName = daoMethod.getAnnotation(StudentName.class);
                    DateDate dateDate = new DateDate(daoMethodName, studentName.sql());
                    DateManiger.map.put(daoMethodName, dateDate);
                }else {// 没有 StudentName 注解
                    DateDate dateDate = new DateDate(daoMethodName, "默认姓名：张三");
                    DateManiger.map.put(daoMethodName, dateDate);
                }
            }
        }
        System.out.println("学生："+studentDao.getName());
        System.out.println("学生："+studentDao.getZhangsan());
    }

    private static void xml() throws Exception {
        DateDate dateDate = new DateDate("getName", "张海涛");
        DateDate dateDate1 = new DateDate("getZhangsan", "张三");
        DateManiger.map.put("getName", dateDate);
        DateManiger.map.put("getZhangsan", dateDate1);//添加静态模拟数据，已经就绪
        Class<AppTest> appTestClass = AppTest.class;
        AppTest appTest = appTestClass.newInstance();//创建一个主类对象
        Field[] declaredFields = appTestClass.getDeclaredFields();
        for (int i=0 ; i < declaredFields.length; i++){//主类的所有的字段
            Field field = declaredFields[i];//本类 DAO 字段
            Class<?> fieldType = field.getType();//本类 字段的class

            StudentImpl student = new StudentImpl();//代理对象
            Object proxyInstance = Proxy.newProxyInstance(fieldType.getClassLoader(), new Class<?>[]{fieldType}, student);
            String fieldMethName = Snippet.altToSet(field.getName());
            Method declaredMethod = appTestClass.getDeclaredMethod((fieldMethName), fieldType);
            declaredMethod.invoke(appTest,proxyInstance);//代理对象注入

        }
        System.out.println("学生："+studentDao.getName());
        System.out.println("学生："+studentDao.getZhangsan());
    }


}
