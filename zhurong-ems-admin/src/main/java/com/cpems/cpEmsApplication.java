package com.cpems;

import cn.hutool.core.util.StrUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 启动程序
 * @author cpems
 */

// 添加 @ComponentScan，指定需要扫描的包（包含 com.ruoyi.system）
@ComponentScan(basePackages = {"com.cpems", "com.ruoyi"})
// 通过配置文件中的mapperPackage配置扫码Mapper接口所在的包
// 扫描Mapper接口所在的包（必须包含 com.ruoyi.system.mapper）
// @MapperScan(basePackages = {"com.cpems.**.mapper", "com.ruoyi.**.mapper"})
@SpringBootApplication
@EnableAsync
@EnableScheduling
public class cpEmsApplication {
    private static final Logger logger = LoggerFactory.getLogger(cpEmsApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication application = new SpringApplication(cpEmsApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        ConfigurableApplicationContext applicationContext = application.run(args);
        // System.out.println("(♥◠‿◠)ﾉﾞ  autoee-zhurong-ems后台启动成功   ლ(´ڡ`ლ)ﾞ");

        Environment env = applicationContext.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String activeEnv = env.getProperty("spring.profiles.active");
        String path = env.getProperty("server.servlet.context-path");
        String dbUrl = env.getProperty("spring.datasource.dynamic.datasource.master.url");
        dbUrl = StrUtil.subBefore(dbUrl, "?", false);

        logger.info("\n    ----------------------------------------------------------\n\t" +
            "服务启动成功！当前环境：" + activeEnv + "\n\t" +
            "Local: \t\thttp://localhost:" + port + path + "\n\t" +
            "External: \thttp://" + ip + ":" + port + path + "\n\t" +
            "----------------------------------------------------------\n\t" +
            "dbUrl: \t" + dbUrl + "\n\t" +
            "----------------------------------------------------------");
    }

}
