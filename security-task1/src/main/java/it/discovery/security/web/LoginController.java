package it.discovery.security.web;

import it.discovery.security.dto.LoginDTO;
import it.discovery.security.jwt.TokenGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

//@RestController
//@RequestMapping("login")
@RequiredArgsConstructor
//@NoSecurity
public class LoginController {

    private static final int TOKEN_EXPIRATION_MIN = 30;

    private final TokenGenerator tokenGenerator;

    @PostMapping
    public ResponseEntity<Void> login(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, tokenGenerator.generate(loginDTO.username(),
                        LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_MIN))).build();
    }
}
