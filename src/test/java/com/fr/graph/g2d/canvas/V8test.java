package com.fr.graph.g2d.canvas;

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
        String beginPath = V8test.class.getResource("/").getPath() + "json";
        findFiles(beginPath, jsonPaths);
        for (String path : jsonPaths) {
            CanvasPainter painter = null;
            try {
                painter = CanvasPainter.newDefaultBuilder()
                        .loadAndExecute("/js/van-adapter", "/js/van-maptalks.js", "/js/van-analysis.js", "/js/adaptor.js")
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
