package com.doinb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.core.env.Environment;

/**
 * @author doinb DoApplication == DoinbApplication
 * springboot 排除数据源启动， 搭建简易工程用于学习
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class
})
public class DoApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DoApplication.class);
        Environment env = app.run(args).getEnvironment();
        // 打印可视化的启动信息
        DefaultProfileUtil.addDefaultProfile(app);
        DefaultProfileUtil.logApplicationStartup(env);
    }
}