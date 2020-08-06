package com.zht.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadFileStream;
import com.github.tobato.fastdfs.domain.upload.FastFile;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.zht.dto.UploadResponse;
import com.zht.entity.UploadFileEntity;
import com.zht.util.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author ZHT
 */
@RestController
public class FileController {

    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    /**
     * 允许上传的文件后缀
     */
    private final static String[] ALLOW_FILE_SUFFIX = {"gif", "jpg", "jpeg", "png", "bmp", "txt", "doc", "docx", "xls", "xlsx", "pdf",
            "rar", "zip", "wmv", "wma", "mp4", "avi", "webm", "swf"};

    @Autowired
    FastFileStorageClient fastFileStorageClient;


    @RequestMapping("/upFile")
    public UploadResponse upFile(@RequestParam MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        String originalFileName = file.getOriginalFilename();
        String fileFormatSize = FileUtils.getFormatSize(file.getSize());
        long size = file.getSize();
        String suffix = FileUtils.getSuffix(file.getOriginalFilename());
        //文件后缀过滤
        if (!ArrayUtils.contains(ALLOW_FILE_SUFFIX, suffix)) {
            return new UploadResponse("对不起，不允许上传后缀为." + suffix + "的文件");
        }
        StorePath storePath = fastFileStorageClient.uploadFile("group1", inputStream, size, suffix);
        //附件信息存入数据库
        UploadFileEntity uploadFileEntity = UploadFileEntity.builder()
                .id(1L).path(storePath.getPath())
                .name(originalFileName)
                .suffix("." + suffix)
                .sizeb(file.getSize())
                .size(fileFormatSize).build();
        //保存数据库
        //uploadFileService.save(uploadFileEntity);
        logger.info("上传文件：文件名："+uploadFileEntity.getName()+"   文件路径："+uploadFileEntity.getPath());
        return new UploadResponse(uploadFileEntity.getId(), uploadFileEntity.getPath(), originalFileName, fileFormatSize);
    }

    @RequestMapping("/downFile")
    public void downFile(HttpServletRequest request, HttpServletResponse response, @RequestParam String path) throws IOException {
       /* //判断文件是否存在
        UploadFileEntity uploadFileEntity = uploadFileService.selectOneByPath(path);
        //不存在文件
        if (ObjectUtil.isNull(uploadFileEntity)){
            response.setStatus(404);
            return;
        }*/
        UploadFileEntity uploadFileEntity = new UploadFileEntity();
        String fileName = UUID.randomUUID().toString(true);
        if (StringUtils.isNotBlank(uploadFileEntity.getName())){
            fileName = uploadFileEntity.getName();
        }
        //IE
        if (StringUtils.containsAny(request.getHeader("user-agent").toLowerCase(), "msie", "like gecko")) {
            fileName = URLEncoder.encode(fileName, "utf-8");
        } else {
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        fastFileStorageClient.downloadFile("group1", path, new DownloadFileStream(response.getOutputStream()));
        //输出文件内容
        //FileCopyUtils.copy(resource.getInputStream(), response.getOutputStream());
    }

}
