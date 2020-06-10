package com.zht.dubbo.filter;

import com.zht.cons.RpcAttachmentKeys;
import com.zht.context.CurrentUserHolder;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 消费者rpc请求前拦截器
 * @author ZHT
 */
@Activate(group = {CommonConstants.CONSUMER})
public class ConsumerContextFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        //传递的参数map
        Map<String, String> attachments = new HashMap<>(1);
        if (CurrentUserHolder.getUser() != null) {
            attachments.put(RpcAttachmentKeys.CURRENT_USER_ID, String.valueOf(CurrentUserHolder.getUserId()));
        }
        if (!attachments.isEmpty()) {
            RpcContext.getContext().setAttachments(attachments);
        }
        return invoker.invoke(invocation);
    }

}
