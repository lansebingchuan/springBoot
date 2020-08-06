package com.zht.mongo;

import cn.hutool.core.lang.Assert;
import com.zht.annotation.MongoGeneratedValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * <p>
 * mongo 事件监听器
 * </p>
 *
 * @author wanghongshuang
 * @since 2019-07-22
 */
@Component
public class SaveEventListener extends AbstractMongoEventListener<Object> {

    @Autowired(required = false)
    private MongoTemplate mongoTemplate;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), (Field field) -> {
            ReflectionUtils.makeAccessible(field);
            if (field.isAnnotationPresent(MongoGeneratedValue.class)) {
                //设置自增ID
                Document document = source.getClass().getAnnotation(Document.class);
                if (document == null) {
                    return;
                }
                String collName = StringUtils.isNotBlank(document.collection()) ? document.collection() : document.value();
                field.set(source, getNextId(StringUtils.isNotBlank(collName) ? collName : source.getClass().getSimpleName()));
            }
        });
    }

    /**
     * 获取下一个自增ID
     *
     * @param collName 集合名称
     * @return 自增ID
     */
    private long getNextId(String collName) {
        Query query = new Query(Criteria.where("collName").is(collName));
        Update update = new Update();
        update.inc("seqId", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        SequenceId seqId = mongoTemplate.findAndModify(query, update, options, SequenceId.class);
        Assert.notNull(seqId);
        return seqId.getSeqId();
    }

}
