package com.taoyuan.stardewvalleysavessync.exception

import com.sctianji.playserver.exception.ParamException
import com.taoyuan.stardewvalleysavessync.model.ApiResult
import com.taoyuan.stardewvalleysavessync.utils.RequestUtil
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class GlobalExceptionHandling {

    // 创建 Logger
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandling::class.java)


    @ResponseBody
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ApiResult<Unit> {
        logger.error("系统异常", e)
        return ApiResult.failure("错误：" + e.message)
    }


    @ResponseBody
    @ExceptionHandler(AuthException::class)
    fun handleLoginException(e: AuthException): ApiResult<Unit> {
        logger.error("认证异常", e)
        logger.error("❌认证异常，uri：{}，异常信息：{}", RequestUtil().getRequestInfo(), e.message)
        return ApiResult.failure(40001, e.message)
    }

    @ResponseBody
    @ExceptionHandler(ParamException::class)
    fun handleParamException(e: ParamException): ApiResult<Unit> {
        logger.error("接口入参异常", e)
        logger.error("❌接口入参异常，uri：{}，异常信息：{}", RequestUtil().getRequestInfo(), e.message)
        return ApiResult.failure(e.message)
    }

}