package com.miaadrajabi.menuxmlparser.java.presentation.menu;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.miaadrajabi.menuxmlparser.R;
import com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class OperatorAdapter extends RecyclerView.Adapter<OperatorAdapter.Holder> {
    public interface OnClick { void onClick(MenuItem operator); }
    private final OnClick onClick;
    private final List<MenuItem> items = new ArrayList<>();
    private String selectedId = null;

    public OperatorAdapter(OnClick onClick) { this.onClick = onClick; }

    public void submit(List<MenuItem> list, String selected) {
        items.clear();
        if (list != null) items.addAll(list);
        selectedId = selected;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Chip chip = (Chip) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_operator_chip, parent, false);
        return new Holder(chip);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        MenuItem item = items.get(position);
        holder.bind(item, item.id.equals(selectedId));
        holder.itemView.setOnClickListener(v -> onClick.onClick(item));
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class Holder extends RecyclerView.ViewHolder {
        private final Chip chip;
        Holder(@NonNull Chip itemView) { super(itemView); this.chip = itemView; }
        void bind(MenuItem item, boolean selected) {
            chip.setText(item.persianTitle);
            chip.setChecked(selected);
        }
    }
}
