package com.taoyuan.stardewvalleysavessync.service

import com.taoyuan.stardewvalleysavessync.model.LoginVo

interface UserService {
    fun login(userName: String, password: String): LoginVo
    fun register(userName: String, password: String)
    fun refreshToken(refreshToken: String): LoginVo
}