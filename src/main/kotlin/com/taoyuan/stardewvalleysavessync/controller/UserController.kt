package com.taoyuan.stardewvalleysavessync.controller

import com.sctianji.playserver.annotation.AllowAnonymous
import com.taoyuan.stardewvalleysavessync.model.LoginDTO
import com.taoyuan.stardewvalleysavessync.model.LoginVo
import com.taoyuan.stardewvalleysavessync.service.UserService
import jakarta.annotation.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    @Resource
    private val userService: UserService
) {

    @PostMapping("/login")
    @AllowAnonymous
    fun login(@RequestBody loginDTO: LoginDTO): LoginVo {
        return userService.login(loginDTO.name, loginDTO.password)
    }

    @PostMapping("/register")
    @AllowAnonymous
    fun register(@RequestBody loginDTO: LoginDTO) {
        userService.register(loginDTO.name, loginDTO.password)
    }

    @GetMapping("/refreshToken")
    @AllowAnonymous
    fun refreshToken(@RequestParam refreshToken: String): LoginVo {
        return userService.refreshToken(refreshToken)
    }
}