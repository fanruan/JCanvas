package com.fr.canvas;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Function;
import com.eclipsesource.v8.V8Object;

public class V8Canvas extends V8Object {

    public static void register(final V8 v8) {
        V8Function constructor = new V8Function(v8, new JavaCallback() {
            @Override
            public Object invoke(V8Object receiver, V8Array parameters) {
                int w = 0;
                int h = 0;
                if (parameters.length() == 2) {
                    w = parameters.getInteger(0);
                    h = parameters.getInteger(1);
                }
                return new V8Canvas(v8, new CanvasAdapter(w, h));
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
        registerJavaMethod(canvas, "getWidth", "getWidth", new Class<?>[]{});
        registerJavaMethod(canvas, "getHeight", "getHeight", new Class<?>[]{});
        registerJavaMethod(canvas, "setWidth", "setWidth", new Class<?>[]{int.class});
        registerJavaMethod(canvas, "setHeight", "setHeight", new Class<?>[]{int.class});
    }
}
