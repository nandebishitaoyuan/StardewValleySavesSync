package com.taoyuan.stardewvalleysavessync

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StardewValleySavesSyncApplication


fun main(args: Array<String>) {
    runApplication<StardewValleySavesSyncApplication>(*args)
    val logger = LoggerFactory.getLogger(StardewValleySavesSyncApplication::class.java)
    logger.info("应用启动成功！")
}
