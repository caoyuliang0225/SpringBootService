package com.yl.crm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Cao Yuliang on 2019-08-23.
 */
@Slf4j
@Configuration
public class BootstrapConfig implements ApplicationRunner {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        logger.info("*******************************************************************");
        logger.info("        spring.profiles.active: {} ({})", appName, activeProfile);
        logger.info("*******************************************************************");
    }
}
