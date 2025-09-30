package com.miaadrajabi.menuxmlparser.java.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuModels {
    public static class DynamicMenus {
        public final List<MenuGroup> groups;
        public DynamicMenus(List<MenuGroup> groups) { this.groups = groups != null ? groups : Collections.emptyList(); }
    }

    public static class MenuGroup {
        public final String id;
        public final String englishTitle;
        public final String persianTitle;
        public final boolean isEnabled;
        public final String icon;
        public final List<TopLevelMenu> topLevelMenus;
        public MenuGroup(String id, String en, String fa, boolean enabled, String icon, List<TopLevelMenu> tops) {
            this.id = id; this.englishTitle = en; this.persianTitle = fa; this.isEnabled = enabled; this.icon = icon;
            this.topLevelMenus = tops != null ? tops : new ArrayList<>();
        }
    }

    public static class TopLevelMenu {
        public final String id;
        public final String englishTitle;
        public final String persianTitle;
        public final String icon;
        public final boolean isEnabled;
        public final Boolean isExpandable;
        public final List<MenuItem> items;
        public final List<ServiceDescriptor> services;
        public TopLevelMenu(String id, String en, String fa, String icon, boolean enabled, Boolean expandable,
                             List<MenuItem> items, List<ServiceDescriptor> services) {
            this.id = id; this.englishTitle = en; this.persianTitle = fa; this.icon = icon; this.isEnabled = enabled; this.isExpandable = expandable;
            this.items = items != null ? items : new ArrayList<>();
            this.services = services != null ? services : new ArrayList<>();
        }
    }

    public static class MenuItem {
        public final String id;
        public final String englishTitle;
        public final String persianTitle;
        public final boolean isEnabled;
        public final String clazz;
        public final String ussdGetSimCharge;
        public final String ussdGetSimNum;
        public final String usssdChargeCommand;
        public final List<MenuItem> children;
        public final List<ServiceDescriptor> services;
        public MenuItem(String id, String en, String fa, boolean enabled, String clazz,
                         String ussdGetSimCharge, String ussdGetSimNum, String usssdChargeCommand,
                         List<MenuItem> children, List<ServiceDescriptor> services) {
            this.id = id; this.englishTitle = en; this.persianTitle = fa; this.isEnabled = enabled; this.clazz = clazz;
            this.ussdGetSimCharge = ussdGetSimCharge; this.ussdGetSimNum = ussdGetSimNum; this.usssdChargeCommand = usssdChargeCommand;
            this.children = children != null ? children : new ArrayList<>();
            this.services = services != null ? services : new ArrayList<>();
        }
    }

    public static class ServiceDescriptor {
        public final String id;
        public final String service;
        public final String serviceAmount;
        public final String categoryId;
        public final String hasCount;
        public final String load;
        public final String providerId;
        public final String support;
        public final String algorithmType;
        public final String companyNameEn;
        public final String companyNameFa;
        public ServiceDescriptor(String id, String service, String amount, String categoryId, String hasCount,
                                  String load, String providerId, String support, String algorithmType,
                                  String companyNameEn, String companyNameFa) {
            this.id = id; this.service = service; this.serviceAmount = amount; this.categoryId = categoryId; this.hasCount = hasCount;
            this.load = load; this.providerId = providerId; this.support = support; this.algorithmType = algorithmType;
            this.companyNameEn = companyNameEn; this.companyNameFa = companyNameFa;
        }
    }
}
