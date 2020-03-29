package com.zht;

import com.zht.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.beans.AppletInitializer;

/**
 * @author zhanghaitao
 * @date 2020/3/24 0024
 */
@Component
@Order(1)
public class InitDataRunner implements ApplicationRunner {

    @Autowired
    InitService initService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        //初始化xml文件
        initService.initXmlData();
    }
}
