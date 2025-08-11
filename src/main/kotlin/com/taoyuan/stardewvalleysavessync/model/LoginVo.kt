package com.taoyuan.stardewvalleysavessync.model

data class LoginVo (
    val id: Long,
    val name: String?,
    val token: String?,
    val refreshToken: String?,
)