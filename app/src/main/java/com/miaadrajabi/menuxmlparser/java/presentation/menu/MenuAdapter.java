package com.miaadrajabi.menuxmlparser.java.presentation.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miaadrajabi.menuxmlparser.R;
import com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.*;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.Holder> {

    public interface OnGroupClickListener { void onGroupClick(MenuGroup group); }
    public interface OnTopClickListener { void onTopClick(TopLevelMenu top); }
    public interface OnOperatorClickListener { void onOperatorClick(TopLevelMenu top, MenuItem operator); }

    private final OnGroupClickListener onGroupClick;
    private final OnTopClickListener onTopClick;
    private final OnOperatorClickListener onOperatorClick;

    private MenuViewModel.MenuUiState state = new MenuViewModel.MenuUiState(MenuViewModel.Level.GROUPS, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null);

    public MenuAdapter(OnGroupClickListener onGroupClick, OnTopClickListener onTopClick, OnOperatorClickListener onOperatorClick) {
        this.onGroupClick = onGroupClick;
        this.onTopClick = onTopClick;
        this.onOperatorClick = onOperatorClick;
    }

    public void submit(MenuViewModel.MenuUiState newState) {
        state = newState;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        switch (state.level) {
            case GROUPS: return state.groups.size();
            case TOPS: return state.topMenus.size();
            case OPERATORS: return state.operators.size();
            default: return state.amounts.size();
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        switch (state.level) {
            case GROUPS:
                MenuGroup group = state.groups.get(position);
                holder.bindTitle(group.persianTitle);
                holder.itemView.setOnClickListener(v -> onGroupClick.onGroupClick(group));
                break;
            case TOPS:
                TopLevelMenu top = state.topMenus.get(position);
                holder.bindTitle(top.persianTitle);
                holder.itemView.setOnClickListener(v -> onTopClick.onTopClick(top));
                break;
            case OPERATORS:
                MenuItem operator = state.operators.get(position);
                holder.bindTitle(operator.persianTitle);
                holder.itemView.setOnClickListener(v -> {
                    // Need to find the TopLevelMenu for this operator
                    // For simplicity, pass null - the ViewModel will handle it
                    onOperatorClick.onOperatorClick(null, operator);
                });
                break;
            default: // AMOUNTS
                ServiceDescriptor amount = state.amounts.get(position);
                holder.bindTitle(amount.serviceAmount != null ? amount.serviceAmount : amount.service);
                holder.itemView.setOnClickListener(null);
                break;
        }
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private final TextView title;
        
        public Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
        
        public void bindTitle(String text) { 
            title.setText(text); 
        }
    }
}
