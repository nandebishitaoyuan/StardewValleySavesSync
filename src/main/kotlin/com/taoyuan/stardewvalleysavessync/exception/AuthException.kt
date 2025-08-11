package com.taoyuan.stardewvalleysavessync.exception

import java.text.MessageFormat

class AuthException(
    override val message: String,
    vararg args: Any?
): RuntimeException(RuntimeException(MessageFormat.format(message, args))) {

    constructor(message: String): this(message, *arrayOfNulls(0)) {
        RuntimeException(MessageFormat.format(message))
    }
}