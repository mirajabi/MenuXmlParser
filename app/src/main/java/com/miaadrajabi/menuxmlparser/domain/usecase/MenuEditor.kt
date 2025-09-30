package com.miaadrajabi.menuxmlparser.domain.usecase

import com.miaadrajabi.menuxmlparser.domain.model.*

class MenuEditor {
    fun addGroup(menus: DynamicMenus, group: MenuGroup): DynamicMenus =
        menus.copy(groups = menus.groups + group)

    fun removeGroup(menus: DynamicMenus, groupId: String): DynamicMenus =
        menus.copy(groups = menus.groups.filterNot { it.id == groupId })

    fun updateGroup(menus: DynamicMenus, updated: MenuGroup): DynamicMenus =
        menus.copy(groups = menus.groups.map { if (it.id == updated.id) updated else it })

    fun addTopMenu(menus: DynamicMenus, groupId: String, top: TopLevelMenu): DynamicMenus =
        menus.copy(groups = menus.groups.map { g ->
            if (g.id == groupId) g.copy(topLevelMenus = g.topLevelMenus + top) else g
        })

    fun updateTopMenu(menus: DynamicMenus, groupId: String, top: TopLevelMenu): DynamicMenus =
        menus.copy(groups = menus.groups.map { g ->
            if (g.id == groupId) g.copy(topLevelMenus = g.topLevelMenus.map { if (it.id == top.id) top else it }) else g
        })

    fun removeTopMenu(menus: DynamicMenus, groupId: String, topId: String): DynamicMenus =
        menus.copy(groups = menus.groups.map { g ->
            if (g.id == groupId) g.copy(topLevelMenus = g.topLevelMenus.filterNot { it.id == topId }) else g
        })

    fun addOperator(menus: DynamicMenus, topId: String, operator: MenuItem): DynamicMenus =
        menus.copy(groups = menus.groups.map { g ->
            g.copy(topLevelMenus = g.topLevelMenus.map { t ->
                if (t.id == topId) t.copy(items = t.items + operator) else t
            })
        })

    fun updateOperator(menus: DynamicMenus, topId: String, operator: MenuItem): DynamicMenus =
        menus.copy(groups = menus.groups.map { g ->
            g.copy(topLevelMenus = g.topLevelMenus.map { t ->
                if (t.id == topId) t.copy(items = t.items.map { if (it.id == operator.id) operator else it }) else t
            })
        })

    fun removeOperator(menus: DynamicMenus, topId: String, operatorId: String): DynamicMenus =
        menus.copy(groups = menus.groups.map { g ->
            g.copy(topLevelMenus = g.topLevelMenus.map { t ->
                if (t.id == topId) t.copy(items = t.items.filterNot { it.id == operatorId }) else t
            })
        })
}


