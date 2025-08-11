package com.taoyuan.stardewvalleysavessync.utils

class StringUtils {
    companion object {
        fun isEmpty(str: String?): Boolean {
            return str == null || str.isEmpty()
        }
        fun isNotEmpty(str: String?): Boolean {
            return !isEmpty(str)
        }
        fun isBlank(str: String?): Boolean {
            return str == null || str.isBlank()
        }
        fun isNotBlank(str: String?): Boolean {
            return !isBlank(str)
        }
    }
}