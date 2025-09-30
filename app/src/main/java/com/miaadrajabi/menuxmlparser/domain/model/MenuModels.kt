package com.miaadrajabi.menuxmlparser.domain.model

data class DynamicMenus(
    val groups: List<MenuGroup>
)

data class MenuGroup(
    val id: String,
    val englishTitle: String,
    val persianTitle: String,
    val isEnabled: Boolean,
    val icon: String?,
    val topLevelMenus: List<TopLevelMenu>
)

data class TopLevelMenu(
    val id: String,
    val englishTitle: String,
    val persianTitle: String,
    val icon: String?,
    val isEnabled: Boolean,
    val isExpandable: Boolean?,
    val items: List<MenuItem>,
    val services: List<ServiceDescriptor>
)

data class MenuItem(
    val id: String,
    val englishTitle: String,
    val persianTitle: String,
    val isEnabled: Boolean,
    val clazz: String?,
    val ussdGetSimCharge: String?,
    val ussdGetSimNum: String?,
    val usssdChargeCommand: String?,
    val children: List<MenuItem>,
    val services: List<ServiceDescriptor>
)

data class ServiceDescriptor(
    val id: String,
    val service: String,
    val serviceAmount: String?,
    val categoryId: String?,
    val hasCount: Boolean?,
    val load: String?,
    val providerId: String?,
    val support: String?,
    val algorithmType: String?,
    val companyNameEn: String?,
    val companyNameFa: String?
)


