/*
package com.brainstation23.erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
public class WebSecurityConfiguration {
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource)
    {
        */
/*UserDetails nishan=  User.builder()
                .username("nishan")
                .password("{noop}test")
                .roles("EMP","ADMIN")
                .build();

        UserDetails mufi=  User.builder()
                .username("mufi")
                .password("{noop}test")
                .roles("EMP")
                .build();

        UserDetails jawad =  User.builder()
                .username("jawad")
                .password("{noop}test")
                .roles("EMP")
                .build();

        return new InMemoryUserDetailsManager(nishan,mufi,jawad);*//*

        return new JdbcUserDetailsManager(dataSource);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize ->
                        authorize
                                .antMatchers("/h2-console/**").permitAll()
                                .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                                .antMatchers(HttpMethod.GET, "/api/users").hasAnyRole("EMP","ADMIN")
                                .antMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole("ADMIN","EMP")
                                .antMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")
                )
                .httpBasic(Customizer.withDefaults())
                .headers(headers->headers.frameOptions().disable())
                    .csrf(csrf->csrf.ignoringRequestMatchers(toH2Console()));

        return http.build();
    }

}*/
