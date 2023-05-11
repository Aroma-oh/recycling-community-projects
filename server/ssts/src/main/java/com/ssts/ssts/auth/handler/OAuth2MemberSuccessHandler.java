package com.ssts.ssts.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ssts.ssts.auth.jwt.JwtTokenizer;
import com.ssts.ssts.auth.utils.CustomAuthorityUtils;
import com.ssts.ssts.auth.utils.CustomOAuth2User;
import com.ssts.ssts.auth.utils.GuestObject;
import com.ssts.ssts.auth.utils.GuestObjectSerializer;
import com.ssts.ssts.domain.member.entity.Member;
import com.ssts.ssts.domain.member.service.MemberOAuthService;
import com.ssts.ssts.domain.member.service.MemberService;
import com.ssts.ssts.exception.BusinessLogicException;
import com.ssts.ssts.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.security.core.GrantedAuthority;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@RequiredArgsConstructor
@Slf4j
public class OAuth2MemberSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenizer jwtTokenizer;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("하늘/security : SuccessHandler 진입");

        var customOAuth2User = (CustomOAuth2User)authentication.getPrincipal();
        //Authentication -> CustomOAuth2User객체 가져오기

        // CustomOAuth2User객체에서 데이터(email, id) 뽑기
        String email = customOAuth2User.getEmail();
        long id = customOAuth2User.getMemberId();
        log.info("하늘/security : email="+email+", id="+id);

        // 권한 정보 가져오기
        List<String> authorities = customOAuth2User.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        log.info("하늘/security : authorities="+authorities.toString());
        if (authorities.toString().equals("[ROLE_GUEST]")) {

            GuestObject object = new GuestObject(email);
            String json = "";

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                SimpleModule module = new SimpleModule();
                module.addSerializer(GuestObject.class, new GuestObjectSerializer());
                objectMapper.registerModule(module);
                json = objectMapper.writeValueAsString(object);
            } catch (IOException e) {
                throw new BusinessLogicException(ExceptionCode.SECURITY_GUEST_OBJECT_SERIALIZE_ERROR);
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);

            try {
                response.getWriter().write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            redirect(request, response, id, email, authorities); // 권한 정보 받아서 새 요청 만들기
        }

    }


    //TODO 왜 IOException을 던지는걸까? 이유 찾아보기.
    private void redirect(HttpServletRequest request, HttpServletResponse response, long id, String email, List<String> authorities) throws IOException {

        System.out.println("하늘/security : redirect() 메서드 진입");

        String accessToken = delegateAccessToken(id, email, authorities);  // access token 생성
        String refreshToken = delegateRefreshToken(email);     // refresh token 생성
        log.info("하늘/security Bearer {}" , accessToken);

        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addHeader("Refresh", refreshToken);

        response.setStatus(HttpServletResponse.SC_OK);
    }

    private String delegateAccessToken(long id, String email, List<String> authorities) {
        //email과 권한 받아옴
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("roles", authorities);
        claims.put("id", id);


        String subject = email;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        //JWT에 넣을 만기시간 만들기

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        //(string->base64) key

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
        //access token만들기
        //질문) subject는 어디에 쓰는 건데?
        // -> subject은 정보의 주체를 나타내는 속성이다. id나 email, 즉 식별자를 넣으면 된다. 반드시 필요
        // 그런데 보통 문자열 타입을 넣는다네, 그럼 email넣자.

        return accessToken;
    }

    private String delegateRefreshToken(String username) {

        //access token과는 다르게 claims를 넣지 않는다.

        String subject = username;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }

    private URI createURI() {

        // 로그인 성공하면 홈 화면으로 리다이렉트
        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/")
                .build()
                .toUri();
    }
}
