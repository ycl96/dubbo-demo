package com.ycl.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.ImportResource;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author : YangChunLong
 * @date : Created in 2020/3/17 17:53
 * @description: 应用启动类
 * @modified By:
 * @version: :
 */
@ImportResource(value = "classpath:application-context.xml")
@SpringBootApplication
public class ApplicationStart implements ServletContextInitializer {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApplicationStart.class);
        application.run(args);
        System.out.println("application start success...");
    }

    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("pass_prot", "25010");
    }
}
