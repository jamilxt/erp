package com.brainstation23.erp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.brainstation23.erp.constant.AppConstant.DEFAULT_TOKEN_VALIDITY_SECONDS;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // TODO: use SecurityFilterChain

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
        auth.userDetailsService(userDetailsService);
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String[] staticResources = {"/static/assets/css/**", "/static/assets/images/**", "/static/assets/js/**"};

        http.authorizeRequests()
                .antMatchers(staticResources).permitAll()
                .antMatchers("/forgot-password", "/change-password", "/about/**", "/", "/login", "/register/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/login").defaultSuccessUrl("/dashboard", true)
                .usernameParameter("email")
                .permitAll()

                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .permitAll()

                .and()
                .rememberMe()
                .key("AbcDefgHijKlmnOpqrs_1234567890") //this will be created session id (cookies) when login
                .tokenValiditySeconds(DEFAULT_TOKEN_VALIDITY_SECONDS);

        http.addFilterAfter(new LoggedInUserFilter(), UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable();

        //http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated().and().csrf().disable();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/css/**", "/assets/images/**", "/assets/js/**", "/assets/img/**", "/assets/vendor/**");
    }
}
