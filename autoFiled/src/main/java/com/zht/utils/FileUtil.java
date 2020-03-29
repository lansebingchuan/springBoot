package com.zht.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author zhanghaitao
 * @date 2020/3/26 0026
 */
public class FileUtil {

    /**
     * 获取文件md5值(多个文件已“-”分割)
     * @param resources
     * @return
     * @throws IOException
     */
    public static String getFileMd5(Resource[] resources) throws IOException {
        List<String> stringList = new ArrayList<>();
        for (Resource resource:resources) {
            stringList.add(SecureUtil.md5(resource.getInputStream()));
        }
        if (!stringList.isEmpty()){
            Collections.sort(stringList);
            return StringUtils.join(stringList, "-");
        }
        return null;
    }
}
