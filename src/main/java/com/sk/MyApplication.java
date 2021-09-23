package com.sk;

import com.sk.hello.web.HelloController;
import com.sk.user.dto.UserSaveRequestDto;
import com.sk.user.service.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan(
        basePackages = "com.sk",
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASPECTJ,
                        pattern = {"com.sk.hello..*"}
                )
        }
)
public class MyApplication  {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

    /*
     * Spring boot 초기화
     */
    @Bean
    public ApplicationRunner myRunner(UserService userService) {
        return args -> {
            //controller.home();
            UserSaveRequestDto dto = new UserSaveRequestDto();
            dto.setPassword("1111");
            dto.setName("이상국");
            dto.setUserId("admin");
            dto.setIsAdmin(new String[]{"ROLE_ADMIN,ROLE_USER"});
            userService.register(dto);
        };
    }
}
