package com.zht.controller;

import com.zht.dto.ApiResult;
import com.zht.dto.UploadFileDTO;
import com.zht.service.FileService;
import com.zht.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 文件控制器
 *
 * @author ZHT
 */
@Controller
@RequestMapping("/file")
public class FileController {


    @Autowired
    private FileService fileService;

    @RequestMapping("/upFilePage")
    public String upFilePage(){
        return "upFileTest";
    }

    @ResponseBody
    @RequestMapping("/upFile")
    public ApiResult upFile(@RequestParam MultipartFile file) throws IOException {

        //原文件名
        String fileName = file.getOriginalFilename();

        //文件后缀
        String suffix = FileUtils.getSuffix(fileName);

        String fileType = file.getContentType();

        String objectId = fileService.saveFile(file.getBytes(), fileName, fileType);
        return ApiResult.ok(objectId);
    }

    @RequestMapping("/downFile")
    public void downFile(@RequestParam String objectId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        //获取文件数据
        UploadFileDTO fileDTO = fileService.getFileData(objectId);
        if (fileDTO == null) {
            response.setStatus(404);
            return;
        }
        //处理文件名称乱码
        String filename = UUID.randomUUID().toString();
        if (StringUtils.isNotBlank(fileDTO.getName())) {
            filename = fileDTO.getName();
        }
        //IE
        if (StringUtils.containsAny(request.getHeader("user-agent").toLowerCase(), "msie", "like gecko")) {
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            filename = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        //输出文件内容
        FileCopyUtils.copy(new ByteArrayInputStream(fileDTO.getFileByte()), response.getOutputStream());
    }
}
