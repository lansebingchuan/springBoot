package com.zht.dubbo.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 远程方法调用日志记录
 * </p>
 *
 * @author wanghongshuang
 * @since 2020-01-09
 */
@Activate(group = {CommonConstants.PROVIDER, CommonConstants.CONSUMER})
public class LogFilter implements Filter {

    /**
     * 日志记录
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Object[] args = invocation.getArguments();
        ObjectMapper om = new ObjectMapper();
        String argsJson;
        try {
            argsJson = om.writeValueAsString(args);
        } catch (JsonProcessingException e) {
            throw new RpcException(e);
        }
        long start = System.currentTimeMillis();
        try {
            return invoker.invoke(invocation);
        } finally {
            long spend = (System.currentTimeMillis() - start);
            int port = RpcContext.getContext().getRemotePort();
            String host = RpcContext.getContext().getRemoteHost();
            //记录日志
            logger.info("Dubbo远程方法调用: service[ {} ], method[ {} ], args[ {} ], spendTime[ {} ms ], clientIp[ {} ], clientPort[ {} ].",
                    invoker.getInterface().getSimpleName(),
                    invocation.getMethodName(), argsJson, spend, host,
                    port);
        }
    }
}
