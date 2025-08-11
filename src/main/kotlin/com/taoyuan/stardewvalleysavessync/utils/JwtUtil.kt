package com.taoyuan.stardewvalleysavessync.utils

import com.sctianji.playserver.constants.JwtConstant
import com.taoyuan.stardewvalleysavessync.exception.AuthException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.Keys
import java.util.*

class JwtUtil {
    companion object {
        /**
         * 生成访问token
         * @param claims 存放在token中的信息
         * @return token
         */
        fun generateAccessToken(claims: HashMap<String, Any>): String {
            val calendar = Calendar.getInstance()
            val now = calendar.getTime()
            // 设置签发时间
            calendar.setTime(Date())
            // 设置过期时间
            // 添加秒钟
            calendar.add(Calendar.SECOND, JwtConstant.EXPIRE_TIME)
            val time = calendar.getTime()

            claims["type"] = "access"

            //jwt前面一般都会加Bearer
            return JwtConstant.PREFIX + Jwts.builder()
                .claims(claims)
                //签发时间
                .issuedAt(now)
                //过期时间
                .expiration(time)
                .signWith(Keys.hmacShaKeyFor(JwtConstant.SECRET.toByteArray()))
                .compact()
        }

        /**
         * 生成刷新token
         * @param claims 存放在token中的信息
         * @return token
         */
        fun generateRefreshToken(claims: HashMap<String, Any>): String {
            val calendar = Calendar.getInstance()
            val now = calendar.getTime()
            // 设置签发时间
            calendar.setTime(Date())
            // 设置过期时间
            // 添加秒钟
            calendar.add(Calendar.SECOND, JwtConstant.REFRESH_EXPIRE_TIME)
            val time = calendar.getTime()

            claims["type"] = "refresh"

            //jwt前面一般都会加Bearer
            return JwtConstant.PREFIX + Jwts.builder()
                .claims(claims)
                //签发时间
                .issuedAt(now)
                //过期时间
                .expiration(time)
                .signWith(Keys.hmacShaKeyFor(JwtConstant.SECRET.toByteArray()))
                .compact()
        }

        /**
         * 解析token
         * @param token token
         * @return Claims 解析后的信息
         */
        fun parseToken(token: String): Claims {
            try {
                val claimsJws = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(JwtConstant.SECRET.toByteArray()))
                    .build().parseSignedClaims(token)
                return claimsJws.getPayload()
            }catch (e: ExpiredJwtException) {
                throw AuthException("token过期", e);
            }catch (e: MalformedJwtException) {
                throw AuthException("无效的token", e);
            }
        }

        /**
         * 校验token
         */
        fun checkToken(token: String) {
            parseToken(token)
        }
    }
}