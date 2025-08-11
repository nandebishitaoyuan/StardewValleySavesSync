package com.taoyuan.stardewvalleysavessync.interceptor

import cn.hutool.json.JSONUtil
import com.sctianji.playserver.annotation.AllowAnonymous
import com.sctianji.playserver.constants.JwtConstant
import com.taoyuan.stardewvalleysavessync.context.UserContext
import com.taoyuan.stardewvalleysavessync.exception.AuthException
import com.taoyuan.stardewvalleysavessync.model.User
import com.taoyuan.stardewvalleysavessync.utils.JwtUtil
import com.taoyuan.stardewvalleysavessync.utils.StringUtils
import io.jsonwebtoken.Claims
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import kotlin.jvm.java

@Component
class AuthHandlerInterceptor: HandlerInterceptor {

    private val log = LoggerFactory.getLogger(AuthHandlerInterceptor::class.java)

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val handlerMethod: HandlerMethod?
        var jwtToken: String? = null

        // 获取请求头中的 JWT token
        val authHeader = request.getHeader(JwtConstant.HEADER)
        if (authHeader != null && authHeader.startsWith(JwtConstant.PREFIX)) {
            jwtToken = authHeader.substring(JwtConstant.PREFIX.length)
        }

        if (handler is HandlerMethod) {
            handlerMethod = handler
            if (handlerMethod.method.isAnnotationPresent(AllowAnonymous::class.java) || handlerMethod.beanType
                    .isAnnotationPresent(AllowAnonymous::class.java)
            ) {
                if (StringUtils.isNotBlank(jwtToken)) {
                    getToken(jwtToken)
                }
                return true
            }
        }

        if (StringUtils.isBlank(jwtToken)) {
            log.error("请求地址 {} 没有token", request.requestURI)
            throw AuthException("token校验失败")
        }

        getToken(jwtToken)
        return true
    }

    private fun getToken(jwtToken: String?) {
        if (jwtToken == null) {
            throw AuthException("token校验失败")
        }
        val claims: Claims
        try {
            claims = JwtUtil.parseToken(jwtToken)
            val userStr: String? = claims["user"] as String?
            val jsonObj = JSONUtil.parseObj(userStr)
            val user = jsonObj.toBean(User::class.java)
            UserContext.setUser(user)

        } catch (e: Exception) {
            log.error("解析token 错误  token {} ", jwtToken, e)
            throw AuthException("token校验失败", e)
        }
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        UserContext.clearContext()
    }
}