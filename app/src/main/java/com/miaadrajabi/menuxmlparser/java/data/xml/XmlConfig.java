package com.miaadrajabi.menuxmlparser.java.data.xml;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;

public class XmlConfig {
    private static volatile XmlMapper mapper;
    
    public static XmlMapper getXmlMapper() {
        if (mapper == null) {
            synchronized (XmlConfig.class) {
                if (mapper == null) {
                    JacksonXmlModule module = new JacksonXmlModule();
                    module.setDefaultUseWrapper(false);
                    mapper = new XmlMapper(module);
                    mapper.registerModule(new KotlinModule.Builder().build());
                    mapper.findAndRegisterModules();
                }
            }
        }
        return mapper;
    }
}
