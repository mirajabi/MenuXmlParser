package com.miaadrajabi.menuxmlparser.data.source

import android.content.Context
import com.miaadrajabi.menuxmlparser.data.xml.DynamicMenusXml
import com.miaadrajabi.menuxmlparser.data.xml.XmlConfig

class AssetsXmlDataSource(private val context: Context) {
    fun read(filename: String = DEFAULT_FILE): DynamicMenusXml {
        context.assets.open(filename).use { input ->
            return XmlConfig.xmlMapper.readValue(input, DynamicMenusXml::class.java)
        }
    }

    companion object {
        const val DEFAULT_FILE = "dynamic_menus.xml"
    }
}


