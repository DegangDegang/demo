package com.example.demo.global.config;

import com.example.demo.global.property.JwtProperties;
import com.example.demo.global.property.OauthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@EnableConfigurationProperties({OauthProperties.class, JwtProperties.class})
@Configuration
public class ConfigurationPropertiesConfig {}