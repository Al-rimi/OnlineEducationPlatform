package com.example.onlineeducationplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.onlineeducationplatform.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/", "/index.html", "/favicon.ico", "/favicon.svg", "/assets/**").permitAll()
                // Frontend routes - allow all SPA routes
                .antMatchers("/login", "/register", "/courses", "/courses/**", "/assignments", "/assignments/**",
                        "/quizzes", "/quizzes/**", "/videos/**", "/profile", "/admin", "/teacher", "/student")
                .permitAll()
                .antMatchers("/api/users/login", "/api/users/register").permitAll()
                // public read-only catalog endpoints
                .antMatchers(org.springframework.http.HttpMethod.GET, "/api/categories/**").permitAll()
                .antMatchers(org.springframework.http.HttpMethod.GET, "/api/courses/enrolled").authenticated()
                .antMatchers(org.springframework.http.HttpMethod.GET, "/api/courses/my-courses").authenticated()
                .antMatchers(org.springframework.http.HttpMethod.GET, "/api/courses/**").permitAll()
                .antMatchers(org.springframework.http.HttpMethod.GET, "/api/courses/*/videos").permitAll()
                .antMatchers(org.springframework.http.HttpMethod.GET, "/api/videos/**").permitAll()
                .anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add JWT filter
        http.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
