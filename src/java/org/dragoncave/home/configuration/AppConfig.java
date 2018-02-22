/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.configuration;

/**
 *
 * @author Rider1
 */
/*
import org.dragoncave.home.security.DCSercurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
*/
import org.dragoncave.home.models.User;
import org.dragoncave.home.service.RandID;
import org.dragoncave.home.security.DCSercurityConfig;
import org.dragoncave.home.message.CaveMessages;
import org.dragoncave.home.comp.DCCart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.dragoncave.home")
@Import ({DCSercurityConfig.class})

public class AppConfig extends WebMvcConfigurerAdapter{
    
    /**
     * Configure ResourceHandlers to serve static resources like CSS/ Javascript etc...
     * @param registry
    */ 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
    /**
     * Configure MultipartResolver
     */
    //@Bean(name="multipartResolver")
    @Bean(name="filterMultipartResolver") // for Spring security
    public CommonsMultipartResolver getResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(5242880);

        return resolver;
    }
    /**
     * Configure ViewResolvers to deliver preferred views.
    
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    */
    /**
     * Configure ViewResolvers to deliver preferred views.
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
 
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("welcome");
    }
    
    /*
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }*/
    
    @Bean(name="theUser")
    public User theUser(){
        User u = new User();
        return u;
    }
    
    @Bean(name="randGen")
    public RandID randGen(){
        RandID r= new RandID();
        return r;
    }
    @Bean(name="caveMessage")
    public CaveMessages caveMessage(){
        return new CaveMessages();
    }
    
    @Bean(name="cart")
    public DCCart DCCart(){
        return new DCCart();
    }
}   
    
    /**
     * Thymeleaf integration below this
     * will need to include thymeleaf.jar (thymeleaf-3.0.2.RELEASE.jar) 
     * and thymeleaf-Spring4.jar  thymeleaf-spring4-3.0.2.RELEASE.jar)
     */
/*    
    @Configuration
    @EnableWebMvc
    @ComponentScan(basePackages = "org.dragoncave.home")
    public class ThymeleafConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

      private ApplicationContext applicationContext;

      public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
      }

      @Bean
      public ViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
      }

      @Bean
      public TemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(templateResolver());
        return engine;
      }

      private ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
      }
    }
*/
