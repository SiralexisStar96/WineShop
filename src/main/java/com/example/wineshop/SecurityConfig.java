package com.example.wineshop;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder pwEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Bean
    UserDetailsService authentication() {
        UserDetails peter = User.builder()
                .username("Alejandro")
                .password("1234")
                //.password(pwEncoder.encode("ppassword"))
                .roles("USER")
                .build();
        UserDetails jodie = User.builder()
                .username("Alvaro")
                .password("1234")
                //.password(pwEncoder.encode("jpassword"))
                .roles("USER", "ADMIN")
                .build();
        System.out.println("   >>> Contraseña de alejandro: " + peter.getPassword());
        System.out.println("   >>> Contraseña de Alvaro: " + jodie.getPassword());
        return new InMemoryUserDetailsManager(peter, jodie);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/wine/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }
}



