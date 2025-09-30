package com.miaadrajabi.menuxmlparser.java.data.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "DynamicMenus")
@JsonIgnoreProperties(ignoreUnknown = true)
public class XmlModels {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "MenuGroup")
    public List<MenuGroupXml> menuGroups = new ArrayList<>();

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MenuGroupXml {
        @JacksonXmlProperty(isAttribute = true, localName = "class") public String clazz;
        @JacksonXmlProperty(isAttribute = true, localName = "en") public String enTitle;
        @JacksonXmlProperty(isAttribute = true, localName = "enable") public String enable;
        @JacksonXmlProperty(isAttribute = true, localName = "fn") public String faTitle;
        @JacksonXmlProperty(isAttribute = true, localName = "id") public String id;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "TopLevelMenu")
        public List<TopLevelMenuXml> topLevelMenus = new ArrayList<>();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TopLevelMenuXml {
        @JacksonXmlProperty(isAttribute = true, localName = "en") public String enTitle;
        @JacksonXmlProperty(isAttribute = true, localName = "enable") public String enable;
        @JacksonXmlProperty(isAttribute = true, localName = "expandable") public String expandable;
        @JacksonXmlProperty(isAttribute = true, localName = "fn") public String faTitle;
        @JacksonXmlProperty(isAttribute = true, localName = "icon") public String icon;
        @JacksonXmlProperty(isAttribute = true, localName = "id") public String id;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "ServiceDescriptor")
        public List<ServiceDescriptorXml> services = new ArrayList<>();

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "MenuItem")
        public List<MenuItemXml> items = new ArrayList<>();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MenuItemXml {
        @JacksonXmlProperty(isAttribute = true, localName = "class") public String clazz;
        @JacksonXmlProperty(isAttribute = true, localName = "en") public String enTitle;
        @JacksonXmlProperty(isAttribute = true, localName = "enable") public String enable;
        @JacksonXmlProperty(isAttribute = true, localName = "fn") public String faTitle;
        @JacksonXmlProperty(isAttribute = true, localName = "id") public String id;
        @JacksonXmlProperty(isAttribute = true, localName = "ussdGetSimCharge") public String ussdGetSimCharge;
        @JacksonXmlProperty(isAttribute = true, localName = "ussdGetSimNum") public String ussdGetSimNum;
        @JacksonXmlProperty(isAttribute = true, localName = "usssdChargeCommand") public String usssdChargeCommand;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "MenuItem")
        public List<MenuItemXml> children = new ArrayList<>();

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "ServiceDescriptor")
        public List<ServiceDescriptorXml> services = new ArrayList<>();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ServiceDescriptorXml {
        @JacksonXmlProperty(isAttribute = true, localName = "id") public String id;
        @JacksonXmlProperty(isAttribute = true, localName = "service") public String service;
        @JacksonXmlProperty(isAttribute = true, localName = "serviceAmount") public String serviceAmount;
        @JacksonXmlProperty(isAttribute = true, localName = "categoryId") public String categoryId;
        @JacksonXmlProperty(isAttribute = true, localName = "hasCount") public String hasCount;
        @JacksonXmlProperty(isAttribute = true, localName = "load") public String load;
        @JacksonXmlProperty(isAttribute = true, localName = "providerId") public String providerId;
        @JacksonXmlProperty(isAttribute = true, localName = "support") public String support;
        @JacksonXmlProperty(isAttribute = true, localName = "algorithmType") public String algorithmType;
        @JacksonXmlProperty(isAttribute = true, localName = "companyNameEn") public String companyNameEn;
        @JacksonXmlProperty(isAttribute = true, localName = "companyNameFa") public String companyNameFa;
    }
}
