package com.miaadrajabi.menuxmlparser.presentation.menu

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miaadrajabi.menuxmlparser.R
import com.miaadrajabi.menuxmlparser.data.repository.MenuRepository
import com.miaadrajabi.menuxmlparser.data.source.AssetsXmlDataSource
import com.miaadrajabi.menuxmlparser.data.source.LocalXmlDataSource
import kotlinx.coroutines.flow.StateFlow

class MenuActivity : ComponentActivity() {
    private val viewModel: MenuViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repo = MenuRepository(AssetsXmlDataSource(this@MenuActivity), LocalXmlDataSource(this@MenuActivity))
                @Suppress("UNCHECKED_CAST")
                return MenuViewModel(repo) as T
            }
        }
    }

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: MenuAdapter
    private lateinit var operatorsRecycler: RecyclerView
    private lateinit var amountsRecycler: RecyclerView
    private lateinit var operatorAdapter: OperatorAdapter
    private lateinit var amountAdapter: AmountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = MenuAdapter(
            onGroupClick = { viewModel.openGroup(it) },
            onTopClick = { viewModel.openTop(it) },
            onOperatorClick = { top, operator -> viewModel.openOperator(top, operator) }
        )
        recycler.adapter = adapter

        operatorsRecycler = findViewById(R.id.operatorsRecycler)
        operatorsRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        operatorAdapter = OperatorAdapter { item ->
            val level = viewModel.state.value.level
            if (level is MenuLevel.Operators) {
                viewModel.openOperator(level.top, item)
            }
        }
        operatorsRecycler.adapter = operatorAdapter

        amountsRecycler = findViewById(R.id.amountsRecycler)
        amountsRecycler.layoutManager = LinearLayoutManager(this)
        amountAdapter = AmountAdapter { sd ->
            val st = viewModel.state.value
            val lvl = st.level
            if (lvl is MenuLevel.Operators) {
                val op = st.operators.firstOrNull { it.id == lvl.selectedOperatorId }
                if (op != null) {
                    val info = buildChargeInfoMap(lvl.top, op, sd)
                    Log.d(TAG, "ChargeSelected: $info")
                    Toast.makeText(this, "Selected: ${info["amount"]}", Toast.LENGTH_SHORT).show()
                    // Here you can pass 'info' to next screen or payment flow
                }
            }
        }
        amountsRecycler.adapter = amountAdapter

        viewModel.state.collectIn(this) { state ->
            adapter.submit(state)
            if (state.level is MenuLevel.Operators) {
                operatorsRecycler.visibility = RecyclerView.VISIBLE
                amountsRecycler.visibility = RecyclerView.VISIBLE
                recycler.visibility = RecyclerView.GONE
                val selectedId = (state.level as MenuLevel.Operators).selectedOperatorId
                operatorAdapter.submit(state.operators, selectedId)
                amountAdapter.submit(state.amounts)
            } else {
                operatorsRecycler.visibility = RecyclerView.GONE
                amountsRecycler.visibility = RecyclerView.GONE
                recycler.visibility = RecyclerView.VISIBLE
            }
        }

        viewModel.load()
    }
}

private const val TAG = "MenuActivity"

private fun buildChargeInfoMap(
    top: com.miaadrajabi.menuxmlparser.domain.model.TopLevelMenu,
    operator: com.miaadrajabi.menuxmlparser.domain.model.MenuItem,
    sd: com.miaadrajabi.menuxmlparser.domain.model.ServiceDescriptor
): Map<String, String> = buildMap {
    put("service", sd.service)
    sd.serviceAmount?.let { put("amount", it) }
    sd.categoryId?.let { put("categoryId", it) }
    sd.providerId?.let { put("providerId", it) }
    sd.support?.let { put("support", it) }
    sd.load?.let { put("load", it) }
    put("topId", top.id)
    put("topFa", top.persianTitle)
    operator.ussdGetSimCharge?.let { put("ussdGetSimCharge", it) }
    operator.ussdGetSimNum?.let { put("ussdGetSimNum", it) }
    operator.usssdChargeCommand?.let { put("usssdChargeCommand", it) }
    put("operatorId", operator.id)
    put("operatorFa", operator.persianTitle)
    put("operatorEn", operator.englishTitle)
}

private fun StateFlow<MenuUiState>.collectIn(
    activity: ComponentActivity,
    block: (MenuUiState) -> Unit
) {
    activity.lifecycle.addObserver(FlowObserver(activity.lifecycle, this, block))
}


