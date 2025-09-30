package com.miaadrajabi.menuxmlparser.java.data.repository;

import com.miaadrajabi.menuxmlparser.java.data.mapper.XmlMappers;
import com.miaadrajabi.menuxmlparser.java.data.source.AssetsXmlDataSource;
import com.miaadrajabi.menuxmlparser.java.data.source.LocalXmlDataSource;
import com.miaadrajabi.menuxmlparser.java.data.xml.XmlModels;
import com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.DynamicMenus;

import java.io.IOException;

public class MenuRepository {
    private final AssetsXmlDataSource assets;
    private final LocalXmlDataSource local;

    public MenuRepository(AssetsXmlDataSource assets, LocalXmlDataSource local) {
        this.assets = assets;
        this.local = local;
    }

    public DynamicMenus load() {
        try {
            XmlModels fromLocal = local.read();
            XmlModels xml = fromLocal != null ? fromLocal : assets.read();
            return XmlMappers.toDomain(xml);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load menu data", e);
        }
    }

    public void save(DynamicMenus domain) {
        try {
            XmlModels xml = toXml(domain);
            local.write(xml);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save menu data", e);
        }
    }

    // Reverse mappers for persistence
    private XmlModels toXml(DynamicMenus domain) {
        XmlModels xml = new XmlModels();
        for (com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.MenuGroup g : domain.groups) {
            xml.menuGroups.add(toXml(g));
        }
        return xml;
    }

    private XmlModels.MenuGroupXml toXml(com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.MenuGroup g) {
        XmlModels.MenuGroupXml gx = new XmlModels.MenuGroupXml();
        gx.clazz = null;
        gx.enTitle = g.englishTitle;
        gx.enable = Boolean.toString(g.isEnabled);
        gx.faTitle = g.persianTitle;
        gx.id = g.id;
        for (com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.TopLevelMenu t : g.topLevelMenus) {
            gx.topLevelMenus.add(toXml(t));
        }
        return gx;
    }

    private XmlModels.TopLevelMenuXml toXml(com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.TopLevelMenu t) {
        XmlModels.TopLevelMenuXml tx = new XmlModels.TopLevelMenuXml();
        tx.enTitle = t.englishTitle;
        tx.enable = Boolean.toString(t.isEnabled);
        tx.expandable = t.isExpandable != null ? Boolean.toString(t.isExpandable) : null;
        tx.faTitle = t.persianTitle;
        tx.icon = t.icon;
        tx.id = t.id;
        for (com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.ServiceDescriptor s : t.services) {
            tx.services.add(toXml(s));
        }
        for (com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.MenuItem m : t.items) {
            tx.items.add(toXml(m));
        }
        return tx;
    }

    private XmlModels.MenuItemXml toXml(com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.MenuItem m) {
        XmlModels.MenuItemXml mx = new XmlModels.MenuItemXml();
        mx.clazz = m.clazz;
        mx.enTitle = m.englishTitle;
        mx.enable = Boolean.toString(m.isEnabled);
        mx.faTitle = m.persianTitle;
        mx.id = m.id;
        mx.ussdGetSimCharge = m.ussdGetSimCharge;
        mx.ussdGetSimNum = m.ussdGetSimNum;
        mx.usssdChargeCommand = m.usssdChargeCommand;
        for (com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.MenuItem c : m.children) {
            mx.children.add(toXml(c));
        }
        for (com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.ServiceDescriptor s : m.services) {
            mx.services.add(toXml(s));
        }
        return mx;
    }

    private XmlModels.ServiceDescriptorXml toXml(com.miaadrajabi.menuxmlparser.java.domain.model.MenuModels.ServiceDescriptor s) {
        XmlModels.ServiceDescriptorXml sx = new XmlModels.ServiceDescriptorXml();
        sx.id = s.id;
        sx.service = s.service;
        sx.serviceAmount = s.serviceAmount;
        sx.categoryId = s.categoryId;
        sx.hasCount = s.hasCount;
        sx.load = s.load;
        sx.providerId = s.providerId;
        sx.support = s.support;
        sx.algorithmType = s.algorithmType;
        sx.companyNameEn = s.companyNameEn;
        sx.companyNameFa = s.companyNameFa;
        return sx;
    }
}
