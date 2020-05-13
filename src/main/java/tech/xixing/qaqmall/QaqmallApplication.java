package tech.xixing.qaqmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("tech.xixing.qaqmall.dao")
public class QaqmallApplication {

    public static void main(String[] args) {
        SpringApplication.run(QaqmallApplication.class, args);
    }

}
