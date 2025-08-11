package com.taoyuan.stardewvalleysavessync.service.impl

import cn.hutool.json.JSONUtil
import com.taoyuan.stardewvalleysavessync.exception.AuthException
import com.taoyuan.stardewvalleysavessync.model.LoginVo
import com.taoyuan.stardewvalleysavessync.model.User
import com.taoyuan.stardewvalleysavessync.service.UserService
import com.taoyuan.stardewvalleysavessync.utils.JwtUtil
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.stereotype.Service
import org.yaml.snakeyaml.Yaml
import java.io.File
import kotlin.jvm.java

@Service
class UserServiceImpl : UserService {

    private val configDir = File("config")
    private val yamlFile = File(configDir, "users.yml")
    private val yaml = Yaml()

    init {
        if (!configDir.exists()) {
            configDir.mkdirs()
        }
        if (!yamlFile.exists()) {
            yamlFile.createNewFile()
            yaml.dump(emptyList<User>(), yamlFile.writer())
        }
    }

    private fun readUsers(): MutableList<User> {
        if (!yamlFile.exists() || yamlFile.readText().isBlank()) {
            return mutableListOf()
        }
        yamlFile.inputStream().use { input ->
            val list = yaml.load<List<Map<String, Any>>>(input) ?: emptyList()
            return list.map {
                User(
                    id = (it["id"] as Number).toLong(),
                    name = it["name"] as String,
                    password = it["password"] as String
                )
            }.toMutableList()
        }
    }

    private fun writeUsers(users: List<User>) {
        // 转成 List<Map<String, Any>>
        val data = users.map { user ->
            mapOf(
                "id" to user.id,
                "name" to user.name,
                "password" to user.password
            )
        }
        yamlFile.writer().use { writer ->
            yaml.dump(data, writer)
        }
    }

    override fun login(
        userName: String,
        password: String
    ): LoginVo {
        val users = readUsers()
        val user = users.find { it.name == userName && it.password == password }
            ?: throw IllegalArgumentException("用户名或密码错误")

        val map = HashMap<String, Any>()
        user.password = null
        map["user"] = user

        return LoginVo(
            id = user.id,
            name = user.name,
            token = JwtUtil.generateAccessToken(map),
            refreshToken = JwtUtil.generateRefreshToken(map)
        )
    }

    override fun register(userName: String, password: String) {
        val users = readUsers()

        if (users.any { it.name == userName }) {
            throw IllegalArgumentException("用户名已存在")
        }

        val newId = (users.maxOfOrNull { it.id } ?: 0) + 1
        val newUser = User(newId, userName, password)

        users.add(newUser)
        writeUsers(users)
    }

    override fun refreshToken(refreshToken: String): LoginVo {
        try {
            val claims: Claims = JwtUtil.parseToken(refreshToken)
            if (claims["type"] != "refresh") {
                throw AuthException("无效的刷新Token")
            }
            val userStr: Any = claims["user"]!!
            val user  = JSONUtil.toBean(userStr.toString(), User::class.java)


            val map = HashMap<String, Any>()
            map["user"] = user

            return LoginVo(
                id = user.id,
                name = user.name,
                token = JwtUtil.generateAccessToken(map),
                refreshToken = JwtUtil.generateRefreshToken(map)
            )

        } catch (e: ExpiredJwtException) {
            throw AuthException("Token已过期", e)
        }
    }

}