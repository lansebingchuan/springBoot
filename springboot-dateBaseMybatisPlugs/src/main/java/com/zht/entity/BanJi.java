package com.zht.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author ZHT
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("banJI")
public class BanJi {
    @TableId(value = "id", type = IdType.AUTO)
    private  Integer id;

    @TableField("zhuanYe")
    private String zhuanYe;

    @TableField("banJi")
    private String banJi;
}
