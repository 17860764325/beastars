package com.lhrlyn.cn.lhrlynadmin.user.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lihaoran
 * @date 2023/3/30 00:02
 */
@Configuration
public class PictureConversionConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
         /**
           * 资源映射路径
           * addResourceHandler：访问映射路径
           * addResourceLocations：资源绝对路径
          */
        registry.addResourceHandler("/doctor/**")
                .addResourceLocations("file:///Users/emlynchen/Pictures/leigexi/");
    }
}
