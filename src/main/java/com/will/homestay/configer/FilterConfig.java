package com.will.homestay.configer;

import com.will.homestay.filter.SensitiveWordsFilter;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private SensitiveWordsFilter sensitiveWordsFilter;

    @Bean
    public FilterRegistrationBean<SensitiveWordsFilter> sensitiveWordsFilterRegistrationBean() {
        FilterRegistrationBean<SensitiveWordsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(sensitiveWordsFilter);
        registrationBean.addUrlPatterns("/homestay/order-comment/comment");
        registrationBean.setName("sensitiveWordsFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
