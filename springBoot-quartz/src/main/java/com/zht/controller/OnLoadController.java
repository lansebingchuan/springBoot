package com.zht.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author zht
 * @create 2019-09-06 15:45
 */
@Controller

public class OnLoadController {

    @RequestMapping("/onLoadFile")
    public void onLoadFile(String fileName , HttpServletResponse response) throws FileNotFoundException {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        String filePath = path.getAbsolutePath()+"/static/upload/"+fileName;
        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            byte[] i = new byte[1024];
            while ((inputStream.read(i)) != -1){
                outputStream.write(i, 0, i.length);
                outputStream.flush();//刷新缓存
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
