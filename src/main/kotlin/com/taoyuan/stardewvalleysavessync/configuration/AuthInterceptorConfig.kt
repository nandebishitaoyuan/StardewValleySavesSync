package com.taoyuan.stardewvalleysavessync.configuration

import com.taoyuan.stardewvalleysavessync.interceptor.AuthHandlerInterceptor
import jakarta.annotation.Resource
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@Import(AuthHandlerInterceptor::class)
class AuthInterceptorConfig(
    @Resource
    private val authHandlerInterceptor: AuthHandlerInterceptor
): WebMvcConfigurer {

    val AUTH_WHITELIST: List<String> = arrayOf<String>(
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/v3/**",
        "/webjars/**",
        "/oauth/**",
        "/actuator/**",
        "/websocket/**",
        "/ws/**",
        "/doc.html",
        "/doc.html/**",
        "/favicon.ico",
        "/oauth2/**"
    ).toList()

    override fun addInterceptors(registry: InterceptorRegistry) {
        val interceptorRegistration = registry.addInterceptor(authHandlerInterceptor)
        //默认不拦截swagger，加载业务侧 不拦截配置
        interceptorRegistration.addPathPatterns("/**").excludePathPatterns(AUTH_WHITELIST)
    }

}