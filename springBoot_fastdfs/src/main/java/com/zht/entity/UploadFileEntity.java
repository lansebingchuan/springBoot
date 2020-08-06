package com.zht.entity;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 上传附件
 * </p>
 *
 * @author zht
 * @since 2020-5-4
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
public class UploadFileEntity{

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 对象id
     */
    private String path;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件后缀名
     */
    private String suffix;

    /**
     * 文件大小
     */
    private String size;

    /**
     * 文件大小(byte)
     */
    private Long sizeb;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    //**********************************START手动添加的字段请写到此文件区域********************************************//


    //**********************************END手动添加的字段请写到此文件区域**********************************************//


}
