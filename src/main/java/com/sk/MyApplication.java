package com.sk;

import com.sk.user.UserHomeController;
import com.sk.user.config.auth.CustomSecurityConfig;
import com.sk.user.config.auth.InMemorySecurityConfig;
import com.sk.user.config.auth.JDBCSecurityConfig;
import com.sk.user.config.auth.SecurityConfig;
import com.sk.user.dto.UserSaveRequestDto;
import com.sk.user.service.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;

/**
 * practice-2110 My Application
 */
@SpringBootApplication
@ComponentScan( basePackageClasses = {UserHomeController.class}, // MyController.class 를 기준으로 하위 package를 scan 한다.알아서 hello package는 제외됨 
		        excludeFilters = { 
		    		   @Filter(
		    				   type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class,
                                                                             InMemorySecurityConfig.class, JDBCSecurityConfig.class}
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
            dto.setIsAdmin(new String[]{"ROLE_ADMIN"});

            userService.register(dto);
        };
    }
}
