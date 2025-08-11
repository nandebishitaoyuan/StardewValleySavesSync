package com.taoyuan.stardewvalleysavessync.context

import com.alibaba.ttl.TransmittableThreadLocal
import com.taoyuan.stardewvalleysavessync.model.User

class UserContext {
    companion object {
        private val context = TransmittableThreadLocal<User>()

        fun setUser(user: User) {
            context.set(user)
        }

        fun getUser(): User {
            return context.get()
        }

        fun clearContext() {
            context.remove()
        }
    }
}