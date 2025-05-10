package ru.mirea.fantasyfootballauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.fantasyfootballauth.dto.AuthCheckDTO;
import ru.mirea.fantasyfootballauth.entity.UserEntity;
import ru.mirea.fantasyfootballauth.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepo;

    @GetMapping("/login")
    public void redirectToProvider() {

    }

    @GetMapping("/check")
    public ResponseEntity<AuthCheckDTO> getAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getPrincipal().equals("anonymousUser")) {
            DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
            String userId = Objects.requireNonNull(user.getAttribute("id")).toString();

            UserEntity info = userRepo.findById(UUID.fromString(userId)).get();
            AuthCheckDTO dto = new AuthCheckDTO(userId, info.getToken());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateId(
            @RequestParam(name = "userId") String userId,
            @RequestParam(name = "token") String token) {
        Optional<UserEntity> infoOptional = userRepo.findById(UUID.fromString(userId));
        return infoOptional
                .map(userInfo -> ResponseEntity.ok(userInfo.getToken().equals(token)))
                .orElseGet(() -> ResponseEntity.ok(false));
    }
}
