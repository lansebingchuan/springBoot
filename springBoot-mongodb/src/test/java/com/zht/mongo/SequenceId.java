package com.zht.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * <p>
 * mongo 自增序列
 * </p>
 *
 * @author zht
 * @since 2020-6-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Document("sequence")
public class SequenceId {

    @Id
    private String id;

    /**
     * 序列
     */
    @Field("seq_id")
    private long seqId;

    /**
     * 集合名称
     */
    @Field("coll_name")
    private String collName;

}
