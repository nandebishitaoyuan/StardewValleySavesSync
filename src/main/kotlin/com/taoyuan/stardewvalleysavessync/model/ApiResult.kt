package com.taoyuan.stardewvalleysavessync.model

class ApiResult<T> (
    var code: Int,
    var msg: String,
    var data: T?
){

    constructor(code: Int, msg: String): this(code, msg, null)

    companion object {

        fun <T> success(): ApiResult<T> {
            return ApiResult(10000, "success")
        }

        fun <T> success(msg: String): ApiResult<T> {
            return ApiResult(10000, msg)
        }

        fun <T> success(dada: T): ApiResult<T> {
            return ApiResult(10000, "success", dada)
        }

        fun <T> success(msg: String, dada: T): ApiResult<T> {
            return ApiResult(10000, msg, dada)
        }


        fun <T> failure(): ApiResult<T> {
            return ApiResult(20000, "failure")
        }

        fun <T> failure(msg: String): ApiResult<T> {
            return ApiResult(20000, msg)
        }

        fun <T> failure(dada: T): ApiResult<T> {
            return ApiResult(20000, "failure", dada)
        }

        fun <T> failure(msg: String, dada: T): ApiResult<T> {
            return ApiResult(20000, msg, dada)
        }

    }
}