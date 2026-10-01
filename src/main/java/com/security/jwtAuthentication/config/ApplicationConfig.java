package com.security.jwtAuthentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;

import com.security.jwtAuthentication.user_repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    @Autowired
    final UserRepository userRepo;

    @Bean
    public UserDetailsService userDetailService(){

        return username -> userRepo.findByEmail(username)
        .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        // return new UserDetailsService() {

        //     @Override
        //     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //         // TODO Auto-generated method stub
        //         throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
        //     }
            
            
        // };

    }
     @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailService());
        // authProvider.setUserDetailsPasswordService(userDetailService());
        authProvider.setPasswordEncoder(passwordEncoder()); 
        return authProvider;
    }

   
    
}
