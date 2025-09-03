package com.taoyuan.stardewvalleysavessync.service

import com.taoyuan.stardewvalleysavessync.model.SaveVo
import org.springframework.web.multipart.MultipartFile
import java.io.File

interface SavesSyncService {
    /**
     * 保存存档
     */
    fun save(file: MultipartFile)

    /**
     * 获取存档列表
     */
    fun getSaveList(): List<SaveVo>

    /**
     * 删除存档
     */
    fun deleteSave(name: String)

    /**
     * 获取存档
     */
    fun getSave(name: String): File
}