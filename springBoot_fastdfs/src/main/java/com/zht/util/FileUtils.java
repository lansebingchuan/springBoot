package com.zht.util;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 文件工具类
 * </p>
 *
 * @author zht
 * @since 2019-10-17
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
            return String.valueOf(size) + "B";
        } else {
            size /= 1024;
        }

        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            rest = size % 1024;
            size /= 1024;
        }

        if (size < 1024) {
            size = size * 100;
            return String.valueOf((size / 100)) + "." + String.valueOf((rest * 100 / 1024 % 100)) + "MB";
        } else {
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "GB";
        }
    }
}
