package test;

import com.chat.common.config.OpenApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {OpenApiConfig.class})
public class Test {
    @Autowired
    OpenApiConfig openApiConfig;
    @org.junit.jupiter.api.Test

    public void t(){
        System.out.println(openApiConfig.getClass());
    }
}
