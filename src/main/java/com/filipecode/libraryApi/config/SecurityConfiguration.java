package com.filipecode.libraryApi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// @Configuration: Indica que esta classe é uma fonte de definições de beans para o contexto do Spring.
@Configuration
// @EnableWebSecurity: Habilita a configuração de segurança web do Spring Security na aplicação.
// É essencial para que as configurações definidas aqui sejam aplicadas.
@EnableWebSecurity
public class SecurityConfiguration {

    // @Bean: Define que o método securityFilterChain produzirá um bean gerenciado pelo Spring.
    // Neste caso, o bean é um SecurityFilterChain, que define como as requisições HTTP serão protegidas.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // HttpSecurity é o objeto principal usado para configurar a segurança web.
        // Ele permite encadear várias configurações de forma fluida (usando o padrão builder).
        return http
                // .csrf(AbstractHttpConfigurer::disable): Desabilita a proteção CSRF (Cross-Site Request Forgery).
                // CSRF é um tipo de ataque onde comandos não autorizados são transmitidos de um usuário em quem a aplicação web confia.
                .csrf(AbstractHttpConfigurer::disable)

                // .formLogin(Customizer.withDefaults()): Habilita a autenticação baseada em formulário com as configurações padrão do Spring Security.
                // Isso significa que o Spring Security irá gerar uma página de login padrão se o usuário tentar acessar um recurso protegido sem estar autenticado.
                // Customizer.withDefaults() aplica as configurações padrão, mas pode personalizar o formulário de login, URL de sucesso, URL de falha, etc.
                // Se você não definir isso e tiver .authorizeHttpRequests().anyRequest().authenticated(),
                .formLogin(configurer -> {
                    configurer.loginPage("/login").permitAll(); // Customizando a página
                })

                // .httpBasic(Customizer.withDefaults()): Habilita a autenticação HTTP Basic com as configurações padrão.
                // Na autenticação HTTP Basic, o cliente (navegador ou outra aplicação) envia o nome de usuário e senha
                // codificados em Base64 no cabeçalho "Authorization" de cada requisição.
                .httpBasic(Customizer.withDefaults())

                // .authorizeHttpRequests(authorize -> { ... }): Configura as regras de autorização para as requisições HTTP.
                // É aqui que você define quais URLs exigem autenticação, quais papéis (roles) ou permissões (authorities) são necessários, etc.
                .authorizeHttpRequests(authorize -> {
                    // authorize.anyRequest().authenticated(): Especifica que qualquer requisição (anyRequest())
                    // para a aplicação deve ser autenticada (authenticated()).
                    authorize.anyRequest().authenticated();
                })

                // .build(): Constrói o objeto SecurityFilterChain com todas as configurações aplicadas.
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        UserDetails firstUser = User.builder()
                .username("Filipe Mota")
                .password(encoder.encode("123"))
                .roles("USER")
                .build();

        UserDetails secondUser = User.builder()
                .username("Nicoly Mota")
                .password(encoder.encode("321"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(firstUser, secondUser);
    }
}