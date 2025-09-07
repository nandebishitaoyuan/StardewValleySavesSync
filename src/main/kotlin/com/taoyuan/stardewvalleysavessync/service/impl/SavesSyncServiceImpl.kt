package com.taoyuan.stardewvalleysavessync.service.impl

import cn.hutool.poi.excel.sax.ElementName
import com.taoyuan.stardewvalleysavessync.context.UserContext
import com.taoyuan.stardewvalleysavessync.model.SaveVo
import com.taoyuan.stardewvalleysavessync.service.SavesSyncService
import com.taoyuan.stardewvalleysavessync.utils.ZipUtil
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Service
class SavesSyncServiceImpl : SavesSyncService {

    private val rootDir = System.getProperty("user.dir") + "/saves"

    private val log = LoggerFactory.getLogger(SavesSyncServiceImpl::class.java)

    init {
        val dir = File(rootDir)
        if (!dir.exists()) {
            log.info("saves目录不存在，创建目录 $dir")
            dir.mkdirs()
        }
    }

    /**
     * 保存存档
     */
    override fun save(file: MultipartFile) {
        if (file.isEmpty) return
        try {
            val filePath = "$rootDir/${UserContext.getUser().id}/${file.originalFilename}"
            val savePath = File(filePath)
            // 判断路径是否存在
            if (!savePath.exists()) {
                savePath.parentFile.mkdirs()
            }
            file.transferTo(savePath)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 获取存档列表
     */
    override fun getSaveList(): List<SaveVo> {
        val rootDir = File("$rootDir/${UserContext.getUser().id}") // 当前项目根目录
        return rootDir.listFiles { file -> file.isFile }
            ?.map {
                val fileTime = Files.getLastModifiedTime(it.toPath())
                // 转换为 LocalDateTime
                val dateTime = LocalDateTime.ofInstant(
                    fileTime.toInstant(),
                    ZoneId.systemDefault()
                )

                val name = it.name.split("_")[0]

                SaveVo(name, originalName = it.name, time = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            }
            ?: emptyList()
    }

    /**
     * 删除存档
     */
    override fun deleteSave(name: String) {
        val file = File("$rootDir/${UserContext.getUser().id}/$name")
        if (file.exists() && file.isFile) {
            file.delete()
        }
    }

    override fun getSave(name: String): File {
        val file = File("$rootDir/${UserContext.getUser().id}/$name")
        if (file.exists() && file.isFile) return file
        throw Exception("存档不存在")
    }

}