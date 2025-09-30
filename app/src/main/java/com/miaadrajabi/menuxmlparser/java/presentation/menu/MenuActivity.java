package com.miaadrajabi.menuxmlparser.java.presentation.menu;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miaadrajabi.menuxmlparser.R;
import com.miaadrajabi.menuxmlparser.java.data.repository.MenuRepository;
import com.miaadrajabi.menuxmlparser.java.data.source.AssetsXmlDataSource;
import com.miaadrajabi.menuxmlparser.java.data.source.LocalXmlDataSource;
import com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.*;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends ComponentActivity {
    private MenuViewModel viewModel;
    private RecyclerView recycler;
    private MenuAdapter adapter;
    private RecyclerView operatorsRecycler;
    private RecyclerView amountsRecycler;
    private OperatorAdapter operatorAdapter;
    private AmountAdapter amountAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull @Override public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                MenuRepository repo = new MenuRepository(new AssetsXmlDataSource(MenuActivity.this), new LocalXmlDataSource(MenuActivity.this));
                //noinspection unchecked
                return (T) new MenuViewModel(repo);
            }
        }).get(MenuViewModel.class);

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MenuAdapter(
                group -> viewModel.openGroup(group),
                top -> viewModel.openTop(top),
                (top, operator) -> viewModel.openOperator(top, operator)
        );
        recycler.setAdapter(adapter);

        operatorsRecycler = findViewById(R.id.operatorsRecycler);
        operatorsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        operatorAdapter = new OperatorAdapter(operator -> {
            MenuViewModel.MenuUiState level = viewModel.getState().getValue();
            if (level != null && level.level == MenuViewModel.Level.OPERATORS) {
                viewModel.openOperator(null, operator);
            }
        });
        operatorsRecycler.setAdapter(operatorAdapter);

        amountsRecycler = findViewById(R.id.amountsRecycler);
        amountsRecycler.setLayoutManager(new LinearLayoutManager(this));
        amountAdapter = new AmountAdapter(this::onAmountClick);
        amountsRecycler.setAdapter(amountAdapter);

        viewModel.getState().observe(this, this::render);
        viewModel.load();
    }

    private void render(MenuViewModel.MenuUiState state) {
        if (state.level == MenuViewModel.Level.OPERATORS) {
            operatorsRecycler.setVisibility(RecyclerView.VISIBLE);
            amountsRecycler.setVisibility(RecyclerView.VISIBLE);
            recycler.setVisibility(RecyclerView.GONE);
            operatorAdapter.submit(state.operators, state.selectedOperatorId);
            amountAdapter.submit(state.amounts);
        } else {
            operatorsRecycler.setVisibility(RecyclerView.GONE);
            amountsRecycler.setVisibility(RecyclerView.GONE);
            recycler.setVisibility(RecyclerView.VISIBLE);
            adapter.submit(state);
        }
    }

    private void onAmountClick(ServiceDescriptor sd) {
        MenuViewModel.MenuUiState st = viewModel.getState().getValue();
        if (st == null) return;
        if (st.level == MenuViewModel.Level.OPERATORS) {
            MenuItem operator = null;
            for (MenuItem it : st.operators) {
                if (it.id.equals(st.selectedOperatorId)) { 
                    operator = it; 
                    break; 
                }
            }
            if (operator != null) {
                Map<String, String> info = buildChargeInfoMap(null, operator, sd);
                Log.d(TAG, "ChargeSelected: " + info);
                Toast.makeText(this, "Selected: " + info.get("amount"), Toast.LENGTH_SHORT).show();
                // Here you can pass 'info' to next screen or payment flow
            }
        }
    }

    private static final String TAG = "JavaMenuActivity";

    private static Map<String, String> buildChargeInfoMap(
            TopLevelMenu top,
            MenuItem operator,
            ServiceDescriptor sd
    ) {
        Map<String, String> map = new HashMap<>();
        map.put("service", sd.service);
        if (sd.serviceAmount != null) map.put("amount", sd.serviceAmount);
        if (sd.categoryId != null) map.put("categoryId", sd.categoryId);
        if (sd.providerId != null) map.put("providerId", sd.providerId);
        if (sd.support != null) map.put("support", sd.support);
        if (sd.load != null) map.put("load", sd.load);
        if (top != null) {
            map.put("topId", top.id);
            map.put("topFa", top.persianTitle);
        }
        if (operator.ussdGetSimCharge != null) map.put("ussdGetSimCharge", operator.ussdGetSimCharge);
        if (operator.ussdGetSimNum != null) map.put("ussdGetSimNum", operator.ussdGetSimNum);
        if (operator.usssdChargeCommand != null) map.put("usssdChargeCommand", operator.usssdChargeCommand);
        map.put("operatorId", operator.id);
        map.put("operatorFa", operator.persianTitle);
        map.put("operatorEn", operator.englishTitle);
        return map;
    }
}
