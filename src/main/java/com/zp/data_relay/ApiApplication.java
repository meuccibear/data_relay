package com.zp.data_relay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) throws UnknownHostException {
        // log4j2全局异步日志配置 http://logging.apache.org/log4j/2.x/manual/async.html#AllAsync
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        SpringApplication app = new SpringApplication(ApiApplication.class);
        ConfigurableApplicationContext application = app.run(args);
        //ConfigurableApplicationContext application=SpringApplication.run(Knife4jSpringBootDemoApplication.class, args);
        Environment env = application.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}\n\t" +
                        "External: \thttp://{}:{}\n\t" +
                        "Doc: \thttp://{}:{}/doc.html\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));
        // 停止应用时，关闭线程池钩子，或者使用 @PreDestroy 注解执行一系列操作
//        Runtime.getRuntime().addShutdownHook(new Thread(ThreadPoolUtils::shutdown, "ShutdownThreadPoolHook"));
    }

}
