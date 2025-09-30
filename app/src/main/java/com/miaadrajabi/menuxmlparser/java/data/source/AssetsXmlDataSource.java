package com.miaadrajabi.menuxmlparser.java.data.source;

import android.content.Context;
import com.miaadrajabi.menuxmlparser.java.data.xml.XmlModels;
import com.miaadrajabi.menuxmlparser.java.data.xml.XmlConfig;

import java.io.IOException;
import java.io.InputStream;

public class AssetsXmlDataSource {
    private final Context context;

    public AssetsXmlDataSource(Context context) {
        this.context = context;
    }

    public XmlModels read(String filename) throws IOException {
        if (filename == null) filename = DEFAULT_FILE;
        try (InputStream input = context.getAssets().open(filename)) {
            return XmlConfig.getXmlMapper().readValue(input, XmlModels.class);
        }
    }

    public XmlModels read() throws IOException {
        return read(DEFAULT_FILE);
    }

    public static final String DEFAULT_FILE = "dynamic_menus.xml";
}
