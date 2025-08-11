package com.sctianji.playserver.constants;

class JwtConstant {

    companion object {
        /**
         * token密钥
         */
        const val SECRET = "0682c3f99b904e18b38486d6ff1e5419";

        /**
         * token前缀
         */
        const val PREFIX = "Bearer ";

        /**
         * token请求头
         */
        const val HEADER = "Authorization";

        /**
         * token过期时间(秒)(24小时)
         */
        const val EXPIRE_TIME = 60 * 60 * 24;

        /**
         * 刷新token过期时间(秒)(30天)
         */
        const val REFRESH_EXPIRE_TIME = EXPIRE_TIME * 30
    }

}