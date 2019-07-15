package com.fr.graph.g2d.canvas.nashorn;

import com.fr.graph.g2d.canvas.CanvasAdapter;
import com.fr.graph.g2d.canvas.CanvasPainter;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.image.BufferedImage;

public class NashornPainter extends CanvasPainter {

    private ScriptEngine nashorn;

    private NashornCanvas canvas = new NashornCanvas();

    public NashornPainter() {
        init();
    }

    public void init() {
        nashorn = new ScriptEngineManager().getEngineByName("JavaScript");
    }

    public BufferedImage paint() throws Exception {
        execute();
        return canvas.getCanvas();
    }

    public Object executeFunction(String functionName, Object... parameters) throws Exception {
        Invocable invocable = (Invocable) nashorn;
        return invocable.invokeFunction(functionName, parameters);
    }

    public BufferedImage paint(String functionName, Object... parameters) throws Exception {
        CanvasAdapter canvas = new NashornCanvas();
        Object[] newParameters = new Object[parameters.length + 1];

        Invocable invocable = (Invocable) nashorn;
        newParameters[0] = canvas;
        System.arraycopy(parameters, 0, newParameters, 1, parameters.length);
        Object result = invocable.invokeFunction(functionName, newParameters);

        BufferedImage image = canvas.getCanvas();
        canvas.dispose();
        return image;
    }

    public void initCanvas() {
        nashorn.put("canvas", canvas);
    }

    public void execute(String script) throws Exception {
        nashorn.eval(script);
    }

    private void execute() throws Exception {
        nashorn.eval(getSb().toString());
        getSb().setLength(0);
    }

}