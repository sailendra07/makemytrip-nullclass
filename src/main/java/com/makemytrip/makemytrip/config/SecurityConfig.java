package com.makemytrip.makemytrip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    /**
     * Creates a BCryptPasswordEncoder bean for securely hashing passwords.
     * This is the standard and recommended password encoder.
     * @return A PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures global CORS (Cross-Origin Resource Sharing) settings for the application.
     * This is necessary to allow your frontend application to communicate with the backend API.
     * @return A WebMvcConfigurer with the CORS mapping.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // This configuration allows requests from any origin.
                // For production, you might want to restrict this to your actual frontend domain
                // for better security, e.g., .allowedOrigins("https://your-frontend-app.com")
                registry.addMapping("/**") // Apply this policy to all endpoints
                        .allowedOrigins("*") // Allow requests from any origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specify allowed HTTP methods
                        .allowedHeaders("*") // Allow all headers in the request
                        .allowCredentials(false); // Credentials (like cookies) are not sent with wildcard origins
            }
        };
    }
}
