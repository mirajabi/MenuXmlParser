package com.miaadrajabi.menuxmlparser.java.data.source;

import android.content.Context;
import com.miaadrajabi.menuxmlparser.java.data.xml.XmlModels;
import com.miaadrajabi.menuxmlparser.java.data.xml.XmlConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class LocalXmlDataSource {
    private final Context context;

    public LocalXmlDataSource(Context context) {
        this.context = context;
    }

    private File file(String path) {
        if (path == null) path = DEFAULT_PATH;
        File f = new File(context.getFilesDir(), path);
        File parent = f.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }
        return f;
    }

    public XmlModels read(String path) throws IOException {
        File f = file(path);
        if (!f.exists()) return null;
        try (FileInputStream input = new FileInputStream(f)) {
            return XmlConfig.getXmlMapper().readValue(input, XmlModels.class);
        }
    }

    public XmlModels read() throws IOException {
        return read(DEFAULT_PATH);
    }

    public void write(XmlModels data, String path) throws IOException {
        File f = file(path);
        try (FileOutputStream output = new FileOutputStream(f)) {
            XmlConfig.getXmlMapper().writeValue(output, data);
        }
    }

    public void write(XmlModels data) throws IOException {
        write(data, DEFAULT_PATH);
    }

    public static final String DEFAULT_PATH = "menus/dynamic_menus.xml";
}
