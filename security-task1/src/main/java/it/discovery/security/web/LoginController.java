package it.discovery.security.web;

import it.discovery.security.dto.LoginDTO;
import it.discovery.security.jwt.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("login")
@RequiredArgsConstructor
public class LoginController {

    private static final int TOKEN_EXPIRATION_MIN = 30;

    private final TokenGenerator tokenGenerator;

    public ResponseEntity<Void> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, tokenGenerator.generate(loginDTO.getUsername(),
                        LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_MIN))).build();
    }
}
