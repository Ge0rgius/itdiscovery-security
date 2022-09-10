package it.discovery.security.web;

import it.discovery.security.dto.LoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    public ResponseEntity<Void> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok().build();
    }
}
