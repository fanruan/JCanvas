package com.fr.graph.g2d.canvas;

import com.fr.general.IOUtils;
import com.fr.graph.g2d.canvas.j2v8.V8Painter;
import com.fr.graph.g2d.canvas.nashorn.NashornPainter;
import com.fr.log.FineLoggerFactory;
import com.fr.stable.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.awt.Font;
import java.awt.image.BufferedImage;

/**
 * @author richie
 * @version 10.0
 * Created by richie on 2019-05-21
 */
public abstract class CanvasPainter implements Closeable {

    public final static boolean SUPPORT_J2V8 = isSupportJ2v8();

    public final static boolean SUPPORT_NASHORN = isSupportNashorn();

    private StringBuilder sb = new StringBuilder();

    private static ConcurrentHashMap<String, Font> loadedFonts = new ConcurrentHashMap<String, Font>();

    public static final String SCRIPT_ENGINE_NAME = "NashornScriptEngine";

    /**
     * 生成用于构建Canvas画板的构建器
     *
     * @return 构建器
     */
    public static Builder newBuilder() {
        return new Builder(JsEngineType.J2V8_ENGINE);
    }

    public static Builder newBuilder(JsEngineType engineType) {
        return new Builder(engineType);
    }

    /**
     * 生成用于构建Canvas画板的构建器，默认加载了用于预处理的内置JS
     *
     * @return 构建器
     */
    public static Builder newDefaultBuilder() throws Exception {
        if (SUPPORT_J2V8) {
            return new Builder(JsEngineType.J2V8_ENGINE).prepare("/com/fr/graph/g2d/canvas/js/fx-adapter.js");
        } else if (SUPPORT_NASHORN) {
            return new Builder(JsEngineType.NASHORN_ENGINE).prepare("/com/fr/graph/g2d/canvas/js/nashorn-adapter.js");
        }
        throw new RuntimeException("C library and JDK version do not meet the requirements, " +
                "Please upgrade GBLIC to 2.17 And GLIBCXX to 3.4.19 or JDK to 1.8");
    }

    public static boolean isSupportJ2v8() {
        try {
            Class<?> v8Class = Class.forName("com.eclipsesource.v8.V8");
            Object result = v8Class.getMethod("createV8Runtime").invoke(null);

            result.getClass().getMethod("close").invoke(result);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public static boolean isSupportNashorn() {
        ScriptEngine javaScript = new ScriptEngineManager().getEngineByName("JavaScript");
        return javaScript != null &&
                SCRIPT_ENGINE_NAME.equals(javaScript.getClass().getSimpleName());
    }

    public static Font loadFont(String fontName, File file) {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, file);
            loadedFonts.put(fontName.toLowerCase(), font);
        } catch (Exception ex) {
            FineLoggerFactory.getLogger().error(ex.getMessage(), ex);
        }
        return font;
    }

    public static Font loadFont(String fontName, InputStream inputStream) {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            loadedFonts.put(fontName.toLowerCase(), font);
        } catch (Exception ex) {
            FineLoggerFactory.getLogger().error(ex.getMessage(), ex);
        }
        return font;
    }

    public static Font getFont(String fontName) {
        return loadedFonts.get(fontName.toLowerCase());
    }

    public static boolean hasFont(String fontName) {
        return loadedFonts.containsKey(fontName.toLowerCase());
    }


    public StringBuilder getSb() {
        return sb;
    }

    public void add(String script) {
        sb.append(script);
        sb.append(";");
    }

    abstract public void initCanvas();

    abstract public void execute(String script) throws Exception;

    abstract public BufferedImage paint() throws Exception;

    abstract public Object executeFunction(String functionName, Object... parameters) throws Exception;

    abstract public BufferedImage paint(String functionName, Object... parameters) throws Exception;

    public void close() {

    }

    public static class Builder {

        private CanvasPainter painter;

        public Builder(JsEngineType engineType) {
            switch (engineType) {
                case NASHORN_ENGINE:
                    painter = new NashornPainter();
                    break;
                default:
                    painter = new V8Painter();
            }
        }

        public Builder prepare(String filePath) throws Exception {
            String content = readFileBody(filePath);
            painter.execute(content);
            painter.initCanvas();
            return this;
        }

        public Builder loadAndExecute(String... filePath) throws Exception {
            for (String path : filePath) {
                String content = readFileBody(path);
                painter.execute(content);
            }
            return this;
        }

        public Builder loadTextAndExecute(String... strings) throws Exception {
            for (String text : strings) {
                painter.execute(text);
            }
            return this;
        }

        public Builder loadFile(String... filePath) {
            for (String path : filePath) {
                painter.add(readFileBody(path));
            }
            return this;
        }

        public Builder loadText(String... strings) {
            for (String text : strings) {
                painter.add(text);
            }
            return this;
        }

        private String readFileBody(String filePath) {
            InputStream in = CanvasPainter.Builder.class.getResourceAsStream(filePath);
            String script = StringUtils.EMPTY;
            try {
                script = IOUtils.inputStream2String(in);
            } catch (Exception e) {
                FineLoggerFactory.getLogger().error(e.getMessage(), e);
            }
            return script;
        }

        public CanvasPainter build() {
            return painter;
        }
    }

}