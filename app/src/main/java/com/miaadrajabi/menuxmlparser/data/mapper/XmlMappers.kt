package com.miaadrajabi.menuxmlparser.data.mapper

import com.miaadrajabi.menuxmlparser.data.xml.*
import com.miaadrajabi.menuxmlparser.domain.model.*

fun DynamicMenusXml.toDomain(): DynamicMenus = DynamicMenus(
    groups = menuGroups.map { it.toDomain() }
)

fun MenuGroupXml.toDomain(): MenuGroup = MenuGroup(
    id = id.orEmpty(),
    englishTitle = enTitle.orEmpty(),
    persianTitle = faTitle.orEmpty(),
    isEnabled = enable.equals("true", ignoreCase = true),
    icon = topLevelMenus.firstOrNull()?.icon,
    topLevelMenus = topLevelMenus.map { it.toDomain() }
)

fun TopLevelMenuXml.toDomain(): TopLevelMenu = TopLevelMenu(
    id = id.orEmpty(),
    englishTitle = enTitle.orEmpty(),
    persianTitle = faTitle.orEmpty(),
    icon = icon,
    isEnabled = enable.equals("true", ignoreCase = true),
    isExpandable = expandable?.let { it.equals("true", ignoreCase = true) },
    items = items.map { it.toDomain() },
    services = services.map { it.toDomain() }
)

fun MenuItemXml.toDomain(): MenuItem = MenuItem(
    id = id.orEmpty(),
    englishTitle = enTitle.orEmpty(),
    persianTitle = faTitle.orEmpty(),
    isEnabled = enable.equals("true", ignoreCase = true),
    clazz = clazz,
    ussdGetSimCharge = ussdGetSimCharge,
    ussdGetSimNum = ussdGetSimNum,
    usssdChargeCommand = usssdChargeCommand,
    children = children.map { it.toDomain() },
    services = services.map { it.toDomain() }
)

fun ServiceDescriptorXml.toDomain(): ServiceDescriptor = ServiceDescriptor(
    id = id.orEmpty(),
    service = service.orEmpty(),
    serviceAmount = serviceAmount,
    categoryId = categoryId,
    hasCount = hasCount?.let { it.equals("true", ignoreCase = true) },
    load = load,
    providerId = providerId,
    support = support,
    algorithmType = algorithmType,
    companyNameEn = companyNameEn,
    companyNameFa = companyNameFa
)


