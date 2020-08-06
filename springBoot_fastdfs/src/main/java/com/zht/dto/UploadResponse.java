package com.zht.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文件上传结果
 * </p>
 *
 * @author zht
 * @since 2019-10-14
 */
@Data
@Accessors(chain = true)
public class UploadResponse {
    /**
     * 成功
     */
    public static int OK = 0;

    /**
     * 失败
     */
    public static int ERROR = -1;

    /**
     * 结果
     */
    private int code;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 主键id
     */
    private long id;

    /**
     * fastdfs 文件路径
     */
    private String path;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小
     */
    private String fileSize;

    public UploadResponse(String errorMsg) {
        this.code = ERROR;
        this.errorMsg = errorMsg;
    }

    public UploadResponse(long id, String path, String fileName, String fileSize) {
        this.code = OK;
        this.id = id;
        this.path = path;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }
}
