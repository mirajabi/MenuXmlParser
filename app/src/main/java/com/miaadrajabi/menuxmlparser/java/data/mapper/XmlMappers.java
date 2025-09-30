package com.miaadrajabi.menuxmlparser.java.data.mapper;

import com.miaadrajabi.menuxmlparser.java.data.xml.XmlModels.*;
import com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.*;

import java.util.ArrayList;
import java.util.List;

public class XmlMappers {
    public static DynamicMenus toDomain(com.miaadrajabi.menuxmlparser.java.data.xml.XmlModels xml) {
        List<MenuGroup> groups = new ArrayList<>();
        for (MenuGroupXml g : xml.menuGroups) {
            groups.add(toDomain(g));
        }
        return new DynamicMenus(groups);
    }

    public static MenuGroup toDomain(MenuGroupXml g) {
        List<TopLevelMenu> tops = new ArrayList<>();
        for (TopLevelMenuXml t : g.topLevelMenus) {
            tops.add(toDomain(t));
        }
        return new MenuGroup(
            g.id != null ? g.id : "",
            g.enTitle != null ? g.enTitle : "",
            g.faTitle != null ? g.faTitle : "",
            "true".equalsIgnoreCase(g.enable),
            g.topLevelMenus.isEmpty() ? null : g.topLevelMenus.get(0).icon,
            tops
        );
    }

    public static TopLevelMenu toDomain(TopLevelMenuXml t) {
        List<MenuItem> items = new ArrayList<>();
        for (MenuItemXml m : t.items) {
            items.add(toDomain(m));
        }
        List<ServiceDescriptor> services = new ArrayList<>();
        for (ServiceDescriptorXml s : t.services) {
            services.add(toDomain(s));
        }
        return new TopLevelMenu(
            t.id != null ? t.id : "",
            t.enTitle != null ? t.enTitle : "",
            t.faTitle != null ? t.faTitle : "",
            t.icon,
            "true".equalsIgnoreCase(t.enable),
            t.expandable != null ? "true".equalsIgnoreCase(t.expandable) : null,
            items,
            services
        );
    }

    public static MenuItem toDomain(MenuItemXml m) {
        List<MenuItem> children = new ArrayList<>();
        for (MenuItemXml c : m.children) {
            children.add(toDomain(c));
        }
        List<ServiceDescriptor> services = new ArrayList<>();
        for (ServiceDescriptorXml s : m.services) {
            services.add(toDomain(s));
        }
        return new MenuItem(
            m.id != null ? m.id : "",
            m.enTitle != null ? m.enTitle : "",
            m.faTitle != null ? m.faTitle : "",
            "true".equalsIgnoreCase(m.enable),
            m.clazz,
            m.ussdGetSimCharge,
            m.ussdGetSimNum,
            m.usssdChargeCommand,
            children,
            services
        );
    }

    public static ServiceDescriptor toDomain(ServiceDescriptorXml s) {
        return new ServiceDescriptor(
            s.id != null ? s.id : "",
            s.service != null ? s.service : "",
            s.serviceAmount,
            s.categoryId,
            s.hasCount,
            s.load,
            s.providerId,
            s.support,
            s.algorithmType,
            s.companyNameEn,
            s.companyNameFa
        );
    }
}
