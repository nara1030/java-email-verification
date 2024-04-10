package org.among.certification.security;

import org.among.certification.util.AppConstant;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(req -> req
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers(AppConstant.PREFIX_OF_URL + "/send-email"
                                , AppConstant.PREFIX_OF_URL + "/verify-email"
                                , AppConstant.PREFIX_OF_URL + "/join").permitAll()
                        .anyRequest().denyAll());
        return http.build();
    }
}
