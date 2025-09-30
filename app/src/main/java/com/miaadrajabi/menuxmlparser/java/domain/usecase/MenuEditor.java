package com.miaadrajabi.menuxmlparser.java.domain.usecase;

import com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.*;

import java.util.ArrayList;
import java.util.List;

public class MenuEditor {
    public DynamicMenus addGroup(DynamicMenus menus, MenuGroup group) {
        List<MenuGroup> groups = new ArrayList<>(menus.groups);
        groups.add(group);
        return new DynamicMenus(groups);
    }

    public DynamicMenus removeGroup(DynamicMenus menus, String groupId) {
        List<MenuGroup> groups = new ArrayList<>();
        for (MenuGroup g : menus.groups) if (!g.id.equals(groupId)) groups.add(g);
        return new DynamicMenus(groups);
    }

    public DynamicMenus updateGroup(DynamicMenus menus, MenuGroup updated) {
        List<MenuGroup> groups = new ArrayList<>();
        for (MenuGroup g : menus.groups) groups.add(g.id.equals(updated.id) ? updated : g);
        return new DynamicMenus(groups);
    }

    public DynamicMenus addTopMenu(DynamicMenus menus, String groupId, TopLevelMenu top) {
        List<MenuGroup> groups = new ArrayList<>();
        for (MenuGroup g : menus.groups) {
            if (g.id.equals(groupId)) {
                List<TopLevelMenu> tops = new ArrayList<>(g.topLevelMenus);
                tops.add(top);
                groups.add(copyGroup(g, tops));
            } else groups.add(g);
        }
        return new DynamicMenus(groups);
    }

    public DynamicMenus updateTopMenu(DynamicMenus menus, String groupId, TopLevelMenu top) {
        List<MenuGroup> groups = new ArrayList<>();
        for (MenuGroup g : menus.groups) {
            if (g.id.equals(groupId)) {
                List<TopLevelMenu> tops = new ArrayList<>();
                for (TopLevelMenu t : g.topLevelMenus) tops.add(t.id.equals(top.id) ? top : t);
                groups.add(copyGroup(g, tops));
            } else groups.add(g);
        }
        return new DynamicMenus(groups);
    }

    public DynamicMenus removeTopMenu(DynamicMenus menus, String groupId, String topId) {
        List<MenuGroup> groups = new ArrayList<>();
        for (MenuGroup g : menus.groups) {
            if (g.id.equals(groupId)) {
                List<TopLevelMenu> tops = new ArrayList<>();
                for (TopLevelMenu t : g.topLevelMenus) if (!t.id.equals(topId)) tops.add(t);
                groups.add(copyGroup(g, tops));
            } else groups.add(g);
        }
        return new DynamicMenus(groups);
    }

    public DynamicMenus addOperator(DynamicMenus menus, String topId, MenuItem operator) {
        return mapTop(menus, topId, t -> {
            List<MenuItem> ops = new ArrayList<>(t.items);
            ops.add(operator);
            return copyTop(t, ops);
        });
    }

    public DynamicMenus updateOperator(DynamicMenus menus, String topId, MenuItem operator) {
        return mapTop(menus, topId, t -> {
            List<MenuItem> ops = new ArrayList<>();
            for (MenuItem m : t.items) ops.add(m.id.equals(operator.id) ? operator : m);
            return copyTop(t, ops);
        });
    }

    public DynamicMenus removeOperator(DynamicMenus menus, String topId, String operatorId) {
        return mapTop(menus, topId, t -> {
            List<MenuItem> ops = new ArrayList<>();
            for (MenuItem m : t.items) if (!m.id.equals(operatorId)) ops.add(m);
            return copyTop(t, ops);
        });
    }

    private interface TopMapper { TopLevelMenu apply(TopLevelMenu t); }

    private DynamicMenus mapTop(DynamicMenus menus, String topId, TopMapper mapper) {
        List<MenuGroup> groups = new ArrayList<>();
        for (MenuGroup g : menus.groups) {
            List<TopLevelMenu> tops = new ArrayList<>();
            boolean changed = false;
            for (TopLevelMenu t : g.topLevelMenus) {
                if (t.id.equals(topId)) {
                    tops.add(mapper.apply(t));
                    changed = true;
                } else tops.add(t);
            }
            groups.add(changed ? copyGroup(g, tops) : g);
        }
        return new DynamicMenus(groups);
    }

    private MenuGroup copyGroup(MenuGroup g, List<TopLevelMenu> tops) {
        return new MenuGroup(g.id, g.englishTitle, g.persianTitle, g.isEnabled, g.icon, tops);
    }

    private TopLevelMenu copyTop(TopLevelMenu t, List<MenuItem> items) {
        return new TopLevelMenu(t.id, t.englishTitle, t.persianTitle, t.icon, t.isEnabled, t.isExpandable, items, t.services);
    }
}
