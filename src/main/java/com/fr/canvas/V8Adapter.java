package com.fr.canvas;

import com.eclipsesource.v8.V8;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class V8Adapter {

    public static void init(V8 v8) {
        String script = loadAdapterJS("/js/fx-adapter.js");
        v8.executeVoidScript(script);
        //注册Canvas
        V8Canvas.register(v8);
    }

    public static String loadAdapterJS(String resPath) {
        InputStream stream = null;
        try{
            stream = V8Adapter.class.getResourceAsStream(resPath);
            return CharStreams.toString(new InputStreamReader(stream));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closeables.closeQuietly(stream);
        }
        return "";
    }
}

