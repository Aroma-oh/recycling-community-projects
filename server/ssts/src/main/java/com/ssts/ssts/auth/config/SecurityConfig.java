package com.ssts.ssts.auth.config;

import com.ssts.ssts.auth.filter.JwtVerificationFilter;
import com.ssts.ssts.auth.handler.OAuth2MemberFailureHandler;
import com.ssts.ssts.auth.handler.OAuth2MemberSuccessHandler;
import com.ssts.ssts.auth.jwt.JwtTokenizer;
import com.ssts.ssts.auth.utils.CustomAuthorityUtils;
import com.ssts.ssts.domain.member.service.MemberOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberOAuthService memberOAuthService;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .addFilterBefore(new JwtVerificationFilter(jwtTokenizer, authorityUtils), UsernamePasswordAuthenticationFilter.class) // https://yunb2.tistory.com/3
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers(HttpMethod.PATCH,"/**/members/edit/**").hasRole("USER")
                        //회원정보수정만 보안 걸어두기.
                        .anyRequest().permitAll()
                )
                .oauth2Login()
                .successHandler(new OAuth2MemberSuccessHandler(jwtTokenizer, authorityUtils, memberOAuthService))
                ;
                //.failureHandler(new OAuth2MemberFailureHandler());



        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Client Registration 을 저장하기 위한 repository
    // 이렇게 지정안하면 스프링이 자동으로 구성함. 이건 그걸 이걸로 지정해버림.
    // 클라이언트 등록.
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        var clientRegistration = clientRegistration(); // 밑에 선언한 함수로 인스턴스를 생성한다.
        // client registration 인스턴스
        return new InMemoryClientRegistrationRepository(clientRegistration);
        //
    }


    private ClientRegistration clientRegistration() {
        // spring security에서 제공하는 enum (CommonOAuth2Provider) -> builder 패턴을 이용해 ClientRegistration 인스턴스를 제공
        return CommonOAuth2Provider
                .GOOGLE
                .getBuilder("google")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }

}
