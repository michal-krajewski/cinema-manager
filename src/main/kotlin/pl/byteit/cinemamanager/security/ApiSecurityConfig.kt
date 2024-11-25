package pl.byteit.cinemamanager.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.*
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.ObjectPostProcessor
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.security.web.session.HttpSessionEventPublisher
import pl.byteit.cinemamanager.security.login.LoginFilter
import pl.byteit.cinemamanager.security.login.LoginHandler
import pl.byteit.cinemamanager.user.Role.ADMIN
import pl.byteit.cinemamanager.user.UserService

@Configuration
@EnableWebSecurity
class ApiSecurityConfig {

    @Bean
    fun filterChain(
        http: HttpSecurity,
        loginFilter: LoginFilter,
        multiReadRequestFilter: MultiReadRequestFilter,
        securityContextRepository: SecurityContextRepository
    ): SecurityFilterChain {
        http
            .csrf { it.disable() } //to be enabled
            .securityContext { it.requireExplicitSave(true).securityContextRepository(securityContextRepository) }
            .sessionManagement{ session ->
                session
                    .sessionFixation { it.newSession() }
                    .maximumSessions(1)
            }
            .exceptionHandling { it.authenticationEntryPoint(HttpStatusEntryPoint(UNAUTHORIZED)) }
            .logout {
                it.logoutUrl("/logout")
                    .invalidateHttpSession(true)
                    .permitAll()
            }
            .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(multiReadRequestFilter, LoginFilter::class.java)
            .authorizeHttpRequests {
                it.requestMatchers("/login").permitAll()
                    .requestMatchers(GET, "/movies/*").permitAll()
                    .requestMatchers(PUT, "/movies/*/tickets").hasRole(ADMIN.name)
                    .requestMatchers(POST, "/users").permitAll()
                    .requestMatchers(GET, "/shows").permitAll()
                    .requestMatchers(DELETE, "/shows/*").hasRole(ADMIN.name)
                    .requestMatchers(PUT, "/shows/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(
        objectPostPostprocessor: ObjectPostProcessor<Any>,
        userAuthenticationProvider: UserAuthenticationProvider
    ): AuthenticationManager =
        AuthenticationManagerBuilder(objectPostPostprocessor)
            .authenticationProvider(userAuthenticationProvider)
            .build()

    @Bean
    fun authenticableUserProvider(userService: UserService, passwordEncoder: PasswordEncoder): AuthenticableUserProvider =
        AuthenticableUserProvider(userService, passwordEncoder)

    @Bean
    fun multiReadRequestFilter(): MultiReadRequestFilter =
        MultiReadRequestFilter()

    @Bean
    fun userAuthenticationProvider(authenticableUserProvider: AuthenticableUserProvider): UserAuthenticationProvider =
        UserAuthenticationProvider(authenticableUserProvider)

    @Bean //Required for more advanced session management
    fun httpSessionEventPublisher(): HttpSessionEventPublisher =
        HttpSessionEventPublisher()


    @Bean
    fun securityContextRepository(): SecurityContextRepository =
        HttpSessionSecurityContextRepository()

    @Bean
    fun loginFilter(
        objectMapper: ObjectMapper,
        authenticationManager: AuthenticationManager,
        securityContextRepository: SecurityContextRepository
    ): LoginFilter {
        val loginHandler = LoginHandler()
        val loginFilter = LoginFilter(objectMapper)

        loginFilter.setFilterProcessesUrl("/login")
        loginFilter.setAuthenticationSuccessHandler(loginHandler)
        loginFilter.setAuthenticationFailureHandler(loginHandler)
        loginFilter.setAuthenticationManager(authenticationManager)
        loginFilter.setSecurityContextRepository(securityContextRepository)

        return loginFilter
    }

}
