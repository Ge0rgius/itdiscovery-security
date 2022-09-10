package it.discovery.security.config;

import it.discovery.security.jwt.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    SecurityFilterChain defaultFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http.authorizeRequests(authorize -> authorize.anyRequest().authenticated())
                .httpBasic(withDefaults())
                .formLogin();
        http.addFilterAfter(jwtFilter, BasicAuthenticationFilter.class);

        http.csrf().disable();
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .passwordEncoder(passwordEncoder::encode)
                .username("admin").password("root")
                .roles("ADMIN").build();
        return new InMemoryUserDetailsManager(admin);
    }
}
