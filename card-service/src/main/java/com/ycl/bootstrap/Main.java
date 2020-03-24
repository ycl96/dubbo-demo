package com.ycl.bootstrap;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : YangChunLong
 * @date : Created in 2020/3/17 17:39
 * @description: 服务启动类
 * @modified By:
 * @version: :
 */
public class Main {
    private static AbstractApplicationContext context;

    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
        context.start();
        System.out.println("start success...");
        while (true){
            try{
                Thread.sleep(Integer.MAX_VALUE);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
