package dgtic.core.M8P1.security;

import dgtic.core.M8P1.Util.JwtAuthFilter;
import dgtic.core.M8P1.repository.UsuarioRepository;
import dgtic.core.M8P1.service.CustomUserDetailsService;
import dgtic.core.M8P1.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtAuthFilter jwtAuthenticationFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/bootstrap/**", "/iconos/**", "/image/**", "/tema/**").permitAll()
                        .requestMatchers("/login", "/error","/usuario/registro").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        // Administrador tiene acceso a todo
                        .requestMatchers("/admin/**").hasRole("Administrador")
                        .requestMatchers("/proyecto/crear", "/proyecto/editar/**").hasAnyRole("ADMINISTRADOR", "LIDER")
                        .requestMatchers("/tarea/crear", "/tarea/editar/**").hasAnyRole("ADMINISTRADOR", "LIDER", "MIEMBRO")
                        .requestMatchers("/usuario/invitar").hasAnyRole("ADMINISTRADOR", "LIDER")
                        .requestMatchers("/usuario/roles").hasAnyRole("ADMINISTRADOR", "LIDER")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(customUserDetailsService)
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // siempre encriptar
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("admin123")) // << ahora encripta con BCrypt
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }


}
