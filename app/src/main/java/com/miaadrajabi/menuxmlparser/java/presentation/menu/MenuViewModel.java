package com.miaadrajabi.menuxmlparser.java.presentation.menu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.miaadrajabi.menuxmlparser.java.data.repository.MenuRepository;
import com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.*;

import java.util.ArrayList;
import java.util.List;

public class MenuViewModel extends ViewModel {
    public enum Level { GROUPS, TOPS, OPERATORS }

    public static class MenuUiState {
        public final Level level;
        public final List<MenuGroup> groups;
        public final List<TopLevelMenu> topMenus;
        public final List<MenuItem> operators;
        public final List<ServiceDescriptor> amounts;
        public final String selectedOperatorId;

        public MenuUiState(Level level, List<MenuGroup> groups, List<TopLevelMenu> tops,
                     List<MenuItem> operators, List<ServiceDescriptor> amounts, String selectedOperatorId) {
            this.level = level;
            this.groups = groups != null ? groups : new ArrayList<>();
            this.topMenus = tops != null ? tops : new ArrayList<>();
            this.operators = operators != null ? operators : new ArrayList<>();
            this.amounts = amounts != null ? amounts : new ArrayList<>();
            this.selectedOperatorId = selectedOperatorId;
        }
    }

    private final MenuRepository repository;
    private DynamicMenus all;
    private final MutableLiveData<MenuUiState> _state = new MutableLiveData<>();

    public MenuViewModel(MenuRepository repository) { 
        this.repository = repository; 
    }

    public LiveData<MenuUiState> getState() { 
        return _state; 
    }

    public void load() {
        all = repository.load();
        List<MenuGroup> enabledGroups = new ArrayList<>();
        for (MenuGroup g : all.groups) {
            if (g.isEnabled) enabledGroups.add(g);
        }
        _state.postValue(new MenuUiState(Level.GROUPS, enabledGroups, null, null, null, null));
    }

    public void openGroup(MenuGroup group) {
        List<TopLevelMenu> enabledTops = new ArrayList<>();
        for (TopLevelMenu t : group.topLevelMenus) {
            if (t.isEnabled) enabledTops.add(t);
        }
        _state.postValue(new MenuUiState(Level.TOPS, null, enabledTops, null, null, null));
    }

    public void openTop(TopLevelMenu top) {
        // Some menus have a wrapper item (e.g., "normal_charge") that holds operators as children
        List<MenuItem> firstLevel = new ArrayList<>();
        for (MenuItem item : top.items) {
            if (item.isEnabled) firstLevel.add(item);
        }
        
        List<MenuItem> ops;
        if (firstLevel.size() == 1 && !firstLevel.get(0).children.isEmpty()) {
            ops = new ArrayList<>();
            for (MenuItem child : firstLevel.get(0).children) {
                if (child.isEnabled) ops.add(child);
            }
        } else {
            ops = firstLevel;
        }
        
        MenuItem selected = ops.isEmpty() ? null : ops.get(0);
        List<ServiceDescriptor> amounts = new ArrayList<>();
        if (selected != null) {
            if (!selected.children.isEmpty()) {
                for (MenuItem c : selected.children) {
                    amounts.addAll(c.services);
                }
            } else {
                amounts.addAll(selected.services);
            }
        }
        _state.postValue(new MenuUiState(Level.OPERATORS, null, null, ops, amounts, selected != null ? selected.id : null));
    }

    public void openOperator(TopLevelMenu top, MenuItem operator) {
        List<ServiceDescriptor> amounts = new ArrayList<>();
        if (!operator.children.isEmpty()) {
            for (MenuItem c : operator.children) {
                amounts.addAll(c.services);
            }
        } else {
            amounts.addAll(operator.services);
        }
        
        MenuUiState currentState = _state.getValue();
        List<MenuItem> currentOps = currentState != null ? currentState.operators : new ArrayList<>();
        _state.postValue(new MenuUiState(Level.OPERATORS, null, null, currentOps, amounts, operator.id));
    }

    public void back() {
        MenuUiState current = _state.getValue();
        if (current == null) return;

        switch (current.level) {
            case OPERATORS:
                // Find parent group that contains this top
                MenuGroup group = null;
                for (MenuGroup g : all.groups) {
                    for (TopLevelMenu t : g.topLevelMenus) {
                        if (!t.items.isEmpty() && !current.operators.isEmpty()) {
                            // Check if any of current operators belong to this top
                            MenuItem firstOp = current.operators.get(0);
                            if (containsOperator(t, firstOp.id)) {
                                group = g;
                                break;
                            }
                        }
                    }
                    if (group != null) break;
                }
                if (group != null) {
                    openGroup(group);
                } else {
                    _state.postValue(new MenuUiState(Level.GROUPS, all.groups, null, null, null, null));
                }
                break;
            case TOPS:
                _state.postValue(new MenuUiState(Level.GROUPS, all.groups, null, null, null, null));
                break;
            case GROUPS:
                // Already at root
                break;
        }
    }

    private boolean containsOperator(TopLevelMenu top, String operatorId) {
        for (MenuItem item : top.items) {
            if (item.id.equals(operatorId)) return true;
            for (MenuItem child : item.children) {
                if (child.id.equals(operatorId)) return true;
            }
        }
        return false;
    }

    public void persistEdits(DynamicMenus newMenus) {
        repository.save(newMenus);
        all = newMenus;
        List<MenuGroup> enabledGroups = new ArrayList<>();
        for (MenuGroup g : all.groups) {
            if (g.isEnabled) enabledGroups.add(g);
        }
        _state.postValue(new MenuUiState(Level.GROUPS, enabledGroups, null, null, null, null));
    }
}
