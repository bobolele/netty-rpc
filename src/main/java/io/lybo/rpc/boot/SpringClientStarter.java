package io.lybo.rpc.boot;

import io.lybo.rpc.test.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringClientStarter {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:application-client.xml");

        UserService userService = (UserService) context.getBean("userService");
        System.out.println("返回值： " + userService.getById("lybo"));
    }
}
