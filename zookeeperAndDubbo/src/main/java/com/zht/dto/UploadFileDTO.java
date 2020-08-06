package com.zht.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件数据dto
 *
 * @author ZHT
 */
@Data
public class UploadFileDTO implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 对象id
     */
    private String objectId;

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

    /**
     * 文件类型
     */
    private String contentType;

    /**
     * 文件字节数据
     */
    private byte[] fileByte;
}
