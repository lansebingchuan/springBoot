package com.zht.entity;

import cn.hutool.core.date.DatePattern;
import com.zht.annotation.FormatDateTime;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhanghaitao
 * @date 2020/3/20 0020
 */
@Data
@Builder
public class User {

    private String name;

    private LocalDateTime createTime;

    @FormatDateTime(ref = "createTime", format = DatePattern.CHINESE_DATE_PATTERN)
    private String formatCreateTime;

}
