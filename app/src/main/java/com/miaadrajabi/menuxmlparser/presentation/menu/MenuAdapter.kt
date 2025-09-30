package com.miaadrajabi.menuxmlparser.presentation.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.miaadrajabi.menuxmlparser.R
import com.miaadrajabi.menuxmlparser.domain.model.MenuGroup
import com.miaadrajabi.menuxmlparser.domain.model.MenuItem
import com.miaadrajabi.menuxmlparser.domain.model.ServiceDescriptor
import com.miaadrajabi.menuxmlparser.domain.model.TopLevelMenu

class MenuAdapter(
    private val onGroupClick: (MenuGroup) -> Unit,
    private val onTopClick: (TopLevelMenu) -> Unit,
    private val onOperatorClick: (TopLevelMenu, MenuItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.Holder>() {

    private var state: MenuUiState = MenuUiState()

    fun submit(newState: MenuUiState) {
        state = newState
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = when (state.level) {
        is MenuLevel.RootGroups -> state.groups.size
        is MenuLevel.TopMenus -> state.topMenus.size
        is MenuLevel.Operators -> state.operators.size
        is MenuLevel.Amounts -> state.amounts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_menu_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        when (val lvl = state.level) {
            is MenuLevel.RootGroups -> {
                val item = state.groups[position]
                holder.bindTitle(item.persianTitle)
                holder.itemView.setOnClickListener { onGroupClick(item) }
            }
            is MenuLevel.TopMenus -> {
                val item = state.topMenus[position]
                holder.bindTitle(item.persianTitle)
                holder.itemView.setOnClickListener { onTopClick(item) }
            }
            is MenuLevel.Operators -> {
                val item = state.operators[position]
                holder.bindTitle(item.persianTitle)
                holder.itemView.setOnClickListener { onOperatorClick(lvl.top, item) }
            }
            is MenuLevel.Amounts -> {
                val item = state.amounts[position]
                holder.bindTitle(item.serviceAmount ?: item.service)
                holder.itemView.setOnClickListener(null)
            }
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        fun bindTitle(text: String) { title.text = text }
    }
}


