package com.zht.redisTest;

import com.zht.util.rides.RedisUtils;

/**
 * @author zht
 * @create 2019-09-07 9:18
 */
public class RedisThread {


    public static void main(String[] args) {
        Thread[] threads = new Thread[10000];
        for (int i = 0 ; i< threads.length; i++){
            threads[i] = new Thread(() -> {
                boolean lock = RedisUtils.tryGetDistributedLock("lock", "1", 5);
                if (lock){
                    String s= (String)RedisUtils.getObjectByString("redisCount");
                    System.out.println("出票成功,你是第："+s+"票");
                    int i1 = Integer.parseInt(s);
                    if (i1 == 0){
                        System.out.println("没有票了");
                    }else {
                        RedisUtils.setObjectByString("redisCount" , String.valueOf(--i1));
                    }
                    RedisUtils.releaseDistributedLock("lock", "1");//释放锁
                }else {
                    System.out.println("抢票失败");
                }
            });
        }
        for (int i = 0 ; i< threads.length; i++){
            threads[i].start();
        }
    }


}
