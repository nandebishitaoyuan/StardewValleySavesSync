package com.taoyuan.stardewvalleysavessync.utils

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

class RequestUtil {

    fun getRequest(): HttpServletRequest {
        val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        return requestAttributes.request
    }

    /**
     * 获取真实ip
     */
    fun getRemoteAddress(): String {
        val request = getRequest();
        // 和运维协商的header，nginx中会在其中存储真实来源ip
        val realIp = request.getHeader("X-Real-IP");
        // 本地调试不经过nginx，一般返回就是内网ip或本机ip
        return realIp ?: request.remoteAddr;
    }

    /**
     * 请求信息，含uri和请求参数
     * 示例：GET:/api/getUser?id=1
     */
    fun getRequestInfo(): String {
        val request: HttpServletRequest = getRequest()
        var queryString: String? = request.queryString
        queryString = if (StringUtils.isEmpty(queryString) || "null" == queryString) "" else "?$queryString"
        return "${request.method}:${request.requestURI}$queryString"
    }
}