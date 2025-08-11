package com.toayuan.stardewvalleysavessync.controller

import com.toayuan.stardewvalleysavessync.service.SavesSyncService
import jakarta.annotation.Resource
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sync")
class SyncController(
    @Resource
    private val savesSyncService: SavesSyncService
) {
}