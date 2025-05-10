package ru.mirea.fantasyfootballauth.config;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.mirea.fantasyfootballauth.entity.UserEntity;
import ru.mirea.fantasyfootballauth.repository.UserRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepo;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(
                                        "/api/auth/validate",
                                        "/api/auth/check",
                                        "/api/auth/hello"
                                ).permitAll()
                                .requestMatchers("/api/auth/login").authenticated()
                                .anyRequest().denyAll())
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(successHandler()));
        return http.build();
    }

    @Bean
    AuthenticationSuccessHandler successHandler() {
        return ((request, response, authentication) -> {
            DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> attrs = user.getAttributes();

            String token = UUID.randomUUID().toString();

            UUID id = ((UUID) attrs.get("id"));
            Optional<UserEntity> infoOptional = userRepo.findById(id);

            UserEntity userInfo;

            if (infoOptional.isEmpty()) {
                userInfo = new UserEntity();

                userInfo.setId(id);
                userInfo.setName(attrs.get("name").toString());
                userInfo.setEmail(attrs.get("email").toString());
            } else {
                userInfo = infoOptional.get();
            }
            userInfo.setToken(token);
            userRepo.save(userInfo);

            response.setStatus(302);
            response.addHeader("location", "http://localhost:80");
        });
    }
}
