package it.discovery.security.jwt.filter;

import it.discovery.security.jwt.TokenValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final TokenValidator tokenValidator;

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        try {
            String username = tokenValidator.validate(request.getHeader(HttpHeaders.AUTHORIZATION));

            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(username, null);
            SecurityContextHolder.getContext().setAuthentication(token);
        } catch (Exception ex) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        filterChain.doFilter(req, resp);
    }
}