package com.taoyuan.stardewvalleysavessync.controller

import cn.hutool.core.io.FileUtil.file
import com.taoyuan.stardewvalleysavessync.model.SaveVo
import com.taoyuan.stardewvalleysavessync.service.SavesSyncService
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File

@RestController
@CrossOrigin
@RequestMapping("/sync")
class SyncController(
    @Resource
    private val savesSyncService: SavesSyncService
) {

    @PostMapping("/save")
    fun save(@RequestPart("file") file: MultipartFile) {
        savesSyncService.save(file)
    }

    @GetMapping("/list")
    fun list(): List<SaveVo> {
        return savesSyncService.getSaveList()
    }

    @GetMapping("/delete")
    fun delete(@RequestParam("name") name: String) {
        savesSyncService.deleteSave(name)
    }

    @GetMapping("/download")
    fun download(@RequestParam("name") name: String, response: HttpServletResponse) {
        val save: File = savesSyncService.getSave(name)
        response.setContentLengthLong(save.length())
        response.contentType = "application/octet-stream"
        response.setHeader("Content-Disposition", "attachment; filename=${save.name}")
        response.outputStream.write(save.readBytes())
        response.flushBuffer()
    }

}