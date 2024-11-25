package pl.byteit.cinemamanager.user

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserConfiguration {

    @Bean
    fun userContext(): UserContext {
        return object : UserContext {}
    }

    @Bean
    fun userInterceptor(): UserInterceptor {
        return UserInterceptor()
    }

}
