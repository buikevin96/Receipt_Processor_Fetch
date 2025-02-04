package com.fetch.receiptProcessor.Config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.fetch.receiptProcessor")
@EnableAspectJAutoProxy
public class AppConfig {
}