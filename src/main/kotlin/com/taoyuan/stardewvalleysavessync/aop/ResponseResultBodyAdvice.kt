package com.taoyuan.stardewvalleysavessync.aop

import com.sctianji.playserver.annotation.IgnoreResultPackage
import com.taoyuan.stardewvalleysavessync.model.ApiResult
import org.springframework.core.MethodParameter
import org.springframework.core.annotation.AnnotatedElementUtils
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@RestControllerAdvice()
class ResponseResultBodyAdvice: ResponseBodyAdvice<Any> {

    companion object {
        private val IGNORE_ANNOTATION_TYPE = IgnoreResultPackage::class.java
    }

    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>?>
    ): Boolean {
        // 先直接过滤swagger接口
        if(returnType.declaringClass.getName().contains("springdoc")){
            return false;
        }
        // 再过滤接口上标记要过滤的接口
        val ignoreAnnotation = AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), IGNORE_ANNOTATION_TYPE);
        return !ignoreAnnotation;
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>?>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        if (body is ApiResult<*>) {
            return body
        }

        val ignoreResultPackage = returnType.hasMethodAnnotation(IgnoreResultPackage::class.java)
        if (ignoreResultPackage) {
            return body
        }

        // 如果返回的 body 是字符串或其他类型，可以包装成 ApiResult
        return ApiResult.success(body)
    }


}