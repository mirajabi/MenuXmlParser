package com.miaadrajabi.menuxmlparser.data.xml

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.module.kotlin.KotlinModule

object XmlConfig {
    val xmlMapper: XmlMapper by lazy {
        val module = JacksonXmlModule().apply {
            setDefaultUseWrapper(false)
        }
        XmlMapper(module).apply {
            registerModule(KotlinModule.Builder().build())
            findAndRegisterModules()
        }
    }
}


