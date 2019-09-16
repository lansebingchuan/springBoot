package com.zht;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zht.websocket1.BitCoinDataCenter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Hello world!
 *
 */
@MapperScan("com.zht.dao")
@SpringBootApplication
@ServletComponentScan //Tomcat servlet扫描
public class SpringBootMybatis
{
    private  static ObjectMapper objectMapper  = new ObjectMapper();
    {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);//指定序列化格式
    }
    public static void main( String[] args )
    {
        SpringApplication.run( SpringBootMybatis.class ,args);
    }

    @Bean(initMethod = "init")//开启线程，发送数据给客户端
    public BitCoinDataCenter bitCoinDataCenter(){
        return new BitCoinDataCenter();
    }

    /*首先注入一个ServerEndpointExporterBean,该Bean会自动注册使用@ServerEndpoint注解申明的websocket endpoint*/
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {//开启 WebSocket 的扫描加载
        return new ServerEndpointExporter();
    }


    public static ObjectMapper objectMapper(){ return  objectMapper; }//通用JSON序列化

    @Bean(value = "webServerObj")
    public Object object(){
        return new Object();
    }
}
