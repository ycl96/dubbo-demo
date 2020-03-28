package com.ycl.service.impl.demo;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @author : YangChunLong
 * @date : Created in 2020/3/26 18:31
 * @description: 自定义 zk 监听结果处理逻辑
 * @modified By:
 * @version: :
 */
public class MyZkWatcher implements Watcher {
    @Override
    public void process(WatchedEvent event) {
        System.out.println("触发watcher，节点路径为：" + event.getPath());
    }
}
