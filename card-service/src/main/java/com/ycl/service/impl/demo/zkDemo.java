package com.ycl.service.impl.demo;

import com.google.gson.Gson;
import com.ycl.config.RedisConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ZooDefs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

import java.util.List;

import static org.apache.zookeeper.CreateMode.PERSISTENT;

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
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,5);
        client = CuratorFrameworkFactory.builder()
                .connectString(zkUrl)
                .sessionTimeoutMs(10000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
    }

    /**
     * @author     : YangChunLong
     * @date       : Created in 2020/3/26 11:50
     * @description: 关闭zk链接
     * @modified By:
     * @Param:
     * @return     : void
     */
    public void closeZk (){
        if (null != client){
            this.client.close();
        }
    }

    /**
     * @author     : YangChunLong
     * @date       : Created in 2020/3/26 14:27
     * @description: 创建节点
     * @modified By:
     * @Param: nodePath
     * @Param: data
     * @return     : boolean
     */
    public boolean createNode (String nodePath, String data){
        if (StringUtils.isBlank(nodePath) || StringUtils.isBlank(data)){
            return false;
        }
        if (!nodePath.startsWith("/")){
            nodePath = "/"+nodePath;
        }
        String result = "";
        try {
            result = this.client.create().creatingParentsIfNeeded()
                    .withMode(PERSISTENT)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(nodePath,data.getBytes());
            System.out.println(result + "节点，创建成功...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodePath.equals(nodePath);
    }

    /**
     * @author     : YangChunLong
     * @date       : Created in 2020/3/26 18:35
     * @description: 单次 监听
     * @modified By:
     * @Param: nodePath
     * @return     : void
     */
    public void watcherSingle (String nodePath){
        if (!nodePath.startsWith("/")){
            nodePath = "/"+nodePath;
        }
        try {
            this.client.getData().usingWatcher(new MyZkWatcher()).forPath(nodePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author     : YangChunLong
     * @date       : Created in 2020/3/27 09:46
     * @description: 多次监听
     * @modified By:
     * @Param: nodePath
     * @return     : void
     */
    public void watcherMultiple (String nodePath){
        nodePath = nodePath.startsWith("/")?nodePath:"/"+nodePath;
        final NodeCache nodeCache = new NodeCache(this.client,nodePath);
        try {
            nodeCache.start(true);
            if (nodeCache.getCurrentData() != null){
                System.out.println("节点初始化数据为：" + new String(nodeCache.getCurrentData().getData()));
            }else {
                System.out.println("节点初始化数据为空...");
            }
            nodeCache.getListenable().addListener(new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    if (null != nodeCache.getCurrentData()){
                        String data = new String(nodeCache.getCurrentData().getData());
                        System.out.println(nodeCache.getCurrentData().getPath()+"节点的数据发生变化，最新的数据为："+data);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author     : YangChunLong
     * @date       : Created in 2020/3/27 16:23
     * @description: 监听 子节点（多次监听）
     * @modified By:
     * @Param: nodePath
     * @return     : void
     */
    public void watcherChildrenMuliteple (String nodePath){
        final PathChildrenCache childrenCache = new PathChildrenCache(this.client,nodePath,true);
        try {
            childrenCache.start();
            List<ChildData> childDataList = childrenCache.getCurrentData();
            System.out.println("当前节点的子节点详细数据列表：");
            for (ChildData childData : childDataList) {
                System.out.println("\t* 子节点路径：" + new String(childData.getPath()) + "，该节点的数据为：" + new String(childData.getData()));
            }
            // 添加事件监听器
            childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
                    // 通过判断event type的方式来实现不同事件的触发
                    if (event.getType().equals(PathChildrenCacheEvent.Type.INITIALIZED)) {  // 子节点初始化时触发
                        System.out.println("\n--------------\n");
                        System.out.println("子节点初始化成功");
                    } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {  // 添加子节点时触发
                        System.out.println("\n--------------\n");
                        System.out.print("子节点：" + event.getData().getPath() + " 添加成功，");
                        System.out.println("该子节点的数据为：" + new String(event.getData().getData()));
                    } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {  // 删除子节点时触发
                        System.out.println("\n--------------\n");
                        System.out.println("子节点：" + event.getData().getPath() + " 删除成功");
                    } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {  // 修改子节点数据时触发
                        System.out.println("\n--------------\n");
                        System.out.print("子节点：" + event.getData().getPath() + " 数据更新成功，");
                        System.out.println("子节点：" + event.getData().getPath() + " 新的数据为：" + new String(event.getData().getData()));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Autowired
    private ApplicationContext context;

    private static String CONFIG_NODE_PATH = "/config";
    private static String CONFIG_REDIS_NODE_PATH = "/config/redis";
    public void getRedisConfig (){
        final Gson gson = new Gson();
        final PathChildrenCache childrenCache = new PathChildrenCache(this.client,CONFIG_NODE_PATH,true);
        try {
            childrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
            List<ChildData> childDataList = childrenCache.getCurrentData();
            System.out.println("当前节点的子节点详细数据列表：");
            for (ChildData childData : childDataList) {
                System.out.println("\t* 子节点路径：" + new String(childData.getPath()) + "，该节点的数据为：" + new String(childData.getData()));
            }
            childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                    PathChildrenCacheEvent.Type eventType = event.getType();
                    String currentPath = event.getData().getPath();
                    String data = event.getData().getData().length>0?String.valueOf(event.getData().getData()):"";
                    // 监听的节点类型（更新）
                    if (eventType.equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)){
                        // 不同的子节点可以进行不同的业务操作，判断当前监听到的子节点 路径
                        if (currentPath.equals(CONFIG_REDIS_NODE_PATH)){
                            System.out.println("节点" + CONFIG_REDIS_NODE_PATH + "的数据为: " + data);
                            RedisConfig redisConfig = null;
                            if (StringUtils.isNotBlank(data)){
                                redisConfig = gson.fromJson(data,RedisConfig.class);
                                String type = redisConfig.getType();
                                String downloadUrl = redisConfig.getDownloadUrl();
                                String serverUrl = redisConfig.getServerUrl();
                                // 根据不同类型，可执行不同的业务逻辑
                                switch (type){
                                    case "add":
                                        // TODO: 2020/3/28 动态 修改容器中的bean property
                                        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)context.getAutowireCapableBeanFactory();
                                        BeanDefinition beanDefinition = new ChildBeanDefinition("redisConfig");
                                        beanDefinition.setAttribute("serverUrl",serverUrl);
                                        beanDefinition.setAttribute("type",type);
                                        beanDefinition.setAttribute("downloadUrl",downloadUrl);
                                        defaultListableBeanFactory.registerBeanDefinition("redisConfig",beanDefinition);
                                        break;
                                    case "update":
                                        // TODO: 2020/3/27 把文件下载到指定目录
                                        break;
                                    case "delete":
                                        // TODO: 2020/3/27 把文件下载到指定目录
                                        break;
                                        default:
                                            // TODO: 2020/3/27 把文件下载到指定目录
                                            break;
                                }
                                // TODO: 2020/3/27 可视情况重启服务
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        zkDemo zkDemo = new zkDemo();
        boolean isZkStarted = zkDemo.client.isStarted();
        System.out.println("当前客户端的状态：" + (isZkStarted ? "连接中..." : "已关闭..."));
        zkDemo.createNode("/demo","1234");
        zkDemo.closeZk();
        isZkStarted = zkDemo.client.isStarted();
        System.out.println("当前客户端的状态：" + (isZkStarted ? "连接中..." : "已关闭..."));
    }
}
