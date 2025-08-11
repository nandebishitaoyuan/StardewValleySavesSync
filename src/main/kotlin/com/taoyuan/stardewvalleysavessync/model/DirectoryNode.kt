package com.taoyuan.stardewvalleysavessync.model

data class DirectoryNode(
    val name: String,
    val isFile: Boolean,
    val children: MutableList<DirectoryNode> = mutableListOf()
)