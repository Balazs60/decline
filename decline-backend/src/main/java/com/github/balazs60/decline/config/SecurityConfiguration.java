package com.github.balazs60.decline.config;


import com.github.balazs60.decline.model.members.Role;
import com.github.balazs60.decline.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final MemberDetailsService memberDetailsService;
    private final PasswordEncoder passwordEncoder;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> {

                    req.requestMatchers("/task/**").permitAll();
                    req.requestMatchers("/api/v1/auth/**").permitAll();
                    req.requestMatchers("/api/guestcart/guest").permitAll();
                    req.requestMatchers("/api/category/{category}/subcategories").permitAll();
                    req.requestMatchers("/api/category/{category}/subcategory/{subCategoryId}/products").permitAll();
                    req.requestMatchers("/api/cart/**").permitAll();
                    req.requestMatchers("/api/order/**").permitAll();
                    req.requestMatchers("/api/products-in-cart").permitAll();
                    req.requestMatchers("/api/payment/**").permitAll();
                    req.requestMatchers("/api/chat/**").permitAll();
                    req.requestMatchers("/api/products/{productId}/productdiscount/{productSale}")

                            .hasRole(Role.ADMIN.name())
                            .anyRequest().authenticated();
                })
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, AnonymousAuthenticationFilter.class);

        return http.build();
    }
    @Bean(name = "securityAuthenticationProvider")
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(memberDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder); // Inject the password encoder bean
        return authenticationProvider;
    }
}
