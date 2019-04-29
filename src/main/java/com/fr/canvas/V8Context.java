package com.fr.canvas;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

import java.awt.Color;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;

public class V8Context extends V8Object {
    private ContextAdapter context;

    public V8Context(V8 v8, ContextAdapter context) {
        super(v8);
        this.context = context;
        //绑定属性
        initPrototype();
        //绑定反射获得的方法
        initSelfMethod();
        //绑定需要适配的方法
        initAdapterMethod();
    }

    private void initPrototype() {
        V8Object proto = v8.getObject("NativeContextPrototype");
        setPrototype(proto);
        proto.release();
    }

    /**
     * 增加通过反射获取并且不需要适配的方法
     */
    private void initSelfMethod() {
        registerJavaMethod(context, "out", "out", new Class[]{String.class});
        registerJavaMethod(context, "save", "save", new Class[]{});
        registerJavaMethod(context, "fill", "fill", new Class[]{});
        registerJavaMethod(context, "rotate", "rotate", new Class[]{double.class});
        registerJavaMethod(context, "scale", "scale", new Class[]{double.class, double.class});
        registerJavaMethod(context, "moveTo", "moveTo", new Class[]{double.class, double.class});
        registerJavaMethod(context, "lineTo", "lineTo", new Class[]{double.class, double.class});
        registerJavaMethod(context, "rect", "rect", new Class[]{double.class, double.class, double.class, double.class});
        registerJavaMethod(context, "strokeRect", "strokeRect", new Class[]{double.class, double.class, double.class, double.class});
        registerJavaMethod(context, "fillRect", "fillRect", new Class[]{double.class, double.class, double.class, double.class});
        registerJavaMethod(context, "clearRect", "clearRect", new Class[]{double.class, double.class, double.class, double.class});
        registerJavaMethod(context, "arc", "arc", new Class[]{double.class, double.class, double.class, double.class, double.class});
        registerJavaMethod(context, "arc", "arc", new Class[]{double.class, double.class, double.class, double.class, double.class, boolean.class});
        registerJavaMethod(context, "arcTo", "arcTo", new Class[]{float.class, float.class, float.class, float.class, float.class});
        registerJavaMethod(context, "quadraticCurveTo", "quadraticCurveTo", new Class[]{double.class, double.class, double.class, double.class});
        registerJavaMethod(context, "bezierCurveTo", "bezierCurveTo", new Class[]{double.class, double.class, double.class, double.class, double.class, double.class});
        registerJavaMethod(context, "closePath", "closePath", new Class[]{});
        registerJavaMethod(context, "isPointInPath", "isPointInPath", new Class[]{double.class, double.class});
        registerJavaMethod(context, "beginPath", "beginPath", new Class[]{});
        registerJavaMethod(context, "stroke", "stroke", new Class[]{});
        registerJavaMethod(context, "clip", "clip", new Class[]{});
        registerJavaMethod(context, "setLineCap", "setLineCap", new Class[]{String.class});
        registerJavaMethod(context, "setLineJoin", "setLineJoin", new Class[]{String.class});
        registerJavaMethod(context, "setLineWidth", "setLineWidth", new Class[]{float.class});
        registerJavaMethod(context, "setMiterLimit", "setMiterLimit", new Class[]{float.class});
        registerJavaMethod(context, "translate", "translate", new Class[]{double.class, double.class});
        registerJavaMethod(context, "setTransform", "setTransform", new Class[]{double.class, double.class, double.class, double.class, double.class, double.class});
        registerJavaMethod(context, "setFont", "setFont", new Class[]{String.class});
        registerJavaMethod(context, "setTextAlign", "setTextAlign", new Class[]{String.class});
        registerJavaMethod(context, "setTextBaseline", "setTextBaseline", new Class[]{String.class});
        registerJavaMethod(context, "fillText", "fillText", new Class[]{String.class, float.class, float.class});
        registerJavaMethod(context, "strokeText", "strokeText", new Class[]{String.class, float.class, float.class});
        registerJavaMethod(context, "measureText", "measureText", new Class[]{String.class});
        registerJavaMethod(context, "setGlobalCompositeOperation", "setGlobalCompositeOperation", new Class[]{String.class});
        registerJavaMethod(context, "setGlobalAlpha", "setGlobalAlpha", new Class[]{float.class});
        registerJavaMethod(context, "drawImage", "drawImage", new Class[]{BufferedImage.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class});
        registerJavaMethod(context, "drawImage", "drawImage", new Class[]{BufferedImage.class, int.class, int.class});
        registerJavaMethod(context, "drawImage", "drawImage", new Class[]{BufferedImage.class, int.class, int.class, int.class, int.class});
        registerJavaMethod(context, "createImageData", "createImageData", new Class[]{int.class, int.class});
        registerJavaMethod(context, "getImageData", "getImageData", new Class[]{int.class, int.class, int.class, int.class});
        registerJavaMethod(context, "putImageData", "putImageData", new Class[]{String.class, int.class, int.class});
        registerJavaMethod(context, "putImageData", "putImageData", new Class[]{String.class, int.class, int.class, int.class, int.class, int.class, int.class});
        registerJavaMethod(context, "restore", "restore", new Class[]{});
        registerJavaMethod(context, "transform", "transform", new Class[]{double.class, double.class, double.class, double.class, double.class, double.class});
    }

    /**
     * 增加自动生成的绑定中不包含的接口或者需要适配的接口
     */
    private void initAdapterMethod() {
        registerJavaMethod(new JavaCallback() {
            @Override
            public Object invoke(V8Object receiver, V8Array parameters) {
                V8Object dimen = new V8Object(v8);
                String str = parameters.getString(0);
                TextMetrics textMetrics = context.measureText(str);
                dimen.registerJavaMethod(textMetrics, "getWidth", "getWidth", new Class[]{});
                V8Object proto = v8.getObject("NativeTextMetricsPrototype");
                dimen.setPrototype(proto);
                return dimen;
            }
        }, "measureText");

        registerJavaMethod(new JavaCallback() {
            @Override
            public Object invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() > 3) {
                    float x0 = (float) parameters.getDouble(0);
                    float y0 = (float) parameters.getDouble(1);
                    float x1 = (float) parameters.getDouble(2);
                    float y1 = (float) parameters.getDouble(3);
                    LinearGradientAdapter linearGradient = context.createLinearGradient(x0, y0, x1, y1);
                    return new V8LinearGradient(v8, linearGradient);
                } else {
                    throw new IllegalArgumentException("Failed to execute 'createLinearGradient': 4 arguments required, but only "
                            + parameters.length() + " present.");
                }
            }

        }, "createLinearGradient");

        registerJavaMethod(new JavaCallback() {
            @Override
            public Object invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() > 0) {
                    String paint = parameters.getString(0);
                    Paint fillPaint = context.setFillStyle(paint);
                   return paint2JSObject(fillPaint);
                } else {
                    throw new IllegalArgumentException("Failed to execute 'setFillStyle': 1 arguments required, but only "
                            + parameters.length() + " present.");
                }
            }
        }, "setFillStyle");

        registerJavaMethod(new JavaCallback() {
            @Override
            public Object invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() > 0) {
                    String paint = parameters.getString(0);
                    Paint strokePaint = context.setStrokeStyle(paint);
                    return paint2JSObject(strokePaint);
                } else {
                    throw new IllegalArgumentException("Failed to execute 'setStrokeStyle': 1 arguments required, but only "
                            + parameters.length() + " present.");
                }
            }
        }, "setStrokeStyle");
    }

    private Object paint2JSObject(Paint paint){
        if (paint instanceof LinearGradientPaint) {
            return new V8LinearGradient(v8, LinearGradientAdapter.Paint2Adapter((LinearGradientPaint) paint));
        } else if (paint instanceof RadialGradientPaint) {
            return new V8RadialGradient(v8, RadialGradientAdapter.Paint2Adapter((RadialGradientPaint) paint));
        } else {
            return CanvasUtils.color2Hexadecimal((Color) paint);
        }
    }
}
