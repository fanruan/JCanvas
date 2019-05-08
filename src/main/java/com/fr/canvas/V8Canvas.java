package com.fr.canvas;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Function;
import com.eclipsesource.v8.V8Object;

import java.util.UUID;

public class V8Canvas extends V8Object {

    private String id;

    public static void register(final V8 v8) {
        V8Function constructor = new V8Function(v8, new JavaCallback() {
            @Override
            public Object invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() == 2) {
                    int w = parameters.getInteger(0);
                    int h = parameters.getInteger(1);
                    return new V8Canvas(v8, new CanvasAdapter(w, h));
                } else {
                    return new V8Canvas(v8, new CanvasAdapter());
                }
            }
        });
        v8.add("Canvas", constructor);
        constructor.release();
    }

    public V8Canvas(final V8 v8, final CanvasAdapter canvas) {
        super(v8);
        V8Object proto = v8.getObject("NativeCanvasPrototype");
        setPrototype(proto);
        proto.release();
        registerJavaMethod(new JavaCallback() {
            @Override
            public Object invoke(V8Object receiver, V8Array parameters) {
                return new V8Context(v8, canvas.getContext());
            }
        }, "getContext");
        registerJavaMethod(new JavaVoidCallback() {
            @Override
            public void invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() > 0) {
                    if (parameters.get(0) instanceof Double) {
                        canvas.setWidth((int) parameters.getDouble(0));
                    } else {
                        canvas.setWidth(parameters.getInteger(0));
                    }
                }
            }
        }, "setWidth");
        registerJavaMethod(new JavaVoidCallback() {
            @Override
            public void invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() > 0) {
                    if (parameters.get(0) instanceof Double) {
                        canvas.setHeight((int) parameters.getDouble(0));
                    } else {
                        canvas.setHeight(parameters.getInteger(0));
                    }
                }
            }
        }, "setHeight");
        registerJavaMethod(new JavaVoidCallback() {
            @Override
            public void invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() > 1) {
                    String attribute = parameters.getString(0);
                    if (parameters.get(1) instanceof Double) {
                        canvas.setAttribute(attribute, (int) parameters.getDouble(1));
                    } else {
                        canvas.setAttribute(attribute, parameters.getInteger(1));
                    }
                }
            }
        }, "setAttribute");
        registerJavaMethod(canvas, "getWidth", "getWidth", new Class<?>[]{});
        registerJavaMethod(canvas, "getHeight", "getHeight", new Class<?>[]{});
        //生成id并缓存
        String id = UUID.randomUUID().toString();
        this.id = id;
        add("canvas_id", id);
        ImageUtils.put(id, canvas);
    }

    @Override
    public void finalize() {
        ImageUtils.remove(id);
    }
}
