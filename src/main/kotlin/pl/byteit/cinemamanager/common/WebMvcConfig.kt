package pl.byteit.cinemamanager.common

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import pl.byteit.cinemamanager.user.UserInterceptor

@Configuration
class WebMvcConfig(private val userInterceptor: UserInterceptor) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(userInterceptor)
    }
}
