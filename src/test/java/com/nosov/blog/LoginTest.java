package com.nosov.blog;

import com.nosov.blog.controller.MainController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-dev.properties")
public class LoginTest {

    @Autowired
    MainController controller;

    @Test
    public void test() throws Exception{
        assertThat(controller).isNotNull();
    }
}
