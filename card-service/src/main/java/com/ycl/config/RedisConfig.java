package com.ycl.config;

import org.springframework.stereotype.Component;

/**
 * @author : YangChunLong
 * @date : Created in 2020/3/27 18:06
 * @description: redis 服务相关配置类
 * @modified By:
 * @version: :
 */
@Component
public class RedisConfig {
    String type; // 类型
    String downloadUrl; // 配置文件下载地址
    String serverUrl;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
