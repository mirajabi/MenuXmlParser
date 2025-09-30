package com.miaadrajabi.menuxmlparser.presentation.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miaadrajabi.menuxmlparser.R
import com.miaadrajabi.menuxmlparser.domain.model.ServiceDescriptor

class AmountAdapter(
    private val onClick: (ServiceDescriptor) -> Unit
) : RecyclerView.Adapter<AmountAdapter.Holder>() {
    private var items: List<ServiceDescriptor> = emptyList()

    fun submit(newItems: List<ServiceDescriptor>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_menu_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onClick(item) }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        fun bind(item: ServiceDescriptor) {
            title.text = item.serviceAmount ?: item.service
        }
    }
}


