import com.eclipsesource.v8.V8;
import com.fr.canvas.CanvasAdapter;
import com.fr.canvas.V8Adapter;
import com.fr.canvas.V8Canvas;
import com.fr.log.FineLoggerFactory;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        V8 v8 = V8.createV8Runtime();
        V8Adapter.init(v8);
        CanvasAdapter canvas = new CanvasAdapter();
        V8Canvas v8Canvas = new V8Canvas(v8, canvas);
        try{
            v8.add("canvas", v8Canvas);
            v8Canvas.release();
            v8.executeVoidScript(V8Adapter.loadAdapterJS("/js/van-analysis.js"));
            v8.executeVoidScript(V8Adapter.loadAdapterJS("/js/adaptor.js"));
            List<String> jsonPaths = new ArrayList<String>();
            String beginPath = CanvasAdapter.class.getResource("/").getPath() + "json";
            findFiles(beginPath, jsonPaths);
            for (String path : jsonPaths) {
                try{
                    String json = V8Adapter.loadAdapterJS(path.substring(path.indexOf(File.separator + "json")));
                    v8.executeVoidScript("var op = " + json);
                    v8.executeVoidScript("op.data.shared.animation = false");
                    v8.executeVoidScript("var chart = new Van.VanChart({width:712,height:725,container:canvas}, op.data)");
                    v8.executeVoidScript("chart.zr.refreshImmediately()");
                    String[] pathSplit = path.split("classes" + File.separator + "json");
                    File imageFile = new File(pathSplit[0] + "Image" + pathSplit[1].replace("json","png"));
                    if(!imageFile.exists()){
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

    public static void findFiles(String beginPath, List<String> filePaths) {
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
