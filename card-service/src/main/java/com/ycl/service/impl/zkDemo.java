package com.ycl.service.impl;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author : YangChunLong
 * @date : Created in 2020/3/23 18:11
 * @description: zk api 操作示例
 * @modified By:
 * @version: :
 */
public class zkDemo {
    public CuratorFramework client = null;
    private static String zkUrl = "127.0.0.1:2181";

    public zkDemo (){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry()
    }
}
