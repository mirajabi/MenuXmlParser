package com.miaadrajabi.menuxmlparser.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miaadrajabi.menuxmlparser.data.repository.MenuRepository
import com.miaadrajabi.menuxmlparser.domain.model.DynamicMenus
import com.miaadrajabi.menuxmlparser.domain.model.MenuGroup
import com.miaadrajabi.menuxmlparser.domain.model.MenuItem
import com.miaadrajabi.menuxmlparser.domain.model.ServiceDescriptor
import com.miaadrajabi.menuxmlparser.domain.model.TopLevelMenu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class MenuLevel {
    object RootGroups : MenuLevel()
    data class TopMenus(val group: MenuGroup) : MenuLevel()
    data class Operators(val top: TopLevelMenu, val selectedOperatorId: String?) : MenuLevel()
    data class Amounts(val top: TopLevelMenu, val operator: MenuItem) : MenuLevel()
}

data class MenuUiState(
    val level: MenuLevel = MenuLevel.RootGroups,
    val groups: List<MenuGroup> = emptyList(),
    val topMenus: List<TopLevelMenu> = emptyList(),
    val operators: List<MenuItem> = emptyList(),
    val amounts: List<ServiceDescriptor> = emptyList()
)

class MenuViewModel(private val repository: MenuRepository) : ViewModel() {
    private val _state = MutableStateFlow(MenuUiState())
    val state: StateFlow<MenuUiState> = _state

    private lateinit var all: DynamicMenus

    fun load() {
        viewModelScope.launch {
            all = repository.load()
            _state.value = MenuUiState(
                level = MenuLevel.RootGroups,
                groups = all.groups.filter { it.isEnabled }
            )
        }
    }

    fun openGroup(group: MenuGroup) {
        _state.value = _state.value.copy(
            level = MenuLevel.TopMenus(group),
            topMenus = group.topLevelMenus.filter { it.isEnabled },
            operators = emptyList(),
            amounts = emptyList()
        )
    }

    fun openTop(top: TopLevelMenu) {
        // Some menus have a wrapper item (e.g., "normal_charge") that holds operators as children
        val firstLevel = top.items.filter { it.isEnabled }
        val ops = if (firstLevel.size == 1 && firstLevel.first().children.isNotEmpty()) {
            firstLevel.first().children.filter { it.isEnabled }
        } else firstLevel
        val selected = ops.firstOrNull()
        _state.value = _state.value.copy(
            level = MenuLevel.Operators(top, selected?.id),
            operators = ops,
            amounts = selected?.let { it.children.flatMap { c -> c.services }.ifEmpty { it.services } }
                ?: emptyList()
        )
    }

    fun openOperator(top: TopLevelMenu, operator: MenuItem) {
        val amounts = operator.children.flatMap { it.services }.ifEmpty { operator.services }
        _state.value = _state.value.copy(
            level = MenuLevel.Operators(top, operator.id),
            amounts = amounts
        )
    }

    fun back() {
        when (val lvl = _state.value.level) {
            is MenuLevel.Amounts -> {
                _state.value = _state.value.copy(
                    level = MenuLevel.Operators(lvl.top, lvl.operator.id),
                    amounts = emptyList()
                )
            }
            is MenuLevel.Operators -> {
                // Find parent group that contains this top
                val group = all.groups.firstOrNull { g -> g.topLevelMenus.any { it.id == lvl.top.id } }
                if (group != null) {
                    openGroup(group)
                } else {
                    _state.value = MenuUiState(level = MenuLevel.RootGroups, groups = all.groups)
                }
            }
            is MenuLevel.TopMenus -> {
                _state.value = MenuUiState(
                    level = MenuLevel.RootGroups,
                    groups = all.groups
                )
            }
            MenuLevel.RootGroups -> Unit
        }
    }

    fun persistEdits(newMenus: DynamicMenus) {
        viewModelScope.launch {
            repository.save(newMenus)
            all = newMenus
            _state.value = _state.value.copy(groups = all.groups)
        }
    }
}


