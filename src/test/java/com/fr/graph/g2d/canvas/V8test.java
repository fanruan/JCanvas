package com.fr.graph.g2d.canvas;

import com.eclipsesource.v8.V8;
import com.fr.general.IOUtils;
import com.fr.log.FineLoggerFactory;
import junit.framework.TestCase;
import org.junit.Ignore;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Ignore
public class V8test extends TestCase {

    @Ignore
    public static void main(String[] args) {
        try {
            runOne();
        } catch (Exception e) {
            FineLoggerFactory.getLogger().error(e.getMessage(), e);
        }
    }

    private static void runOne() throws Exception {
        List<String> jsonPaths = new ArrayList<String>();
        String beginPath = V8test.class.getResource("/").getPath().replace("test-classes", "classes") + "json";
        findFiles(beginPath, jsonPaths);
        for (String path : jsonPaths) {
            CanvasPainter painter = null;
            try {
                painter = CanvasPainter.newBuilder()
                        .prepare("/js/fx-adapter.js")
                        .loadAndExecute("/js/van-analysis.js", "/js/adaptor.js")
                        .loadText("var op=" + IOUtils.readResourceAsString(path.substring(path.indexOf(File.separator + "json"))))
                        .loadText("op.data.shared.animation = false;")
                        .loadText("var chart = new Van.VanChart({width:712,height:725,container:canvas}, op.data);")
                        .loadText("chart.zr.refreshImmediately();")
                        .build();
                String[] pathSplit = path.split("classes" + File.separator + "json");
                File imageFile = new File(pathSplit[0] + "Image" + pathSplit[1].replace("json", "png"));
                if (!imageFile.exists()) {
                    imageFile.mkdirs();
                }
                ImageIO.write(painter.paint(), "PNG",
                        imageFile);
            } catch (Exception e) {
                throw e;
            } finally {
                if (painter != null) {
                    painter.close();
                }
            }
        }
    }

    public static void runTwo() throws Exception {
        V8 v8 = V8.createV8Runtime();
        //V8Adapter.init(v8);
        CanvasAdapter canvas = new CanvasAdapter();
        v8.executeVoidScript(IOUtils.readResourceAsString("/js/fx-adapter.js"));
        V8Canvas v8Canvas = new V8Canvas(v8, canvas);
        try{
            v8.add("canvas", v8Canvas);
            v8Canvas.release();
            v8.executeVoidScript(IOUtils.readResourceAsString("/js/van-analysis.js"));
            v8.executeVoidScript(IOUtils.readResourceAsString("/js/adaptor.js"));
            List<String> jsonPaths = new ArrayList<String>();
            String beginPath = V8test.class.getResource("/").getPath().replace("test-classes", "classes") + "json";
            findFiles(beginPath, jsonPaths);
            for (String path : jsonPaths) {
                try{
                    String json = IOUtils.readResourceAsString(path.substring(path.indexOf(File.separator + "json")));
                    v8.executeVoidScript("var op = " + json);
                    v8.executeVoidScript("op.data.shared.animation = false");
                    v8.executeVoidScript("var chart = new Van.VanChart({width:712,height:725,container:canvas}, op.data)");
                    v8.executeVoidScript("chart.zr.refreshImmediately()");
                    String[] pathSplit = path.split("classes" + File.separator + "json");
                    File imageFile = new File(pathSplit[0] + "Image" + pathSplit[1].replace("json", "png"));
                    if (!imageFile.exists()) {
                        imageFile.mkdirs();
                    }
                    ImageIO.write(canvas.getCanvas(), "PNG",
                            imageFile);
                } catch (Exception ex1) {
                    FineLoggerFactory.getLogger().error(ex1.getMessage(), ex1);
                }
            }

        } catch (Exception ex) {
            FineLoggerFactory.getLogger().error(ex.getMessage(), ex);

        } finally {
            v8.release();
        }
    }

    private static void findFiles(String beginPath, List<String> filePaths) {
        File homeDic = new File(beginPath);
        if (homeDic.exists()) {
            File[] files = homeDic.listFiles();
            if (null == files || files.length == 0) {
                return;
            } else {
                for (File file : files) {
                    if (file.isDirectory()) {
                        findFiles(file.getAbsolutePath(), filePaths);
                    } else {
                        filePaths.add(file.getAbsolutePath());
                    }
                }
            }
        }
    }
}
