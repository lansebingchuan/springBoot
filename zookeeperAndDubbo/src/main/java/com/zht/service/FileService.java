package com.zht.service;

import com.zht.dto.UploadFileDTO;

import java.io.IOException;

/**
 * 文件服务类
 *
 * @author ZHT
 */
public interface FileService {

    /**
     * 保存文件
     *
     * @param fileBytes 文件byte数据
     * @param fileName 文件名
     * @param fileType 文件类型
     * @return ObjectId mongodb文件id
     */
    String saveFile(byte[] fileBytes,String fileName, String fileType);

    /**
     * 根据id获取文件数据
     *
     * @param objectId mongodb文件id
     * @return 文件数据
     */
    UploadFileDTO getFileData(String objectId) throws IOException;
}
