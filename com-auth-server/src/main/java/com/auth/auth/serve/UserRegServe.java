package com.auth.auth.serve;

import com.alibaba.fastjson.JSONObject;
import com.common.common.dto.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p> 用户注册服务 </p>
 *
 * @author: ZHT
 * @create: 2021-07-12 12:20
 **/
@FeignClient("springboot-feign")
public interface UserRegServe {
    
    @PostMapping("/user-reg/userReg")
    R userReg(JSONObject user);

    @PostMapping("/user-reg/userReg")
    R login(JSONObject user);
}
