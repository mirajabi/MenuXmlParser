package com.miaadrajabi.menuxmlparser.presentation.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.miaadrajabi.menuxmlparser.R
import com.miaadrajabi.menuxmlparser.domain.model.MenuItem

class OperatorAdapter(
    private val onClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<OperatorAdapter.Holder>() {
    private var items: List<MenuItem> = emptyList()
    private var selectedId: String? = null

    fun submit(newItems: List<MenuItem>, selected: String?) {
        items = newItems
        selectedId = selected
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_operator_chip, parent, false) as Chip
        return Holder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]
        holder.bind(item, item.id == selectedId)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    class Holder(private val chip: Chip) : RecyclerView.ViewHolder(chip) {
        fun bind(item: MenuItem, isSelected: Boolean) {
            chip.text = item.persianTitle
            chip.isChecked = isSelected
        }
    }
}


