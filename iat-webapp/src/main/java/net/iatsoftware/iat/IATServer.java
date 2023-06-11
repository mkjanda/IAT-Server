/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat;

import net.iatsoftware.iat.admin.AdminViewResolver;
import net.iatsoftware.iat.config.IATDeployerFactory;
import net.iatsoftware.iat.deployment.DefaultIATDeployer;
import net.iatsoftware.iat.deployment.DefaultIATRedeployer;
import net.iatsoftware.iat.deployment.IATDeployer;
import net.iatsoftware.iat.deployment.IATRedeployer;
import net.iatsoftware.iat.events.WebSocketDataReceived;
import net.iatsoftware.iat.messaging.Envelope;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.services.WebSocketService;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.crypto.SecretKeyFactory;
import javax.inject.Inject;
import javax.inject.Named;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import javax.sql.DataSource;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Michael Janda
 */
@Order(1)
@SpringBootApplication
@ConfigurationProperties
@EnableAsync(order = 1)
@EnableTransactionManagement(order = 2)
@EnableWebSocket
@EnableScheduling
@EnableAutoConfiguration(exclude = { ThymeleafAutoConfiguration.class })
@EnableConfigurationProperties({ IATServer.class })
@PropertySources({ @PropertySource("application.properties"), @PropertySource("datasource.properties"),
        @PropertySource("iat.webapp.properties") })
public class IATServer implements SchedulingConfigurer {

    private static final Logger logger = LogManager.getLogger();

    @Inject
    Environment enviornment;
    @Inject
    ApplicationContext applicationContext;

    @Value("${server.port}")
    private int serverPort;

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");        
        SpringApplication.run(net.iatsoftware.iat.IATServer.class);
    }

    private static final Logger log = LogManager.getLogger("critical");

    @ConfigurationProperties(prefix = "datasource")
    @Bean
    public DataSource dataSource() {
        return new MysqlConnectionPoolDataSource();
    }

    @Primary
    @ConfigurationProperties(prefix = "iat.webapp")
    @Bean("ServerConfiguration")
    public Properties notServerConfiguration() {
        return new Properties();
    }

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.setPort(serverPort);
        tomcat.setContextPath("");
        return tomcat;
    }

    @Bean
    public WebSocketHandler webSocketHandler() {

        class MyWebSocketHandler extends TextWebSocketHandler {

            private final ConcurrentHashMap<String, List<String>> partialTransmissions = new ConcurrentHashMap<>();

            @Inject
            Unmarshaller unmarshaller;
            @Inject
            WebSocketService webSocketService;
            @Inject
            ApplicationEventPublisher publisher;

            @Override
            public void afterConnectionEstablished(WebSocketSession sess) {
                webSocketService.registerWebSocket(sess);
            }

            @Override
            public boolean supportsPartialMessages() {
                return true;
            }

            protected Envelope unmarshalTransmission(String trans) throws java.io.IOException {
                StringReader sReader = new StringReader(trans);
                StreamSource sSource = new StreamSource(sReader);
                return (Envelope) unmarshaller.unmarshal(sSource);
            }

            @Override
            protected void handleTextMessage(WebSocketSession sess, TextMessage msg) {
                try {

                    if (!msg.isLast()) {
                        if (partialTransmissions.containsKey(sess.getId())) {
                            partialTransmissions.get(sess.getId()).add(msg.getPayload());
                        } else {
                            List<String> trans = new ArrayList<>();
                            trans.add(msg.getPayload());
                            partialTransmissions.put(sess.getId(), trans);
                        }
                    } else if (!partialTransmissions.containsKey(sess.getId())) {
                        publisher.publishEvent(
                                new WebSocketDataReceived(sess.getId(), unmarshalTransmission(msg.getPayload())));
                    } else {
                        List<String> fullTrans = partialTransmissions.get(sess.getId());
                        fullTrans.add(msg.getPayload());
                        publisher.publishEvent(new WebSocketDataReceived(sess.getId(),
                                unmarshalTransmission(fullTrans.stream().reduce("", (s1, s2) -> s1.concat(s2)))));
                        fullTrans.clear();
                    }
                } catch (Exception ex) {
                    logger.error("Error receiving web socket transmission", ex);
                }
            }

            @Override
            public void afterConnectionClosed(WebSocketSession sess, CloseStatus status) {
                if (sess.getUri().getPath().equals("/IAT/DataTransaction"))
                    webSocketService.unregisterWebSocket(sess.getId());
                partialTransmissions.remove(sess.getId());
            }
        }
        return new MyWebSocketHandler();
    }

    @Bean
    WebSocketConfigurer webSocketConfigurer() {
        return registry -> registry.addHandler(webSocketHandler(), "/IAT/DataTransaction");
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        return mapper;
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan("net.iatsoftware.iat");
        Map<String, Boolean> properties = new HashMap<>();
        properties.put(jakarta.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return jaxb2Marshaller;
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Inject
            Marshaller marshaller;
            @Inject
            Unmarshaller unmarshaller;
            @Inject
            ObjectMapper objectMapper;
            @Inject
            SpringValidatorAdapter validator;
            @Inject
            @Named("TaskScheduler")
            ThreadPoolTaskScheduler taskScheduler;
            @Inject
            IATRepositoryManager repositoryManager;

            @Bean
            public ViewResolver adminViewResolver() {
                AdminViewResolver adminViewResolver = new AdminViewResolver();
                adminViewResolver.setRepositoryManager(repositoryManager);
                adminViewResolver.setOrder(1);
                return adminViewResolver;
            }

            @Bean
            public ViewResolver thymeleafViewResolver() {
                ThymeleafViewResolver resolver = new ThymeleafViewResolver();
                SpringTemplateEngine engine = new SpringTemplateEngine();
                engine.addTemplateResolver(defaultTemplateResolver());
                resolver.setTemplateEngine(engine);
                resolver.setOrder(2);
                return resolver;
            }

            private ITemplateResolver defaultTemplateResolver() {
                final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
                templateResolver.setResolvablePatterns(Collections.singleton("*"));
                templateResolver.setPrefix("thymeleaf/");
                templateResolver.setSuffix(".html");
                templateResolver.setTemplateMode(TemplateMode.HTML);
                templateResolver.setCharacterEncoding("UTF-8");
                templateResolver.setCacheable(false);
                return templateResolver;
            }

            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new ByteArrayHttpMessageConverter());
                converters.add(new StringHttpMessageConverter());
                converters.add(new FormHttpMessageConverter());
                converters.add(new SourceHttpMessageConverter<>());
                converters.add(new ResourceHttpMessageConverter());

                MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
                xmlConverter.setSupportedMediaTypes(
                        Arrays.asList(new MediaType("application", "xml"), new MediaType("text", "xml")));
                xmlConverter.setMarshaller(this.marshaller);
                xmlConverter.setUnmarshaller(this.unmarshaller);
                converters.add(xmlConverter);
                MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
                jsonConverter.setSupportedMediaTypes(
                        Arrays.asList(new MediaType("application", "json"), new MediaType("text", "json")));
                jsonConverter.setObjectMapper(this.objectMapper);
                converters.add(jsonConverter);
            }

            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                configurer.favorParameter(false).parameterName("mediaType")
                        .ignoreAcceptHeader(false).mediaType("xml", MediaType.APPLICATION_XML)
                        .mediaType("xml", MediaType.TEXT_XML).mediaType("json", MediaType.APPLICATION_JSON)
                        .mediaType("html", MediaType.TEXT_HTML);
            }

            @Override
            public void configureViewResolvers(ViewResolverRegistry registry) {
                registry.viewResolver(adminViewResolver());
                registry.viewResolver(thymeleafViewResolver());
                registry.order(Ordered.HIGHEST_PRECEDENCE);
            }

            @Override
            public Validator getValidator() {
                return this.validator;
            }

            @Override
            public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
                configurer.setTaskExecutor(this.taskScheduler);
            }

            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                /*
                 * registry.addMapping("/**").allowedMethods("GET", "POST",
                 * "HEAD").allowedOrigins("iatsoftware.net",
                 * "www.iatsoftware.net", "127.0.0.1", "192.168.56.101", "100.100.100.101");
                 * registry.addMapping("/**")
                 * Iterable<CorsOrigin> corsOrigins = repositoryManager.getAllCorsOrigins();
                 * final List<String> allDomains = new ArrayList<>();
                 * corsOrigins.forEach(co -> {
                 * allDomains.add(co.getOrigin());
                 * allDomains.add("www." + co.getOrigin());
                 * });
                 * String[] domainNames = new String[allDomains.size()];
                 * allDomains.toArray(domainNames);
                 * CorsRegistration adminRegistration = registry.addMapping("/Admin");
                 * adminRegistration.allowedMethods("GET", "HEAD", "POST");
                 * adminRegistration.allowedOrigins(domainNames);
                 * CorsRegistration ajaxRegistration = registry.addMapping("/Admin/Ajax**");
                 * ajaxRegistration.allowedMethods("GET", "HEAD", "POST");
                 * ajaxRegistration.allowedOrigins(domainNames);
                 * CorsRegistration webJarsRegistration = registry.addMapping("/webjars/**");
                 * webJarsRegistration.allowedMethods("GET", "HEAD", "POST");
                 * webJarsRegistration.allowedOrigins(domainNames);
                 * corsOrigins.forEach(co -> {
                 * CorsRegistration clientRegistration = registry
                 * .addMapping("/Admin/TestResource/" +
                 * Long.toString(co.getClient().getClientId()) + "/**");
                 * clientRegistration.allowedMethods("GET", "HEAD", "POST");
                 * clientRegistration.allowedOrigins(co.getOrigin(), "www." + co.getOrigin());
                 * });
                 */
            }
        };
    }

    @Primary
    @Bean("aesJs")
    public String aesJs() {
        try {
            File aesFile = applicationContext.getResource("classpath:scripts/aes.js").getFile();
            try (InputStreamReader inStream = new InputStreamReader(new FileInputStream(aesFile))) {
                try (BufferedReader aesReader = new BufferedReader(inStream)) {
                    StringBuilder aesBuilder = new StringBuilder();
                    String line = "";
                    while ((line = aesReader.readLine()) != null) {
                        aesBuilder.append(line);
                    }
                    return aesBuilder.toString();
                }
            }
        } catch (java.io.IOException ex) {
            logger.error("Error loading 'aes.js'", ex);
            return null;
        }
    }

    @Bean
    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
        FilterRegistrationBean<ForwardedHeaderFilter> frb = new FilterRegistrationBean<>();
        frb.setFilter(new ForwardedHeaderFilter());
        frb.addUrlPatterns("/**");
        return frb;
    }

    @Primary
    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.schema-generation.database.action", "none");
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(false);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(adapter);
        factory.setDataSource(dataSource());
        factory.setPackagesToScan("net.iatsoftware.iat.entities");
        factory.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
        factory.setValidationMode(ValidationMode.NONE);
        factory.setJpaPropertyMap(properties);
        return factory;
    }

    @Primary
    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

    @Primary
    @Bean("TaskScheduler")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(20);
        scheduler.setErrorHandler(t -> logger.error("Error in asynchronous task", t));
        scheduler.setThreadNamePrefix("task-");
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }

    @Bean
    public SecretKeyFactory desKeyFactory() {
        try {
            return SecretKeyFactory.getInstance("DES");
        } catch (java.security.NoSuchAlgorithmException ex) {
            log.error("Error instantiating DES key factory", ex);
            return null;
        }
    }

    @Bean
    public KeyFactory rsaKeyFactory() {
        try {
            return KeyFactory.getInstance("RSA");
        } catch (java.security.NoSuchAlgorithmException ex) {
            log.error("Error instantiating RSA key factory", ex);
            return null;
        }
    }

    @Primary
    @Bean("ExceptionMessageSource")
    public MessageSource exceptionMessageSource() {
        ReloadableResourceBundleMessageSource msgSource = new ReloadableResourceBundleMessageSource();
        msgSource.setCacheSeconds(-1);
        msgSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        msgSource.setBasename("classpath:OauthExceptionMessages");
        return msgSource;
    }

    @Bean
    @Scope("prototype")
    public IATDeployer iatDeployer(@Nullable Long clientId, @Nullable Long deploymentId, @Nullable Long testId, 
            @Nullable String session) {
        return new DefaultIATDeployer(clientId, deploymentId, testId, session);
    }

    @Bean
    @Scope("prototype")
    public IATRedeployer iatRedeployer(@Nullable Long clientId, @Nullable Long deploymentId, @Nullable Long newTestId,
            @Nullable Long oldTestId, String session) {
        return new DefaultIATRedeployer(clientId, deploymentId, newTestId, oldTestId, session);
    }

    @Bean
    public IATDeployerFactory deployerFactory() {
        return new IATDeployerFactory() {
            public IATDeployer createDeployer(Long clientId, Long deploymentId, Long testId, String session) {
                return iatDeployer(clientId, deploymentId, testId, session);
/* 
                var deployer = (IATDeployer) applicationContext.getBean("IATDeployer");
                deployer.setClientId(clientId);
                deployer.setDeploymentId(deploymentId);
                deployer.setTestId(testId);
                deployer.setSessionId(session);
                return deployer;
            */         }

            public IATRedeployer createRedeployer(Long clientId, Long deploymentId, Long replacementTestId,
                    Long testId, String sessId) {
                return iatRedeployer(clientId, deploymentId, replacementTestId, testId, sessId);
                /*
                var deployer = (IATRedeployer) applicationContext.getBean("IATRedeployer");
                deployer.setClientId(clientId);
                deployer.setDeploymentId(deploymentId);
                deployer.setTestId(testId);
                deployer.setSessionId(sessId);
                deployer.setOldTestId(replacementTestId);
                return deployer;*/
            }
        };
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        TaskScheduler scheduler = this.taskScheduler();
        registrar.setTaskScheduler(scheduler);
    }

}
