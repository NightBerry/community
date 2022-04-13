package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author HAN
 * @date 2022/4/13 16:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTests {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

//    @Test
//    public void testTextMail() {
//        mailClient.sendMail(
//                "han_jlu@126.com", "TEST", "Welcome.");
//    }

    @Test
    public void testHtmlMail() {
        Context context = new Context();
        context.setVariable("username", "sunday");

        // 使用模板引擎生成同台网页
        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("han_jlu@126.com", "HTML", content);
    }

}
