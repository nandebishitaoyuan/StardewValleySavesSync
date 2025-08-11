package com.sctianji.playserver.exception

import java.text.MessageFormat

class ParamException(
    override val message: String,
    vararg args: Any?
): RuntimeException(RuntimeException(MessageFormat.format(message, args))) {

    constructor(message: String): this(message, *arrayOfNulls(0)) {
        RuntimeException(MessageFormat.format(message))
    }
}