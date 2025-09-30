package com.miaadrajabi.menuxmlparser.data.xml

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "DynamicMenus")
@JsonIgnoreProperties(ignoreUnknown = true)
data class DynamicMenusXml(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "MenuGroup")
    val menuGroups: List<MenuGroupXml> = emptyList()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MenuGroupXml(
    @JacksonXmlProperty(isAttribute = true, localName = "class") val clazz: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "en") val enTitle: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "enable") val enable: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "fn") val faTitle: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "id") val id: String? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "TopLevelMenu")
    val topLevelMenus: List<TopLevelMenuXml> = emptyList()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TopLevelMenuXml(
    @JacksonXmlProperty(isAttribute = true, localName = "en") val enTitle: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "enable") val enable: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "expandable") val expandable: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "fn") val faTitle: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "icon") val icon: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "id") val id: String? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ServiceDescriptor")
    val services: List<ServiceDescriptorXml> = emptyList(),

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "MenuItem")
    val items: List<MenuItemXml> = emptyList()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class MenuItemXml(
    @JacksonXmlProperty(isAttribute = true, localName = "class") val clazz: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "en") val enTitle: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "enable") val enable: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "fn") val faTitle: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "id") val id: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "ussdGetSimCharge") val ussdGetSimCharge: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "ussdGetSimNum") val ussdGetSimNum: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "usssdChargeCommand") val usssdChargeCommand: String? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "MenuItem")
    val children: List<MenuItemXml> = emptyList(),

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ServiceDescriptor")
    val services: List<ServiceDescriptorXml> = emptyList()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ServiceDescriptorXml(
    @JacksonXmlProperty(isAttribute = true, localName = "id") val id: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "service") val service: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "serviceAmount") val serviceAmount: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "categoryId") val categoryId: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "hasCount") val hasCount: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "load") val load: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "providerId") val providerId: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "support") val support: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "algorithmType") val algorithmType: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "companyNameEn") val companyNameEn: String? = null,
    @JacksonXmlProperty(isAttribute = true, localName = "companyNameFa") val companyNameFa: String? = null
)


