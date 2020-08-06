package com.zht;

import com.zht.entity.User;
import com.zht.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    private Logger logger = LoggerFactory.getLogger(AppTest.class);

    @Autowired
    private UserService userService;
    /**
     * 测试保存用户
     */
    @Test
    public void addUser() {
        User user = User.builder().age(21).name("张海涛").build();
        userService.addUser(user);
        logger.info(user.toString());
    }

    /**
     * 测试通过id获取用户
     */
    @Test
    public void findUserById() {
        User user = userService.getUserById(1L);
        logger.info(user.toString());
    }


    /**
     * 测试通过"name"获取用户
     */
    @Test
    public void findUserByName() {
        User user = userService.findUserByName("张海涛");
        logger.info(user.toString());
    }



    /**
     * 测试通过id移除
     */
    @Test
    public void removeById() {
        userService.removeById(1L);
        User user = userService.getUserById(1L);
        logger.info(user.toString());
    }

    /**
     * 测试通过"id"更新用户
     */
    @Test
    public void updateById() {
        User userUpdate = User.builder().id(2L).age(21).name("张三").build();
        userService.updateById(userUpdate);
        User user = userService.getUserById(2L);
        logger.info(user.toString());
    }
}
