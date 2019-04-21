package io.lybo.rpc.boot;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringServerStarter {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath:application-server.xml");
    }
}
