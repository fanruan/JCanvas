package com.fr.canvas;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.fr.log.FineLoggerFactory;

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
        registerJavaMethod(context, "dispose", "dispose", new Class[]{});
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
        //registerJavaMethod(context, "arc", "arc", new Class[]{double.class, double.class, double.class, double.class, double.class});
        //registerJavaMethod(context, "arc", "arc", new Class[]{double.class, double.class, double.class, double.class, double.class, boolean.class});
        registerJavaMethod(context, "arcTo", "arcTo", new Class[]{double.class, double.class, double.class, double.class, double.class});
        registerJavaMethod(context, "quadraticCurveTo", "quadraticCurveTo", new Class[]{double.class, double.class, double.class, double.class});
        registerJavaMethod(context, "bezierCurveTo", "bezierCurveTo", new Class[]{double.class, double.class, double.class, double.class, double.class, double.class});
        registerJavaMethod(context, "closePath", "closePath", new Class[]{});
        registerJavaMethod(context, "isPointInPath", "isPointInPath", new Class[]{double.class, double.class});
        registerJavaMethod(context, "beginPath", "beginPath", new Class[]{});
        registerJavaMethod(context, "stroke", "stroke", new Class[]{});
        registerJavaMethod(context, "clip", "clip", new Class[]{});
        registerJavaMethod(context, "setLineCap", "setLineCap", new Class[]{String.class});
        registerJavaMethod(context, "setLineJoin", "setLineJoin", new Class[]{String.class});
        registerJavaMethod(context, "setLineWidth", "setLineWidth", new Class[]{double.class});
        registerJavaMethod(context, "setMiterLimit", "setMiterLimit", new Class[]{double.class});
        registerJavaMethod(context, "translate", "translate", new Class[]{double.class, double.class});
        registerJavaMethod(context, "setTransform", "setTransform", new Class[]{double.class, double.class, double.class, double.class, double.class, double.class});
        registerJavaMethod(context, "setFont", "setFont", new Class[]{String.class});
        registerJavaMethod(context, "setTextAlign", "setTextAlign", new Class[]{String.class});
        registerJavaMethod(context, "setTextBaseline", "setTextBaseline", new Class[]{String.class});
        registerJavaMethod(context, "fillText", "fillText", new Class[]{String.class, double.class, double.class});
        registerJavaMethod(context, "strokeText", "strokeText", new Class[]{String.class, double.class, double.class});
        registerJavaMethod(context, "measureText", "measureText", new Class[]{String.class});
        registerJavaMethod(context, "setGlobalCompositeOperation", "setGlobalCompositeOperation", new Class[]{String.class});
        registerJavaMethod(context, "setGlobalAlpha", "setGlobalAlpha", new Class[]{double.class});
        registerJavaMethod(context, "restore", "restore", new Class[]{});
        registerJavaMethod(context, "transform", "transform", new Class[]{double.class, double.class, double.class, double.class, double.class, double.class});
    }

    /**
     * 增加自动生成的绑定中不包含的接口或者需要适配的接口
     */
    private void initAdapterMethod() {

        registerJavaMethod(new JavaVoidCallback() {
            @Override
            public void invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() > 4) {
                    double cx = parameters.getDouble(0);
                    double cy = parameters.getDouble(1);
                    double r = parameters.getDouble(2);
                    double startAngle = parameters.getDouble(3);
                    double endAngle = parameters.getDouble(4);
                    boolean anticlockwise = false;
                    if (parameters.length() > 5) {
                        Object anticlockwiseObject = parameters.get(5);
                        if (anticlockwiseObject instanceof V8Object) {
                            ((V8Object) anticlockwiseObject).release();
                        } else {
                            anticlockwise = (Boolean) anticlockwiseObject;
                        }
                    }
                    context.arc(cx, cy, r, startAngle, endAngle, anticlockwise);
                } else {
                    throw new IllegalArgumentException("Failed to execute 'arc': 5 arguments required, but only "
                            + parameters.length() + " present.");
                }
            }
        }, "arc");

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
                if (parameters.length() > 5) {
                    float x0 = (float) parameters.getDouble(0);
                    float y0 = (float) parameters.getDouble(1);
                    float r0 = (float) parameters.getDouble(2);
                    float x1 = (float) parameters.getDouble(3);
                    float y1 = (float) parameters.getDouble(4);
                    float r1 = (float) parameters.getDouble(5);
                    RadialGradientAdapter radialGradient = context.createRadialGradient(x0, y0, r0, x1, y1, r1);
                    return new V8RadialGradient(v8, radialGradient);
                } else {
                    throw new IllegalArgumentException("Failed to execute 'createRadialGradient': 6 arguments required, but only "
                            + parameters.length() + " present.");
                }
            }

        }, "createRadialGradient");

        registerJavaMethod(new JavaCallback() {
            @Override
            public Object invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() > 0) {
                    Object paint = parameters.get(0);
                    Paint fillPaint;
                    if (paint != null) {
                        String paintString = paint.toString();
                        if (paint instanceof V8Object) {
                            ((V8Object) paint).release();
                        }
                        fillPaint = context.setFillStyle(paintString);
                    } else {
                        fillPaint = context.getFillPaint();
                    }
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
                    Object paint = parameters.get(0);
                    Paint strokePaint;
                    if (paint != null) {
                        String paintString = paint.toString();
                        if (paint instanceof V8Object) {
                            ((V8Object) paint).release();
                        }
                        strokePaint = context.setStrokeStyle(paintString);
                    } else {
                        strokePaint = context.getStrokePaint();
                    }
                    return paint2JSObject(strokePaint);
                } else {
                    throw new IllegalArgumentException("Failed to execute 'setStrokeStyle': 1 arguments required, but only "
                            + parameters.length() + " present.");
                }
            }
        }, "setStrokeStyle");

        registerJavaMethod(new JavaCallback() {
            @Override
            public Object invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() > 1) {
                    int width = parameters.getInteger(0);
                    int height = parameters.getInteger(1);
                    ImageData imageData = context.createImageData(width, height);
                    return new V8ImageData(v8, imageData);
                } else {
                    throw new IllegalArgumentException("Failed to execute 'createImageData': 2 arguments required, but only "
                            + parameters.length() + " present.");
                }
            }
        }, "createImageData");

        registerJavaMethod(new JavaCallback() {
            @Override
            public Object invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() > 3) {
                    int x = parameters.getInteger(0);
                    int y = parameters.getInteger(1);
                    int width = parameters.getInteger(2);
                    int height = parameters.getInteger(3);
                    ImageData imageData = context.getImageData(x, y, width, height);
                    return new V8ImageData(v8, imageData);
                } else {
                    throw new IllegalArgumentException("Failed to execute 'getImageData': 4 arguments required, but only "
                            + parameters.length() + " present.");
                }
            }
        }, "getImageData");

        registerJavaMethod(new JavaVoidCallback() {
            @Override
            public void invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() > 2) {
                    V8Object imageData = parameters.getObject(0);
                    int x = parameters.getInteger(1);
                    int y = parameters.getInteger(2);
                    int width = imageData.getInteger("width");
                    int height = imageData.getInteger("height");
                    V8Array v8Data = imageData.getArray("data");
                    imageData.release();
                    int[] data = new int[v8Data.length()];
                    for (int i = 0; i < v8Data.length(); i++) {
                        data[i] = v8Data.getInteger(i);
                    }
                    v8Data.release();
                    ImageData img = new ImageData(width, height, data);
                    if (parameters.length() > 6) {
                        int dirtyX = parameters.getInteger(3);
                        int dirtyY = parameters.getInteger(4);
                        int dirtyWidth = parameters.getInteger(5);
                        int dirtyHeight = parameters.getInteger(6);
                        context.putImageData(img, x, y, dirtyX, dirtyY, dirtyWidth, dirtyHeight);
                    } else {
                        context.putImageData(img, x, y);
                    }
                } else {
                    throw new IllegalArgumentException("Failed to execute 'getImageData': 3 arguments required, but only "
                            + parameters.length() + " present.");
                }
            }
        }, "putImageData");

        registerJavaMethod(new JavaVoidCallback() {
            @Override
            public void invoke(V8Object receiver, V8Array parameters) {
                int length = parameters.length();
                //至少需要三个参数
                if (length < 3) {
                    throw new IllegalArgumentException("Failed to execute 'getImageData': 3 arguments required, but only "
                            + parameters.length() + " present.");
                }
                Object imageObj = parameters.get(0);
                BufferedImage image;
                if (imageObj instanceof V8Object) {
                    V8Object v8Object = (V8Object) imageObj;
                    if (v8Object.contains("canvas_id")) {
                        //从另一个canvas绘制
                        image = ImageUtils.get(v8Object.getString("canvas_id"));
                        v8Object.release();
                    } else {
                        v8Object.release();
                        throw new IllegalArgumentException("The provided value must be Canvas Object or srcUrl");
                    }
                } else {
                    String url = (String) imageObj;
                    image = ImageUtils.getOrCreate(url);
                }
                if (image == null) {
                    throw new IllegalArgumentException("Wrong Image resources");
                }
                try{
                    //参数个数在3~4之间
                    if (length < 5) {
                        int x = parameters.getInteger(1);
                        int y = parameters.getInteger(2);
                        context.drawImage(image, x, y);
                    } else if (length < 9) {  //参数个数在5~8之间
                        int x = parameters.getInteger(1);
                        int y = parameters.getInteger(2);
                        int width = parameters.getInteger(3);
                        int height = parameters.getInteger(4);
                        context.drawImage(image, x, y, width, height);
                    } else { //参数个数大与9个之间
                        int sx = parameters.getInteger(1);
                        int sy = parameters.getInteger(2);
                        int sWidth = parameters.getInteger(3);
                        int sHeight = parameters.getInteger(4);
                        int x = parameters.getInteger(5);
                        int y = parameters.getInteger(6);
                        int width = parameters.getInteger(7);
                        int height = parameters.getInteger(8);
                        context.drawImage(image, sx, sy, sWidth, sHeight, x, y, width, height);
                    }
                } catch (Exception e) {
                    FineLoggerFactory.getLogger().error(e.getMessage(), e);
                }
            }
        }, "drawImage");
    }

    private Object paint2JSObject(Paint paint) {
        if (paint instanceof LinearGradientPaint) {
            return new V8LinearGradient(v8, LinearGradientAdapter.Paint2Adapter((LinearGradientPaint) paint));
        } else if (paint instanceof RadialGradientPaint) {
            return new V8RadialGradient(v8, RadialGradientAdapter.Paint2Adapter((RadialGradientPaint) paint));
        } else {
            return CanvasUtils.color2Hexadecimal((Color) paint);
        }
    }
}
