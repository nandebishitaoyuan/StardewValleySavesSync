package com.taoyuan.stardewvalleysavessync.utils

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


class ZipUtil {

    companion object {
        fun zip(parentDirectory: File, zipFilePath: String) {

            val zipFile = File(zipFilePath)
            zipFile.parentFile?.let {
                if (!it.exists()) {
                    it.mkdirs()
                }
            }

            ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFile))).use { zos ->
                // 从空字符串开始，不加根目录名
                parentDirectory.listFiles()?.forEach { child ->
                    zipFileRecursive(child, child.name, zos)
                }
            }
        }


        /**
         * 递归压缩文件夹
         */
        private fun zipFileRecursive(fileToZip: File, fileName: String, zos: ZipOutputStream) {
            if (fileToZip.isHidden) return

            if (fileToZip.isDirectory) {
                if (fileName.isNotEmpty() && !fileName.endsWith("/")) {
                    zos.putNextEntry(ZipEntry("$fileName/"))
                    zos.closeEntry()
                }
                fileToZip.listFiles()?.forEach { child ->
                    zipFileRecursive(child, "$fileName/${child.name}", zos)
                }
            } else {
                FileInputStream(fileToZip).use { fis ->
                    val zipEntry = ZipEntry(fileName)
                    zos.putNextEntry(zipEntry)
                    fis.copyTo(zos)
                }
            }
        }
    }




}