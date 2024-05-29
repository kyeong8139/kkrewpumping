package com.kkrew.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.kkrew.model.dao")
public class DBConfig {
}
