package com.social.network.security;


import com.social.network.security.filters.ProfileAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.authorization.SpringAuthorizationEventPublisher;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


    final ProfileAuthenticationEntryPoint authenticationEntryPoint;

    final CustomUserDetailsService customUserDetailsService;

    //final ProfileAuthentiationProvider authProvider;

    final JwtRequestFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.csrf(csrf -> csrf.disable())
                .headers(httpSecurityHeadersConfigurer -> {
                    httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                })
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console**").permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui.html/**").permitAll()
                        .requestMatchers("/login.html/**").permitAll()
                        .requestMatchers("/ui/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/").permitAll()
                        .requestMatchers(HttpMethod.POST, "/gen-ai/search").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login/").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login/token").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/public/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/index/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/register/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/mvc/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/mvc/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/gen-ai/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/gen-ai/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setAuthoritiesMapper(authoritiesMapper());
        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception
    {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    GrantedAuthoritiesMapper authoritiesMapper()
    {
        SimpleAuthorityMapper mapper = new SimpleAuthorityMapper();
        mapper.setConvertToUpperCase(true);
        mapper.setDefaultAuthority("ADMIN");
        return mapper;
    }

    @Bean
    AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
    {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }

    @Bean
    AuthorizationEventPublisher authorizationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
    {
        return new SpringAuthorizationEventPublisher(applicationEventPublisher);
    }

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    RequestContext requestContext()
    {
        return new RequestContext();
    }
}
