package com.filipecode.libraryApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(configurer -> {
                    configurer.loginPage("/login").permitAll(); // Customizando a página
                })
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {

//                    authorize.requestMatchers(HttpMethod.POST, "/autores/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.DELETE, "/autores/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.PUT, "/autores/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.GET, "/autores/**").hasAnyRole("USER" ,"ADMIN");

                    authorize.requestMatchers("/login").permitAll();
                    authorize.requestMatchers("/autores/**").hasRole("ADMIN");
                    authorize.requestMatchers("/livros/**").hasAnyRole("USER", "ADMIN");

                    authorize.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        UserDetails firstUser = User.builder()
                .username("Filipe Mota")
                .password(encoder.encode("123"))
                .roles("USER")
                .build();

        UserDetails secondUser = User.builder()
                .username("Nicoly Mota")
                .password(encoder.encode("321"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(firstUser, secondUser);
    }
}