package it.discovery.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private List<HandlerInterceptor> interceptors;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        interceptors.forEach(registry::addInterceptor);
//    }
}
