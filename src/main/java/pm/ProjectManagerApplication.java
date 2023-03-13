package pm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pm.entity.Users;
import pm.service.UserService;

@SpringBootApplication
public class ProjectManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectManagerApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserService userService, PasswordEncoder passwordEncoder) {
        return args -> {
            userService.save(new Users(1, "user", passwordEncoder.encode("userPass"), "ROLE_USER"));
            userService.save(new Users(2, "admin", passwordEncoder.encode("adminPass"), "ROLE_USER,ROLE_ADMIN"));
        };
    }
}
