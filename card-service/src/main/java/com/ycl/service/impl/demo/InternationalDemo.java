package com.ycl.service.impl.demo;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author : YangChunLong
 * @date : Created in 2020/4/9 19:46
 * @description: 国际化 demo
 * @modified By:
 * @version: :
 */
public class InternationalDemo extends ReloadableResourceBundleMessageSource {
    public static void main(String[] args) {
        Locale locale = new Locale("zh","CN");
        ResourceBundle rbEn = ResourceBundle.getBundle("com/ycl/demo/resource",Locale.US);
        ResourceBundle rbZh = ResourceBundle.getBundle("com/ycl/demo/resource",locale);
        System.out.println("en : "+rbEn.getString("greeting.common"));
        System.out.println("zh : "+rbZh.getString("greeting.common"));
    }
}
