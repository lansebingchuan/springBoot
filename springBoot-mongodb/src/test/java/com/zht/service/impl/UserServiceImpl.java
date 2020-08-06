package com.zht.service.impl;

import com.zht.entity.User;
import com.zht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addUser(User user) {
        User save = mongoTemplate.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        User user = mongoTemplate.findById(userId, User.class);
        return user;
    }

    @Override
    public User findUserByName(String name) {
        Query query = Query.query(Criteria.where("name").is(name).and("id").is(1));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public void removeById(long l) {
        Query query = Query.query(Criteria.where("id").is(1));
        mongoTemplate.remove(query, User.class);
    }

    @Override
    public void updateById(User user) {
        Query query = Query.query(Criteria.where("id").is(2));
        Update update = new Update();
        update.set("age", user.getAge());
        update.set("name", user.getName());
        mongoTemplate.updateFirst(query, update, User.class);
    }
}
