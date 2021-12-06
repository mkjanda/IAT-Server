/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.config;

/**
 *
 * @author michael
 */

import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
@Order(4)
public class ResourceViewResolverConfigurer {
    
    @Bean
    public ViewResolver internalResourceViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(InternalResourceView.class);
        resolver.setOrder(2);
        resolver.setViewNames(new String[]{ "*.html" });
        resolver.setPrefix("/");
        resolver.setCache(false);
        resolver.setContentType("text/html");
        return resolver;
    }
}
