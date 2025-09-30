package com.miaadrajabi.menuxmlparser.java.presentation.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miaadrajabi.menuxmlparser.R;
import com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.ServiceDescriptor;

import java.util.ArrayList;
import java.util.List;

public class AmountAdapter extends RecyclerView.Adapter<AmountAdapter.Holder> {
    public interface OnClick { void onClick(ServiceDescriptor sd); }
    private final OnClick onClick;
    private final List<ServiceDescriptor> items = new ArrayList<>();

    public AmountAdapter(OnClick onClick) { this.onClick = onClick; }

    public void submit(List<ServiceDescriptor> list) {
        items.clear();
        if (list != null) items.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ServiceDescriptor item = items.get(position);
        holder.bind(item);
        holder.itemView.setOnClickListener(v -> onClick.onClick(item));
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class Holder extends RecyclerView.ViewHolder {
        private final TextView title;
        Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
        void bind(ServiceDescriptor item) {
            String t = item.serviceAmount != null ? item.serviceAmount : item.service;
            title.setText(t);
        }
    }
}
