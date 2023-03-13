package pm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pm.service.JpaUserDetailsService;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final JpaUserDetailsService jpaUserDetailsService;

    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService) {
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf
                        .ignoringAntMatchers("/h2/**")
                        .disable())
                .authorizeRequests(auth ->  auth
                        .antMatchers("/h2/**").permitAll()
                         .antMatchers(HttpMethod.POST, "/project/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/project/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST, "/subproject/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/subproject/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .userDetailsService(jpaUserDetailsService)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
