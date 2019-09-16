package com.zht.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.JsonParserSequence;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.org.apache.regexp.internal.RE;
import com.zht.bean.Student;
import com.zht.service.StudentService;
import com.zht.util.redis.RedisUtils;
import com.zht.websocket1.BitCoinDataCenter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zht
 * @create 2019-08-30 9:11
 */
@Controller
public class StudentController {

    @Autowired
    @Qualifier("webServerObj")
    Object webServerObj;//控制是否发送客服端的锁

    @Autowired
    StudentService studentService;

    @RequestMapping("/getStudent")
    @ResponseBody
    public Student getStudent(){
        return studentService.getStudent();
    }


    @RequestMapping("/")
    @ResponseBody
    public String start(@RequestParam(required = false) String name){
        synchronized (webServerObj){
            RedisUtils.setObject("webServerMessage", name);
            webServerObj.notify();//发送数据到客户端,释放等待
        }
        return "你还真勇敢！";
    }
    @RequestMapping("/toIndex")
    public String toIndex(){
        return "index";
    }

    @RequestMapping("/getStudent1")
    @ResponseBody
    public String getStudent1(){
        return "张海涛";
    }

    @RequestMapping("/getStudent2")
    @ResponseBody
    public Student getStudent1(String id){
        return studentService.selectStudent(id);
    }

    @ResponseBody
    @RequestMapping("/updateStudent/{id}/{name}")
    public boolean updateStudent(@PathVariable("id") Integer id , @PathVariable("name") String name){
        int i = studentService.updateStudent(name, id);
        if (i > 0){
            return  true;
        }
        return  false;
    }

    @ResponseBody
    @RequestMapping("/setStudentName/{id}/{name}")//设置kay
    public String setStudentName(@PathVariable("id") Integer id , @PathVariable("name") String name){
        String zht = RedisUtils.setObject(id.toString() , name);
        return zht;

    }
    @ResponseBody
    @RequestMapping("/getStudentName/{id}")//根据key获取
    public String getStudentName(@PathVariable("id") Integer id){
        String zht = (String) RedisUtils.getObject(id.toString());
        return zht;
    }

    @ResponseBody
    @RequestMapping("/delStudentName/{id}")//删除对应的key
    public Long delStudentName(@PathVariable("id") Integer id){
        Long aLong = RedisUtils.delkeyObject(id.toString());
        return aLong;
    }

    @ResponseBody
    @RequestMapping("/tryStudentName/{id}")//尝试key是否存在
    public Boolean tryStudentName(@PathVariable("id") Integer id){
        Boolean aBoolean = RedisUtils.existsObject(id.toString());
        return aBoolean;
    }

    @Test
    public void doGet() {
        try {
            String urlStr = "https://blog.csdn.net/fanx_000/article/details/80297487";
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(false);
            connection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String ss ,total="";

            while ((ss = reader.readLine()) != null) {
                total += ss;
                total+="\n";
            }
            System.out.println("total=" + total);
            reader.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void asd() throws IOException {
        Map<String,String> map = new HashMap<>();
        map.put("name", "张海涛");
        map.put("age","21" );
        Student student = new Student();
        student.setAge(21);
        student.setName("张三");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);//指定序列化格式
        String value = objectMapper.writeValueAsString(map);//序列化对象，返回序列化之后的的值
        String value1 = objectMapper.writeValueAsString(student);//序列化对象，返回序列化之后的的值
        Map map1 = objectMapper.readValue(value, map.getClass());
        System.out.println(value);
        System.out.println(value1);
        System.out.println(map1.toString());
    }
}
