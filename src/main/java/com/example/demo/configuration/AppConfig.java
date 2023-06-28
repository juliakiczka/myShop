//package com.example.demo.configuration;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration// adnotacja do tworzenia beanów
//@EnableWebSecurity
//public class AppConfig {
//    @Bean
//    public UserDetailsManager userDetailsManager() {
//        return new InMemoryUserDetailsManager();
//    }
//
////    @Bean
////    InitializingBean initializingBean(UserDetailsManager userDetailsManager) {
////        return () -> {
////            UserDetails jk = User
////                    .builder()
////                    .passwordEncoder(password -> PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password))
////                    .password("maslo")
////                    .roles("User")
////                    .username("jk")
////                    .build();
////            userDetailsManager.createUser(jk);
////            UserDetails admin = User
////                    .builder()
////                    .passwordEncoder(password -> PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password))
////                    .password("password")
////                    .roles("Admin")
////                    .username("admin")
////                    .build();
////            userDetailsManager.createUser(admin);
////        };
////    }
////
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
////        httpSecurity
////                .httpBasic()
////                .and()
////                .formLogin()
////                .and()
////                .logout();
////        httpSecurity
////                .sessionManagement()
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////        todo fix this and commit
////        httpSecurity
////                .authorizeHttpRequests()
////                .requestMatchers(HttpMethod.POST,"/questions/submit")
////                .hasRole("Admin")
////                .anyRequest()
////                .authenticated();
////        return httpSecurity
////                .build();
////    }
////
////
//}
