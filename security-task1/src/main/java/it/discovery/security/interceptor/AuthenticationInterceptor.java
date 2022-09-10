package it.discovery.security.interceptor;

import it.discovery.security.NoSecurity;
import it.discovery.security.jwt.TokenValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenValidator tokenValidator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (handler instanceof HandlerMethod handlerMethod) {

            Object restController = handlerMethod.getBean();
            if (restController.getClass().isAnnotationPresent(NoSecurity.class)) {
                return true;
            }

            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            try {
                String userName = tokenValidator.validate(authHeader);

                log.info("User logged in {}", userName);
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
        }

        return true;
    }


}
