package com.fr.canvas;

import com.eclipsesource.v8.V8;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class V8Adapter {

    private V8 v8;

    public V8Adapter(V8 v8){
        this.v8 = v8;
    }

    public void init() {
        String script = loadAdapterJS();
        v8.executeVoidScript(script);
        //注册Canvas
        V8Canvas.register(v8);
    }

    private static String loadAdapterJS() {
        InputStream stream = null;
        try {
            stream = V8Adapter.class.getResourceAsStream("/js/fx-adapter.js");
            return CharStreams.toString(new InputStreamReader(stream));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closeables.closeQuietly(stream);
        }
        return "";
    }
}

