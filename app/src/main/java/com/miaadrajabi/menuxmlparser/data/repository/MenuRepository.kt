package com.miaadrajabi.menuxmlparser.data.repository

import com.miaadrajabi.menuxmlparser.data.mapper.toDomain
import com.miaadrajabi.menuxmlparser.data.source.AssetsXmlDataSource
import com.miaadrajabi.menuxmlparser.data.source.LocalXmlDataSource
import com.miaadrajabi.menuxmlparser.data.xml.DynamicMenusXml
import com.miaadrajabi.menuxmlparser.domain.model.DynamicMenus
import com.miaadrajabi.menuxmlparser.domain.model.MenuGroup

class MenuRepository(
    private val assets: AssetsXmlDataSource,
    private val local: LocalXmlDataSource
) {
    fun load(): DynamicMenus {
        val fromLocal = local.read()
        val xml: DynamicMenusXml = fromLocal ?: assets.read()
        return xml.toDomain()
    }

    fun save(domain: DynamicMenus) {
        // Convert domain back to XML DTO shape
        val xml = domain.toXml()
        local.write(xml)
    }
}

// Reverse mappers for persistence
private fun DynamicMenus.toXml(): DynamicMenusXml = DynamicMenusXml(
    menuGroups = groups.map { it.toXml() }
)

private fun MenuGroup.toXml(): com.miaadrajabi.menuxmlparser.data.xml.MenuGroupXml {
    val topLevel = topLevelMenus.map { it.toXml() }
    return com.miaadrajabi.menuxmlparser.data.xml.MenuGroupXml(
        clazz = null,
        enTitle = englishTitle,
        enable = isEnabled.toString(),
        faTitle = persianTitle,
        id = id,
        topLevelMenus = topLevel
    )
}

private fun com.miaadrajabi.menuxmlparser.domain.model.TopLevelMenu.toXml(): com.miaadrajabi.menuxmlparser.data.xml.TopLevelMenuXml =
    com.miaadrajabi.menuxmlparser.data.xml.TopLevelMenuXml(
        enTitle = englishTitle,
        enable = isEnabled.toString(),
        expandable = isExpandable?.toString(),
        faTitle = persianTitle,
        icon = icon,
        id = id,
        services = services.map { it.toXml() },
        items = items.map { it.toXml() }
    )

private fun com.miaadrajabi.menuxmlparser.domain.model.MenuItem.toXml(): com.miaadrajabi.menuxmlparser.data.xml.MenuItemXml =
    com.miaadrajabi.menuxmlparser.data.xml.MenuItemXml(
        clazz = clazz,
        enTitle = englishTitle,
        enable = isEnabled.toString(),
        faTitle = persianTitle,
        id = id,
        ussdGetSimCharge = ussdGetSimCharge,
        ussdGetSimNum = ussdGetSimNum,
        usssdChargeCommand = usssdChargeCommand,
        children = children.map { it.toXml() },
        services = services.map { it.toXml() }
    )

private fun com.miaadrajabi.menuxmlparser.domain.model.ServiceDescriptor.toXml(): com.miaadrajabi.menuxmlparser.data.xml.ServiceDescriptorXml =
    com.miaadrajabi.menuxmlparser.data.xml.ServiceDescriptorXml(
        id = id,
        service = service,
        serviceAmount = serviceAmount,
        categoryId = categoryId,
        hasCount = hasCount?.toString(),
        load = load,
        providerId = providerId,
        support = support,
        algorithmType = algorithmType,
        companyNameEn = companyNameEn,
        companyNameFa = companyNameFa
    )


