package com.github.vchimishuk.newground;

import java.io.File;

import javax.inject.Named;

import com.github.vchimishuk.newground.service.ParserService;
import com.github.vchimishuk.newground.service.StorageService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:newground.properties")
public class AppConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer getPropertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Named("sourceStorage")
    public StorageService getSourceStorage(ParserService parserService,
            @Value("${sourceStorage}") String file)
    {
        return new StorageService(parserService, new File(file));
    }

    @Bean
    @Named("calculationsStorage")
    public StorageService getCalculationsStorage(ParserService parserService,
            @Value("${calculationsStorage}") String file)
    {
        return new StorageService(parserService, new File(file));
    }
}
