package com.sctianji.playserver.annotation

import org.springframework.web.bind.annotation.ResponseBody

/**
 * 忽略api返回结果的封包
 * @author IceYuany
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@MustBeDocumented
@ResponseBody
annotation class IgnoreResultPackage
