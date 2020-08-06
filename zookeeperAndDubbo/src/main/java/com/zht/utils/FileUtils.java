package com.zht.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 文件工具类
 *
 * @author ZHT
 */
public class FileUtils extends FileUtil {

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名称
     * @return 文件扩展名
     */
    public static String getSuffix(String fileName) {
        String suffix = "";
        if (StringUtils.isNotBlank(fileName)) {
            int index = fileName.lastIndexOf('.');
            if (index > 0 && index < fileName.length() - 1) {
                suffix = fileName.substring(index + 1).toLowerCase();
            }
        }
        return suffix;
    }

    /**
     * 获取不含后缀的文件名称
     *
     * @param fileName 文件名称
     * @return 不含后缀的文件名称
     */
    public static String getPrefix(String fileName) {
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, splitIndex);
    }

    /**
     * 获取文件格式化大小
     *
     * @param size 文件大小
     * @return 格式化后的文件大小
     */
    public static String getFormatSize(long size) {
        long rest = 0;
        if (size < 1024) {
            return size + "B";
        } else {
            size /= 1024;
        }

        if (size < 1024) {
            return size + "KB";
        } else {
            rest = size % 1024;
            size /= 1024;
        }

        if (size < 1024) {
            size = size * 100;
            return size / 100 + "." + rest * 100 / 1024 % 100 + "MB";
        } else {
            size = size * 100 / 1024;
            return size / 100 + "." + size % 100 + "GB";
        }
    }

    /**
     * 计算资源文件Md5值（多个文件的Md5值排序后以“-”拼接）
     *
     * @param resources 配置文件
     * @return 资源文件Md5值
     */
    public static String calResourcesMd5(Resource[] resources) throws IOException {
        List<String> md5List = new ArrayList<>();
        for (Resource resource : resources) {
            md5List.add(SecureUtil.md5(resource.getInputStream()));
        }
        if (!md5List.isEmpty()) {
            //排序
            Collections.sort(md5List);
            //拼接
            return StringUtils.join(md5List, "-");
        }
        return null;
    }
}
