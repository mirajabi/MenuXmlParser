package com.miaadrajabi.menuxmlparser.data.source

import android.content.Context
import com.miaadrajabi.menuxmlparser.data.xml.DynamicMenusXml
import com.miaadrajabi.menuxmlparser.data.xml.XmlConfig
import java.io.File

class LocalXmlDataSource(private val context: Context) {
    private fun file(path: String = DEFAULT_PATH): File {
        val f = File(context.filesDir, path)
        f.parentFile?.mkdirs()
        return f
    }

    fun read(path: String = DEFAULT_PATH): DynamicMenusXml? {
        val f = file(path)
        if (!f.exists()) return null
        return f.inputStream().use { XmlConfig.xmlMapper.readValue(it, DynamicMenusXml::class.java) }
    }

    fun write(data: DynamicMenusXml, path: String = DEFAULT_PATH) {
        val f = file(path)
        f.outputStream().use { XmlConfig.xmlMapper.writeValue(it, data) }
    }

    companion object {
        const val DEFAULT_PATH = "menus/dynamic_menus.xml"
    }
}


