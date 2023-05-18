package com.will.homestay.configer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileConfig implements WebMvcConfigurer {

    @Value("${file.staticAcessPath}")//读取yaml配置文件中的内容
    private String staticAcessPath;
    @Value("${file.uploadFileFolder}")
    private String uploadFileFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticAcessPath).addResourceLocations("file:"+uploadFileFolder);
    }
}
