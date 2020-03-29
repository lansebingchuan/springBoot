package com.zht.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author zht
 * @create 2019-09-06 11:08
 */
@Controller
public class UploadController1 {

    @RequestMapping("/upload1")
    @ResponseBody
    public String upload(HttpServletRequest request, HttpSession session) throws FileNotFoundException {
        MultipartHttpServletRequest request1;
        try {
            request1 = (MultipartHttpServletRequest)request;
        }catch (Exception e){
            return "上传文件类型错误！"+e.getMessage();
        }
        List<MultipartFile> multipartFiles =request1.getFiles("file");
        MultipartFile file;
        int count = 0;//文件次数
        for (int i=0 ; i< multipartFiles.size() ; i++){
            file = multipartFiles.get(i);
            String originalFilename = file.getOriginalFilename();
            String houzhui = originalFilename.substring(originalFilename.lastIndexOf("."));
            if (!houzhui.equals(".jpg")){
                return "上传文件失败，不支持的文件类型--"+houzhui;
            }
            System.out.println("后缀："+houzhui);
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            String filename = UUID.randomUUID().toString();
            String filePath = path.getAbsolutePath()+"/static/upload/"+filename+houzhui;
            System.out.println("存储文件："+filePath);
            File file1 = new File(filePath);
            try {
                file.transferTo(file1);
            } catch (IOException e) {
                e.printStackTrace();
                return "上传文件失败，文件数量："+count;
            }
            count++;
        }
        return "上传文件成功，文件数量："+count;
    }
}
